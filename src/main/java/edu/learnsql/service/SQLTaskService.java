package edu.learnsql.service;

import edu.learnsql.dao.learning.CommonDao;
import edu.learnsql.dao.main.SQLTaskRepository;
import edu.learnsql.entities.learning.Car;
import edu.learnsql.entities.learning.Customer;
import edu.learnsql.entities.learning.Employee;
import edu.learnsql.entities.learning.Order;
import edu.learnsql.entities.main.SQLTask;
import org.omg.CORBA.WStringSeqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SQLTaskService {

    public enum QueryType {
        SELECT,
        INSERT,
        UPDATE
    }

    @Autowired
    private CommonDao commonDao;
    private final SQLTaskRepository SQLTaskRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Qualifier("learningEntityManager")
    @Autowired
    private EntityManager em;

    public SQLTaskService(SQLTaskRepository SQLTaskRepository) {
        this.SQLTaskRepository = SQLTaskRepository;
    }

    public List<SQLTask> findAll() {
        List<SQLTask> tasks = new ArrayList<>();
        tasks = SQLTaskRepository.findAll();
        return tasks;
    }

    public SQLTask findTask(int id) {
        return SQLTaskRepository.findOne(id);
    }

    public void save(SQLTask task) throws SQLException {
        String[] sqlStatements = task.getPrecondition().split(";");

        for (String statement : sqlStatements) {
            commonDao.executeUpdate(statement, em);
        }

        SQLTaskRepository.save(task);
    }

    public void delete(int id) throws SQLException {
        String[] sqlStatements = SQLTaskRepository.findOne(id).getPostcondition().split(";");

        for (String statement : sqlStatements) {
            commonDao.executeUpdate(statement, em);
        }

        SQLTaskRepository.delete(id);
    }

    public QueryType getTypeOfSQLQuery(String query) {
        String[] parseQuery = query.split(" ");

        if (QueryType.SELECT.toString().equalsIgnoreCase(parseQuery[0].trim())) {
            return QueryType.SELECT;
        } else if (QueryType.INSERT.toString().equalsIgnoreCase(parseQuery[0].trim())) {
            return QueryType.INSERT;
        } else if (QueryType.UPDATE.toString().equalsIgnoreCase(parseQuery[0].trim())) {
            return QueryType.UPDATE;
        } else {
            return null;
        }
    }

    public boolean checkQueries(String correctQuery, String userQuery) {
        QueryType typeOfSQLQuery = getTypeOfSQLQuery(correctQuery);

        QueryType typeOfSQLQuery1 = getTypeOfSQLQuery(userQuery);

        if (!typeOfSQLQuery.equals(typeOfSQLQuery1)) {
            return false;
        }

        boolean result = false;

        switch (typeOfSQLQuery) {
            case INSERT:
                result = checkInsertQueries(correctQuery, userQuery);
                break;
            case SELECT:
                result = checkSelectQueries(correctQuery, userQuery);
                break;
            case UPDATE:
                result = checkUpdateQueries(correctQuery, userQuery);
                break;
            default:
                return false;
        }
        return result;
    }


    public boolean checkUpdateQueries(String correctQuery, String userQuery) {
        Class<?> type = getTypeOfTable(correctQuery, QueryType.UPDATE);
        Class<?> type2 = getTypeOfTable(userQuery, QueryType.UPDATE);

        if (!type.equals(type2)) {
            return false;
        }

        List<?> result = Collections.emptyList();
        List<?> userQueryResult = Collections.emptyList();

        String selectQueryToCheck = getCheckQueryOfUpdate(correctQuery);

        try {
            commonDao.executeUpdate(userQuery, em);
        } catch (SQLException ex) {
            System.err.println("User insert query is incorrect!");
        }

        try {
            userQueryResult = commonDao.selectQuery(type, selectQueryToCheck, em);
        } catch (SQLException ex) {
            System.err.println("User check insert query is incorrect");
        }

        try {
            commonDao.executeUpdate(correctQuery, em);
        } catch (SQLException ex) {
            System.err.println("Task query is incorrect!");
        }

        try {
            result = commonDao.selectQuery(type, selectQueryToCheck, em);
        } catch (SQLException ex) {
            System.err.println("Task check query is incorrect");
        }

        if (userQueryResult.size() != result.size()) {
            return false;
        }

        for (int i = 0; i < userQueryResult.size(); ++i) {
            if (!userQueryResult.get(i).equals(result.get(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean checkInsertQueries(String correctQuery, String userQuery) {
        return false;

        // TO DO
    }

    public boolean checkSelectQueries(String correctQuery, String userQuery) {
        Class<?> type = getTypeOfTable(correctQuery, QueryType.SELECT);
        Class<?> type2 = getTypeOfTable(userQuery, QueryType.SELECT);

        if (!type.equals(type2)) {
            return false;
        }

        List<?> userResult = Collections.emptyList();
        List<?> result = Collections.emptyList();
        try {
            result = commonDao.selectQuery(type, correctQuery, em);
        } catch (SQLException ex) {
            System.err.println("Task query is incorrect");
        }

        try {
            userResult = commonDao.selectQuery(type, userQuery, em);
        } catch (SQLException ex) {
            System.err.println("User query is incorrect");
        }

        if (userResult.size() != result.size()) {
            return false;
        }

        for (int i = 0; i < userResult.size(); ++i) {
            if (!userResult.get(i).equals(result.get(i))) {
                return false;
            }
        }

        return true;
    }

    public Class<?> getTypeOfTable(String query, QueryType queryType) {
        String[] parsedQuery = query.split(" ");
        String keyword = "";

        switch (queryType) {
            case UPDATE:
                keyword = "UPDATE";
                break;
            case SELECT:
                keyword = "FROM";
                break;
        }

        String result = null;
        boolean isNextTableName = false;
        for (String str : parsedQuery) {
            if (keyword.equalsIgnoreCase(str) || isNextTableName) {
                if (isNextTableName) {
                    result = str.split(";")[0].trim();
                    break;
                }
                isNextTableName = true;
            }
        }
        if ("car".equalsIgnoreCase(result)) {
            return Car.class;
        } else if ("customer".equalsIgnoreCase(result)) {
            return Customer.class;
        } else if ("employee".equalsIgnoreCase(result)) {
            return Employee.class;
        } else if ("orders".equalsIgnoreCase(result)) {
            return Order.class;
        }

        return null;
    }

    public String getCheckQueryOfUpdate(String query) {
        String[] queryParse = query.split(" ");
        String tableName = null;
        if (queryParse.length > 2) {
            tableName = queryParse[1].trim();
        }
        List<String> fields = new ArrayList<>();
        String whereCondition = null;
        for (int i = 0; i < queryParse.length; ++i) {
            if ("SET".equalsIgnoreCase(queryParse[i].trim())) {
                for (int j = i + 1; j < queryParse.length; ++j) {
                    if(queryParse[j].equalsIgnoreCase("WHERE")) {
                        break;
                    }
                    String[] field = queryParse[j].split("=");
                    if (field.length == 1) {
                        fields.add(field[0].trim());
                        j += 2;
                    } else if (field.length > 1) {
                        fields.add(field[0].trim());
                    }
                }
            }
        }

        whereCondition = query.toUpperCase().split("WHERE")[1].trim();

        String result = "SELECT ";

        for (int i = 0; i < fields.size(); ++i) {
            if (i + 1 == fields.size()) {
                result += fields.get(i) + " ";
            } else {
                result += fields.get(i) + ", ";
            }
        }

        result += "FROM " + tableName + " ";

        result += "WHERE " + whereCondition.toLowerCase();

        return result;
    }
}
