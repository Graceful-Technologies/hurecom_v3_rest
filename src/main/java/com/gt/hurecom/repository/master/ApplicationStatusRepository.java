package com.gt.hurecom.repository.master;

import com.gt.hurecom.entity.master.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {

}
