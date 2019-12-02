package edu.learnsql.service;

import edu.learnsql.entities.main.Task;
import edu.learnsql.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        tasks = taskRepository.findAll();
        return tasks;
    }

    public Task findTask(int id) {
        return taskRepository.findOne(id);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void delete(int id) {
        taskRepository.delete(id);

    }
}
