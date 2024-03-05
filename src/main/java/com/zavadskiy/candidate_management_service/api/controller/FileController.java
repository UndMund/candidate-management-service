package com.zavadskiy.candidate_management_service.api.controller;

import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.interfaces.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zavadskiy.candidate_management_service.api.utils.UrlPath.FILES;

@RestController
@RequiredArgsConstructor
@RequestMapping(FILES)
@Tag(name = "File controller")
public class FileController {
    private final IFileService fileService;

    @GetMapping("/{id}")
    @Operation(summary = "Get file by id",
            description = "Returns file data by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "File successfully returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Byte.class)))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "File not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<byte[]> getFile(
            @PathVariable String id
    ) {
        var file = fileService.getById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getData());
    }
}
