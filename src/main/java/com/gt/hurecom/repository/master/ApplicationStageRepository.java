package com.gt.hurecom.repository.master;

import com.gt.hurecom.entity.master.ApplicationStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStageRepository extends JpaRepository<ApplicationStage, Long> {
}
