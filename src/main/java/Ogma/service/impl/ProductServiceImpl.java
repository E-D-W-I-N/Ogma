package Ogma.service.impl;

import Ogma.domain.Product;
import Ogma.domain.ProductType;
import Ogma.repo.ProductRepo;
import Ogma.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public boolean addProduct(Product product, Map<String, String> form) {

        try {
            Set<String> types = Arrays.stream(ProductType.values())
                    .map(ProductType::name)
                    .collect(Collectors.toSet());

            Set<ProductType> typeSet = new HashSet<>();
            for (String key : form.keySet()) {
                if (types.contains(key)) {
                    typeSet.add(ProductType.valueOf(key));
                    product.setTypes(typeSet);
                }
            }
            productRepo.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findAllByTypesContains(ProductType type) {
        return productRepo.findAllByTypesContains(type);
    }

    @Override
    public Product findProductByTypesContainsAndId(ProductType type, Long id) {
        return productRepo.findProductByTypesContainsAndId(type, id);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepo.findProductById(id);
    }
}
