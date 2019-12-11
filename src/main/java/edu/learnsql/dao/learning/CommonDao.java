package edu.learnsql.dao.learning;

import edu.learnsql.entities.learning.DBTable;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonDao {

    public <T> List<T> selectQuery(Class<T> type, String query, EntityManager entityManager) throws SQLException {
        List<T> list = new ArrayList<T>();
        List<String> fields = getSelectFields(query);
        Session session = (Session) entityManager.getDelegate();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                // Для Select
                try (ResultSet rst = statement.executeQuery(query)) {
                    while (rst.next()) {
                        T t = type.newInstance();
                        loadResultSetIntoObject(rst, t, fields);
                        list.add(t);
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
                }
            }
        });
        return list;
    }


    public void executeUpdate(String query, EntityManager entityManager) throws SQLException {
        Session session = (Session) entityManager.getDelegate();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                // Для Insert, Update, Delete
                statement.executeUpdate(query);
            }
        });
    }


    public static void loadResultSetIntoObject(ResultSet rst, Object object, List<String> fields)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            field.setAccessible(true);
            DBTable column = field.getAnnotation(DBTable.class);
            if("*".equals(fields.get(0)) || fields.contains(column.columnName())) {
                Object value = rst.getObject(column.columnName());
                Class<?> type = field.getType();
                if (isPrimitive(type)) {//check primitive type(Point 5)
                    Class<?> boxed = boxPrimitiveClass(type);//box if primitive(Point 6)
                    value = boxed.cast(value);
                }
                field.set(object, value);
            }
        }
    }


    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }


    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    public List<String> getSelectFields(String query) {
        List<String> fields = new ArrayList<>();

        String[] parsedQuery = query.split(" ");

        if (parsedQuery.length > 1 && "*".equals(parsedQuery[1].trim())) {
            fields.add(parsedQuery[1].trim());
            return fields;
        }

        for (int i = 1; i < parsedQuery.length; ++i) {
            if ("FROM".equalsIgnoreCase(parsedQuery[i])) {
                break;
            }
            for(String str : parsedQuery[i].split(",")) {
                fields.add(str.trim());
            }
        }
        return fields;
    }
}
