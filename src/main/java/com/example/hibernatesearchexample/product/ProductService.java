package com.example.hibernatesearchexample.product;

import com.example.hibernatesearchexample.search.SearchDataFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductSearchRepository searchRepository;
    private final ProductRepository productRepository;

    public List<ProductDTO> findProducts(List<SearchDataFilter> filters) {
        return this.searchRepository.searchProducts(filters).stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return this.productRepository.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setProductionYear(productDTO.getProductionYear());
                    return this.productRepository.save(product).toDTO();
                })
                .orElseGet(() -> productRepository.save(productDTO.toEntity()).toDTO());
    }

}
