package com.example.hibernatesearchexample;

import com.example.hibernatesearchexample.product.ProductDTO;
import com.example.hibernatesearchexample.product.ProductService;
import com.example.hibernatesearchexample.search.IntRangeSearchDataFilter;
import com.example.hibernatesearchexample.search.KeywordSearchDataFilter;
import com.example.hibernatesearchexample.search.SearchDataFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService searchService;

    @GetMapping
    public List<ProductDTO> searchProducts(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) Integer minYear,
                                           @RequestParam(required = false) Integer maxYear) {
        List<SearchDataFilter> filters = List.of(
                new KeywordSearchDataFilter("name", name),
                new KeywordSearchDataFilter("description", description),
                new IntRangeSearchDataFilter("productionYear", minYear, maxYear)
        );
        return searchService.findProducts(filters);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return this.searchService.updateProduct(id, productDTO);
    }

}
