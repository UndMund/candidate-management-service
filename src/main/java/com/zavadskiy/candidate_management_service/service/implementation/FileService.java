package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.repository.FileRepository;
import com.zavadskiy.candidate_management_service.service.dto.file.FileDataDtoResponse;
import com.zavadskiy.candidate_management_service.service.exception.FileNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.IFileService;
import com.zavadskiy.candidate_management_service.service.mappers.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService implements IFileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public FileDataDtoResponse getById(String id) {
        log.info("Try to get file data by id = {}", id);

        var fileData = fileRepository.findById(id)
                .map(fileMapper::toDataDto)
                .orElseThrow(() -> {
                    log.info("File with id = {} not found", id);
                    throw new FileNotFoundException(id);
                });

        log.info("Successfully got file with id = {}", id);

        return fileData;
    }
}
