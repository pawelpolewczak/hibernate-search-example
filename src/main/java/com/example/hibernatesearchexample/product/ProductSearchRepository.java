package com.example.hibernatesearchexample.product;

import com.example.hibernatesearchexample.search.SearchDataFilter;

import java.util.List;

interface ProductSearchRepository {

    List<Product> searchProducts(List<SearchDataFilter> filters);
}
