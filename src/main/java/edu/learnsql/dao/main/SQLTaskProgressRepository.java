package edu.learnsql.dao.main;

import edu.learnsql.entities.main.SQLTask;
import edu.learnsql.entities.main.SQLTaskProgress;
import edu.learnsql.entities.main.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SQLTaskProgressRepository extends JpaRepository<SQLTaskProgress, Integer> {
    List<SQLTaskProgress> findBySqlTask(SQLTask task);

    List<SQLTaskProgress> findByUser(User user);
}
