package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.entity.common.CodeSequence;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.repository.common.CodeSequenceRepository;
import com.gt.hurecom.service.common.CodeGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final CodeSequenceRepository codeSequenceRepository;

    public CodeGeneratorServiceImpl(CodeSequenceRepository codeSequenceRepository) {
        this.codeSequenceRepository = codeSequenceRepository;
    }

    @Transactional
    @Override
    public String generateCode(String entityType, String prefix) {
        CodeSequence sequence = codeSequenceRepository.findByEntityType(entityType)
                .orElseThrow(() -> new HurecomException("Entity type is not found."));

        Long currentValue = sequence.getNextValue();
        sequence.setNextValue(currentValue + 1);
        codeSequenceRepository.save(sequence);

        return String.format("%s-%d-%04d", prefix, Year.now().getValue(), currentValue);
    }

}
