package edu.learnsql.service;

import edu.learnsql.entities.main.SQLTask;
import edu.learnsql.dao.main.SQLTaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SQLTaskService {

    private final SQLTaskRepository SQLTaskRepository;


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

    public void save(SQLTask task) {
        SQLTaskRepository.save(task);
    }

    public void delete(int id) {
        SQLTaskRepository.delete(id);

    }
}
