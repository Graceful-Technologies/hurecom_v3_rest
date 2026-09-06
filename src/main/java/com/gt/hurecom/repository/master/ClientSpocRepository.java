package com.gt.hurecom.repository.master;

import com.gt.hurecom.entity.master.ClientSpoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientSpocRepository extends JpaRepository<ClientSpoc, Long>, JpaSpecificationExecutor<ClientSpoc> {

    boolean existsByEmailIgnoreCaseOrMobileNumber(String email, String mobileNumber);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);

    List<ClientSpoc> findByClient_Id(Long clientId);

    List<ClientSpoc> findByClient_IdAndActiveTrue(Long clientId);

    @Query("""
           SELECT COUNT(cs)
           FROM ClientSpoc cs
           WHERE cs.client.id = :clientId
           AND cs.active = true
    """)
    Long countActiveSpocs(Long clientId);

}
