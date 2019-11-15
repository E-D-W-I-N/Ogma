package Ogma.controller;

import Ogma.domain.Product;
import Ogma.domain.ProductType;
import Ogma.service.ProductService;
import Ogma.service.impl.ProductServiceImpl;
import Ogma.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("catalog/products")
public class ProductController {
    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String products(Model model) {
        model.addAttribute("types", ProductType.values());
        model.addAttribute("products", productService.findAllProducts());
        return "products";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("types", ProductType.values());
        model.addAttribute("products", productService.findAllByTypesContains(ProductType.BOOK));
        return "products";
    }

    @GetMapping("/books/{bookId}")
    public String book(@PathVariable Long bookId, Model model) {
        model.addAttribute("product", productService.findProductByTypesContainsAndId(ProductType.BOOK, bookId));
        return "productPage";
    }

    @GetMapping("/office-supplies")
    public String officeSupplies(Model model) {
        model.addAttribute("types", ProductType.values());
        model.addAttribute("products", productService.findAllByTypesContains(ProductType.OFFICE_SUPPLY));
        return "products";
    }

    @GetMapping("/office-supplies/{supplyId}")
    public String officeSupply(@PathVariable Long supplyId, Model model) {
        model.addAttribute("product", productService.findProductByTypesContainsAndId(ProductType.OFFICE_SUPPLY, supplyId));
        return "productPage";
    }

    @GetMapping("/souvenirs")
    public String souvenirs(Model model) {
        model.addAttribute("types", ProductType.values());
        model.addAttribute("products", productService.findAllByTypesContains(ProductType.SOUVENIR));
        return "products";
    }

    @GetMapping("/souvenirs/{souvenirId}")
    public String souvenir(@PathVariable Long souvenirId, Model model) {
        model.addAttribute("product", productService.findProductByTypesContainsAndId(ProductType.SOUVENIR, souvenirId));
        return "productPage";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public String add(
            @RequestParam("file") MultipartFile file,
            @RequestParam Map<String, String> form,
            @Valid Product product,
            Model model) throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdirs();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            product.setFilename(resultFilename);
        }


        if (productService.addProduct(product, form)) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Товар успешно добавлена");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Ошибка, товар не был добавлен");
        }

        Iterable<Product> products = productService.findAllByTypesContains(product.getTypes().stream().findFirst().get());
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> usersReport() {
        List<Product> products = productService.findAllProducts();
        String title = "Книги";
        List<String> tableHeader = Arrays.asList("ID", "Название", "Описание", "Цена", "Вес", "Дата выпуска");
        List content = new ArrayList();
        for (Product product : products) {
            content.add(Arrays.asList(product.getId().toString(), product.getName(), product.getDescription(),
                    product.getPrice().toString(), product.getWeight().toString(), product.getDate().toString()));
        }

        ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=products.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
