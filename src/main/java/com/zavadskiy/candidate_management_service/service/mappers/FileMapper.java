package com.zavadskiy.candidate_management_service.service.mappers;

import com.zavadskiy.candidate_management_service.database.entity.File;
import com.zavadskiy.candidate_management_service.service.dto.file.FileDataDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.file.FileDtoResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Mapper(componentModel = "spring")
public abstract class FileMapper {
    public File toEntity(MultipartFile file) throws IOException {
        return File.builder()
                .name(StringUtils.cleanPath(file.getOriginalFilename()))
                .contentType(file.getContentType())
                .data(file.getBytes())
                .size(file.getSize())
                .build();
    }

    public FileDtoResponse toDto(File file) {
        return FileDtoResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .url(FileDtoResponse.getUrl(file.getId()))
                .build();
    }

    public abstract FileDataDtoResponse toDataDto(File file);
}
