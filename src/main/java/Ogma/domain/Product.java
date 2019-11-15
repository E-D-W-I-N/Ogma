package Ogma.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass = ProductType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private Set<ProductType> types;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    private String filename;

    private BigDecimal price;

    private Double weight;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
