package com.gt.hurecom.service.impl.shared;

import org.springframework.stereotype.Service;

import com.gt.hurecom.entity.shared.ErrorMessage;
import com.gt.hurecom.repository.shared.ErrorMessageRepository;
import com.gt.hurecom.service.shared.ErrorMessageService;

@Service
public class ErrorMessageServiceImpl implements ErrorMessageService {

	private final ErrorMessageRepository errorMessageRepository;

	public ErrorMessageServiceImpl(ErrorMessageRepository errorMessageRepository) {
		this.errorMessageRepository = errorMessageRepository;
	}

	@Override
	public String getMessage(String code, String locale) {
		locale = "en";
		return errorMessageRepository.findByCodeAndLocale(code, locale).map(ErrorMessage::getMessage)
				.orElse("Unknown error");
	}
}
