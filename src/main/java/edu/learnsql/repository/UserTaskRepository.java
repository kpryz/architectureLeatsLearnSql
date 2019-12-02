package edu.learnsql.repository;

import edu.learnsql.entities.main.Task;
import edu.learnsql.entities.main.User;
import edu.learnsql.entities.main.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {
    List<UserTask> findByTask(Task task);

    List<UserTask> findByUser(User user);
}
