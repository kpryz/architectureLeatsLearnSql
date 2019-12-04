package edu.learnsql.dao.main;

import edu.learnsql.entities.main.Role;
import edu.learnsql.entities.main.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findByRole(Role role);
}
