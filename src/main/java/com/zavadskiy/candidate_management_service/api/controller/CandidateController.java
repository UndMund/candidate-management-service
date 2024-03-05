package com.zavadskiy.candidate_management_service.api.controller;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CreateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidate.UpdateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.interfaces.ICandidateService;
import com.zavadskiy.candidate_management_service.service.validation.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zavadskiy.candidate_management_service.api.utils.UrlPath.CANDIDATES;

@RestController
@RequiredArgsConstructor
@RequestMapping(CANDIDATES)
@Tag(name = "Candidate controller",
        description = "Operations with candidates")
public class CandidateController {
    private final ICandidateService candidateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new candidate",
            description = "Creates and returns new candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Candidate successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidateDtoResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Speciality not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<CandidateDtoResponse> create(
            @Validated @ModelAttribute CreateCandidateDtoRequest request
    ) {
        return new ResponseEntity<>(
                candidateService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Update candidate by id",
            description = "Updates and returns candidate by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidate successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidateDtoResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Candidate not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Speciality not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<CandidateDtoResponse> update(
            @PathVariable Long id,
            @Validated @ModelAttribute UpdateCandidateDtoRequest request
    ) {
        return new ResponseEntity<>(
                candidateService.update(id, request),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Get all candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidates successfully returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CandidateDtoResponse.class)))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No candidates found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<List<CandidateDtoResponse>> getAll() {
        var candidates = candidateService.getAll();
        if (candidates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                candidates,
                HttpStatus.OK
        );
    }

    @GetMapping("{page}/{page_size}")
    @Operation(summary = "Get all candidates by page data and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidates successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No candidates found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<PageResponse<CandidateDtoResponse>> getAllSpecialitiesByPageRequest(
            @PathVariable(name = "page") int page,
            @PathVariable(name = "page_size") int pageSize,
            @RequestBody CandidateFilter candidateFilter
    ) {
        var candidatesPageResponse = candidateService.getAllByPageAndFilter(page, pageSize, candidateFilter);
        if (candidatesPageResponse.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                candidatesPageResponse,
                HttpStatus.OK
        );
    }
}
