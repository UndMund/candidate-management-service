package com.zavadskiy.candidate_management_service.service.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.metadata = new Metadata(page.getNumber(), page.getSize(), page.hasNext());
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        boolean nextPagePresent;
    }
}
