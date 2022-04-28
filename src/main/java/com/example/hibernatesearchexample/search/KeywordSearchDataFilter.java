package com.example.hibernatesearchexample.search;

public record KeywordSearchDataFilter(String field, String value) implements SearchDataFilter {

    @Override
    public boolean isValidFilter() {
        return value != null && !value.isEmpty();
    }
}
