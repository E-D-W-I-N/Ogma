package Ogma.service;

import Ogma.domain.Product;
import Ogma.domain.ProductType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    boolean addProduct(Product product, Map<String, String> form);

    List<Product> findAllProducts();

    List<Product> findAllByTypesContains(ProductType type);

    Product findProductByTypesContainsAndId(ProductType type, Long id);

    Optional<Product> findProductById(Long id);
}
