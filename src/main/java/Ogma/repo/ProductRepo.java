package Ogma.repo;

import Ogma.domain.Product;
import Ogma.domain.ProductType;
import com.sun.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, ID> {
    Optional<Product> findProductById(Long id);

    List<Product> findAllByTypesContains(ProductType type);

    Product findProductByTypesContainsAndId(ProductType type, Long id);
}
