package learnUp.project.springboot.repositories;

import learnUp.project.springboot.entities.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll(Specification<Client> specification);

    @Query(value = "select * from clients c where c.fio = ?1", nativeQuery = true)
    Client findByName(String name);

    @Query(value = "select * from clients c where c.username = ?1", nativeQuery = true)
    Client findByUsername(String username);
}
