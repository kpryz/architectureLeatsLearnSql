package edu.learnsql.repository;

/**
 * Created by Yasin Mert on 25.02.2017.
 */
import edu.learnsql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);
}
