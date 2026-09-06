package com.gt.hurecom.repository.master;

import com.gt.hurecom.entity.master.ClientLocation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientLocationRepository extends JpaRepository<ClientLocation, Long>, JpaSpecificationExecutor<ClientLocation> {

    boolean existsByClient_IdAndCity_IdAndBranchNameIgnoreCase(Long clientId, Long cityId, String branchName);

    boolean existsByClient_IdAndCity_IdAndBranchNameIgnoreCaseAndIdNot(Long clientId, Long cityId, String branchName, Long id);

    @EntityGraph(attributePaths = {"country", "state", "city"})
    List<ClientLocation> findByClient_Id(Long jobId);

    @EntityGraph(attributePaths = {"country", "state", "city"})
    List<ClientLocation> findByClient_IdAndActiveTrue(Long jobId);

    @Query("""
           SELECT COUNT(cl)
           FROM ClientLocation cl
           WHERE cl.client.id = :clientId
           AND cl.active = true
    """)
    Long countActiveLocations(Long clientId);

}
