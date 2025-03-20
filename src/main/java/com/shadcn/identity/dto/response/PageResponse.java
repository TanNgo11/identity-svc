package com.shadcn.identity.dto.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class PageResponse<T> implements Serializable {
    int current;
    int totalPages;
    int pageSize;
    long totalElements;
    List<T> data = Collections.emptyList();

    public PageResponse(int currentPage, int pageSize, int totalPages, long totalElements, List<T> data) {
        this.current = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }
}
