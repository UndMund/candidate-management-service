package com.zavadskiy.candidate_management_service.api.controller;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.CreateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.test.TestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.TestFilter;
import com.zavadskiy.candidate_management_service.service.dto.test.UpdateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.interfaces.ITestService;
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

import static com.zavadskiy.candidate_management_service.api.utils.UrlPath.TESTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(TESTS)
@Tag(name = "Test controller",
        description = "Operations with tests")
public class TestController {
    private final ITestService testService;

    @PostMapping
    @Operation(summary = "Create new test",
            description = "Creates and returns new test")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Test successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestDtoResponse.class))}
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
    public ResponseEntity<TestDtoResponse> create(
            @Validated @RequestBody CreateTestDtoRequest request
    ) {
        return new ResponseEntity<>(
                testService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update test by id",
            description = "Updates and returns test by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Test successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestDtoResponse.class))}
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
                    description = "Test not found",
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
    public ResponseEntity<TestDtoResponse> update(
            @PathVariable Long id,
            @Validated @RequestBody UpdateTestDtoRequest request
    ) {
        return new ResponseEntity<>(
                testService.update(id, request),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Get all tests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Tests successfully returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TestDtoResponse.class)))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No tests found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<List<TestDtoResponse>> getAll() {
        var tests = testService.getAll();
        if (tests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                tests,
                HttpStatus.OK
        );
    }

    @GetMapping("{page}/{page_size}")
    @Operation(summary = "Get all tests by page data and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Tests successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No tests found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<PageResponse<TestDtoResponse>> getAllSpecialitiesByPageRequest(
            @PathVariable(name = "page") int page,
            @PathVariable(name = "page_size") int pageSize,
            @RequestBody TestFilter testFilter
    ) {
        var testsPageResponse = testService.getAllByPageAndFilter(page, pageSize, testFilter);
        if (testsPageResponse.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                testsPageResponse,
                HttpStatus.OK
        );
    }
}
