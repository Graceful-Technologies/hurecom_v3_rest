package com.gt.hurecom.repository.master;

import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.master.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, ClientRepositoryCustom {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("""
            SELECT c
            FROM Client c
            LEFT JOIN FETCH c.createdUser
            LEFT JOIN FETCH c.lastModifiedUser
            WHERE c.id = :id
            """)
    Optional<Client> getClientById(Long id);

    List<Client> findByActiveTrue();

}
