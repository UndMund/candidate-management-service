package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.service.dto.file.FileDataDtoResponse;

public interface IFileService {
    FileDataDtoResponse getById(String id);
}
