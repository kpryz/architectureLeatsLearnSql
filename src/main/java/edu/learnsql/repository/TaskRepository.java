package edu.learnsql.repository;

/**
 * Created by Yasin Mert on 25.02.2017.
 */
import edu.learnsql.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
