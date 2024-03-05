package com.zavadskiy.candidate_management_service.api.controller;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CreateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.UpdateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.interfaces.ICandidateTestService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zavadskiy.candidate_management_service.api.utils.UrlPath.CANDIDATE_TEST;

@RestController
@RequiredArgsConstructor
@RequestMapping(CANDIDATE_TEST)
@Tag(name = "Candidate's tests controller",
        description = "Operations with candidate's tests")
public class CandidateTestController {
    private final ICandidateTestService candidateTestService;

    @PostMapping
    @Operation(summary = "Create new candidate's test",
            description = "Creates and returns new candidate's test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Candidate's test successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityDtoResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<CandidateTestDtoResponse> create(
            @Validated @RequestBody CreateCandidateTestDtoRequest request
    ) {
        return new ResponseEntity<>(
                candidateTestService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update candidate's test by id",
            description = "Updates and returns candidate's test by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidate's test successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpecialityDtoResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Candidate's test not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<CandidateTestDtoResponse> update(
            @PathVariable Long id,
            @Validated @RequestBody UpdateCandidateTestDtoRequest request
    ) {
        return new ResponseEntity<>(
                candidateTestService.update(id, request),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Get all candidate's tests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidate's tests successfully returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SpecialityDtoResponse.class)))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No candidate's tests found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<List<CandidateTestDtoResponse>> getAll() {
        var candidatesTests = candidateTestService.getAll();
        if (candidatesTests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                candidatesTests,
                HttpStatus.OK
        );
    }

    @GetMapping("{page}/{page_size}")
    @Operation(summary = "Get all candidate tests by page data and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Candidate tests successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No candidate tests found"
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<PageResponse<CandidateTestDtoResponse>> getAllSpecialitiesByPageRequest(
            @PathVariable(name = "page") int page,
            @PathVariable(name = "page_size") int pageSize,
            @Validated @RequestBody CandidateTestFilter candidateTestFilter
    ) {
        var candidateTestsPageResponse = candidateTestService.getAllByPageAndFilter(page, pageSize, candidateTestFilter);
        if (candidateTestsPageResponse.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                candidateTestsPageResponse,
                HttpStatus.OK
        );
    }
}
