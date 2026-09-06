package com.gt.hurecom.repository.common;

import com.gt.hurecom.entity.common.CodeSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeSequenceRepository extends JpaRepository<CodeSequence, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CodeSequence> findByEntityType(String entityType);
}
