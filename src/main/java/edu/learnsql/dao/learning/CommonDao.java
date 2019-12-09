package edu.learnsql.dao.learning;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class CommonDao {
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
}
