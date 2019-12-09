package edu.learnsql.service;

import edu.learnsql.dao.learning.CommonDao;
import edu.learnsql.dao.main.SQLTaskRepository;
import edu.learnsql.entities.main.SQLTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class SQLTaskService {

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
}
