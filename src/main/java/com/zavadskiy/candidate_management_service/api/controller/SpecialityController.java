package com.zavadskiy.candidate_management_service.api.controller;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.CreateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityFilter;
import com.zavadskiy.candidate_management_service.service.dto.speciality.UpdateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.ApiError;
import com.zavadskiy.candidate_management_service.service.interfaces.ISpecialityService;
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

import static com.zavadskiy.candidate_management_service.api.utils.UrlPath.SPECIALITIES;

@RestController
@RequiredArgsConstructor
@RequestMapping(SPECIALITIES)
@Tag(name = "Speciality controller",
        description = "Operations with specialities")
public class SpecialityController {
    private final ISpecialityService specialityService;

    @PostMapping
    @Operation(summary = "Create new speciality",
            description = "Creates and returns new speciality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Speciality successfully created",
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
    public ResponseEntity<SpecialityDtoResponse> create(
            @Validated @RequestBody CreateSpecialityDtoRequest request
    ) {
        return new ResponseEntity<>(
                specialityService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update speciality by id",
            description = "Updates and returns speciality by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Speciality successfully updated",
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
                    description = "Speciality not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))}
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<SpecialityDtoResponse> update(
            @PathVariable Long id,
            @Validated @RequestBody UpdateSpecialityDtoRequest request
    ) {
        return new ResponseEntity<>(
                specialityService.update(id, request),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Get all specialities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Specialities successfully returned",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SpecialityDtoResponse.class)))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No specialities found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<List<SpecialityDtoResponse>> getAll() {
        var specialities = specialityService.getAll();
        if (specialities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                specialities,
                HttpStatus.OK
        );
    }

    @GetMapping("{page}/{page_size}")
    @Operation(summary = "Get all specialities by page data and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Specialities successfully returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))}
            ),
            @ApiResponse(responseCode = "204",
                    description = "No specialities found"
            ),
            @ApiResponse(responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    public ResponseEntity<PageResponse<SpecialityDtoResponse>> getAllSpecialitiesByPageRequest(
            @PathVariable(name = "page") int page,
            @PathVariable(name = "page_size") int pageSize,
            @RequestBody SpecialityFilter specialityFilter
    ) {
        var specialitiesPageResponse = specialityService.getAllByPageAndFilter(page, pageSize, specialityFilter);
        if (specialitiesPageResponse.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(
                specialitiesPageResponse,
                HttpStatus.OK
        );
    }
}
