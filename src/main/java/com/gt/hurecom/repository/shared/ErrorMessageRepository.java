package com.gt.hurecom.repository.shared;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.hurecom.entity.shared.ErrorMessage;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {

	Optional<ErrorMessage> findByCodeAndLocale(String code, String locale);
}
