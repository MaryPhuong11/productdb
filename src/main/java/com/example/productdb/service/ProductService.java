package com.example.productdb.service;

import com.example.productdb.entity.Language;
import com.example.productdb.entity.Product;
import com.example.productdb.entity.ProductTranslation;
import com.example.productdb.repository.LanguageRepository;
import com.example.productdb.repository.ProductRepository;
import com.example.productdb.repository.ProductTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductTranslationRepository productTranslationRepository;
    
    @Autowired
    private LanguageRepository languageRepository;
    
    // Lấy tất cả sản phẩm theo ngôn ngữ
    public List<ProductTranslation> getProductsByLanguage(String languageId) {
        return productTranslationRepository.findByLanguageId(languageId);
    }
    
    // Lấy tất cả ngôn ngữ
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
    
    // Lấy ngôn ngữ theo ID
    public Optional<Language> getLanguageById(String id) {
        return languageRepository.findById(id);
    }
    
    // Thêm sản phẩm mới
    public Product addProduct(BigDecimal price, String productNameEn, String descriptionEn, 
                             String productNameVn, String descriptionVn) {
        // Tạo sản phẩm
        Product product = new Product(price);
        product = productRepository.save(product);
        
        // Tạo bản dịch tiếng Anh
        Language languageEn = languageRepository.findById("EN").orElse(null);
        if (languageEn != null) {
            ProductTranslation translationEn = new ProductTranslation(product, languageEn, productNameEn, descriptionEn);
            productTranslationRepository.save(translationEn);
        }
        
        // Tạo bản dịch tiếng Việt
        Language languageVn = languageRepository.findById("VN").orElse(null);
        if (languageVn != null) {
            ProductTranslation translationVn = new ProductTranslation(product, languageVn, productNameVn, descriptionVn);
            productTranslationRepository.save(translationVn);
        }
        
        return product;
    }
    
    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    // Khởi tạo dữ liệu mẫu
    public void initializeData() {
        // Kiểm tra xem đã có dữ liệu chưa
        if (languageRepository.count() == 0) {
            // Thêm ngôn ngữ
            Language en = new Language("EN", "English");
            Language vn = new Language("VN", "Tiếng Việt");
            languageRepository.save(en);
            languageRepository.save(vn);
            
            // Thêm sản phẩm mẫu
            addProduct(new BigDecimal("29.99"), "Laptop", "High-performance laptop", "Máy tính xách tay", "Máy tính xách tay hiệu suất cao");
            addProduct(new BigDecimal("19.99"), "Mouse", "Wireless mouse", "Chuột", "Chuột không dây");
            addProduct(new BigDecimal("99.99"), "Keyboard", "Mechanical keyboard", "Bàn phím", "Bàn phím cơ học");
        }
    }
}