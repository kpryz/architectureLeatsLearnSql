package edu.learnsql.dao.main;

import edu.learnsql.entities.main.SQLTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SQLTaskRepository extends JpaRepository<SQLTask, Integer> {
}
