package com.guttery.madii.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pagination<T> {
    private final List<T> content = new ArrayList<>();
    private final long total;
    private final Pageable pageable;
    private final long totalPages;

    public Pagination(List<T> content, long total, Pageable pageable) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = total;
        this.totalPages = pageable.getPageSize() == 0 ? 1 : (int) Math.ceil(total / (double) pageable.getPageSize());
    }

    @JsonProperty("hasPrevious")
    public boolean hasPrevious() {
        return this.pageable.getPageNumber() > 0;
    }

    @JsonProperty("hasNext")
    public boolean hasNext() {
        return this.pageable.getPageNumber() + 1 < getTotalPages();
    }

}
