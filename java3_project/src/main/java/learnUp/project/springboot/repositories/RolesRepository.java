package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

    Set<Role> findByRoleIn(Set<String> roles);
}
