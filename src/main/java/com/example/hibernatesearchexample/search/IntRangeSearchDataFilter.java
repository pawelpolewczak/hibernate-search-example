package com.example.hibernatesearchexample.search;

import java.util.Objects;

public record IntRangeSearchDataFilter(String field, Integer min, Integer max) implements SearchDataFilter {

    @Override
    public boolean isValidFilter() {
        return Objects.nonNull(min) && Objects.nonNull(max) && min != 0 && max != 0;
    }

}
