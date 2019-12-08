package edu.learnsql.service;

import edu.learnsql.dao.main.SQLTaskProgressRepository;
import edu.learnsql.entities.main.SQLTask;
import edu.learnsql.entities.main.SQLTaskProgress;
import edu.learnsql.entities.main.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SQLTaskProgressService {

    private final SQLTaskProgressRepository sqlTaskProgressRepository;

    public SQLTaskProgressService(SQLTaskProgressRepository SQLTaskProgress) {
        this.sqlTaskProgressRepository = SQLTaskProgress;
    }

    public List<SQLTaskProgress> findAll() {
        List<SQLTaskProgress> user_tasks = new ArrayList<>();
        user_tasks = sqlTaskProgressRepository.findAll();
        return user_tasks;
    }

    public SQLTaskProgress findUserTask(int id) {
        return sqlTaskProgressRepository.findOne(id);
    }

    public void save(SQLTaskProgress user_task) {
        sqlTaskProgressRepository.save(user_task);
    }

    public void delete(int id) {
        sqlTaskProgressRepository.delete(id);

    }

    public List<SQLTaskProgress> findByTask(SQLTask task) {
        return sqlTaskProgressRepository.findBySqlTask(task);
    }

    public List<SQLTaskProgress> findByUser(User user) {
        return sqlTaskProgressRepository.findByUser(user);
    }
}
