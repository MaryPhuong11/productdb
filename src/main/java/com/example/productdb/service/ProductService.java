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
    
    // Tìm kiếm sản phẩm theo từ khóa trong ngôn ngữ cụ thể
    public List<ProductTranslation> searchProductsByLanguage(String languageId, String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getProductsByLanguage(languageId);
        }
        return productTranslationRepository.findByLanguageIdAndProductNameOrDescriptionContainingIgnoreCase(languageId, searchTerm.trim());
    }
    
    // Tìm kiếm sản phẩm trong tất cả ngôn ngữ
    public List<ProductTranslation> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return productTranslationRepository.findAll();
        }
        return productTranslationRepository.findByProductNameOrDescriptionContainingIgnoreCase(searchTerm.trim());
    }
    
    // Tìm kiếm sản phẩm theo tên trong ngôn ngữ cụ thể
    public List<ProductTranslation> searchProductsByName(String languageId, String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return getProductsByLanguage(languageId);
        }
        return productTranslationRepository.findByLanguageIdAndProductNameContainingIgnoreCase(languageId, productName.trim());
    }
    
    // Tìm kiếm sản phẩm theo mô tả trong ngôn ngữ cụ thể
    public List<ProductTranslation> searchProductsByDescription(String languageId, String description) {
        if (description == null || description.trim().isEmpty()) {
            return getProductsByLanguage(languageId);
        }
        return productTranslationRepository.findByLanguageIdAndDescriptionContainingIgnoreCase(languageId, description.trim());
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
    
    // Thêm ngôn ngữ mới
    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }
    
    // Xóa ngôn ngữ
    public void deleteLanguage(String languageId) {
        // Kiểm tra xem có sản phẩm nào đang sử dụng ngôn ngữ này không
        List<ProductTranslation> translations = productTranslationRepository.findByLanguageId(languageId);
        if (!translations.isEmpty()) {
            throw new RuntimeException("Cannot delete language. There are products using this language.");
        }
        languageRepository.deleteById(languageId);
    }
    
    // Cập nhật sản phẩm với tất cả ngôn ngữ
    public Product addProductWithAllLanguages(BigDecimal price, List<ProductTranslation> translations) {
        // Tạo sản phẩm
        Product product = new Product(price);
        product = productRepository.save(product);
        
        // Tạo bản dịch cho tất cả ngôn ngữ
        for (ProductTranslation translation : translations) {
            translation.setProduct(product);
            productTranslationRepository.save(translation);
        }
        
        return product;
    }
    
    // Khởi tạo dữ liệu mẫu
    public void initializeData() {
        // Kiểm tra xem đã có dữ liệu chưa
        if (languageRepository.count() == 0) {
            // Thêm ngôn ngữ
            Language en = new Language("EN", "English");
            Language vn = new Language("VN", "Tiếng Việt");
            Language fr = new Language("FR", "Français");
            Language de = new Language("DE", "Deutsch");
            Language es = new Language("ES", "Español");
            Language ja = new Language("JA", "日本語");
            Language ko = new Language("KO", "한국어");
            Language zh = new Language("ZH", "中文");
            
            languageRepository.save(en);
            languageRepository.save(vn);
            languageRepository.save(fr);
            languageRepository.save(de);
            languageRepository.save(es);
            languageRepository.save(ja);
            languageRepository.save(ko);
            languageRepository.save(zh);
            
            // Thêm sản phẩm mẫu
            addProduct(new BigDecimal("29.99"), "Laptop", "High-performance laptop", "Máy tính xách tay", "Máy tính xách tay hiệu suất cao");
            addProduct(new BigDecimal("19.99"), "Mouse", "Wireless mouse", "Chuột", "Chuột không dây");
            addProduct(new BigDecimal("99.99"), "Keyboard", "Mechanical keyboard", "Bàn phím", "Bàn phím cơ học");
        }
    }
}