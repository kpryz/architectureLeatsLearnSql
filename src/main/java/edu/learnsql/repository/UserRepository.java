package edu.learnsql.repository;

/**
 * Created by Yasin Mert on 25.02.2017.
 */
import edu.learnsql.model.Role;
import edu.learnsql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByEmail(String email);
	List<User> findByRole(Role role);
}
