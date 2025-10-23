package com.example.productdb.controller;

import com.example.productdb.entity.Language;
import com.example.productdb.entity.ProductTranslation;
import com.example.productdb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String index(@RequestParam(value = "lang", defaultValue = "EN") String languageId, 
                       @RequestParam(value = "search", required = false) String searchTerm,
                       Model model) {
        // Khởi tạo dữ liệu mẫu
        productService.initializeData();
        
        // Lấy danh sách sản phẩm theo ngôn ngữ và từ khóa tìm kiếm
        List<ProductTranslation> products;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            products = productService.searchProductsByLanguage(languageId, searchTerm);
        } else {
            products = productService.getProductsByLanguage(languageId);
        }
        
        // Lấy danh sách ngôn ngữ
        List<Language> languages = productService.getAllLanguages();
        
        // Lấy ngôn ngữ hiện tại
        Language currentLanguage = productService.getLanguageById(languageId).orElse(null);
        
        model.addAttribute("products", products);
        model.addAttribute("languages", languages);
        model.addAttribute("currentLanguage", currentLanguage);
        model.addAttribute("currentLanguageId", languageId);
        model.addAttribute("searchTerm", searchTerm);
        
        return "index";
    }
    
    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        List<Language> languages = productService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "add-product";
    }
    
    @GetMapping("/add-product-multi")
    public String addProductMultiForm(Model model) {
        List<Language> languages = productService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "add-product-multi";
    }
    
    @PostMapping("/add-product")
    public String addProduct(@RequestParam BigDecimal price,
                           @RequestParam String productNameEn,
                           @RequestParam String descriptionEn,
                           @RequestParam String productNameVn,
                           @RequestParam String descriptionVn) {
        productService.addProduct(price, productNameEn, descriptionEn, productNameVn, descriptionVn);
        return "redirect:/";
    }
    
    @PostMapping("/add-product-multi")
    public String addProductMulti(@RequestParam BigDecimal price,
                                @RequestParam java.util.Map<String, String> translations) {
        List<ProductTranslation> productTranslations = new ArrayList<>();
        List<Language> languages = productService.getAllLanguages();
        
        for (Language language : languages) {
            String productNameKey = "productName_" + language.getId();
            String descriptionKey = "description_" + language.getId();
            
            if (translations.containsKey(productNameKey) && 
                !translations.get(productNameKey).trim().isEmpty()) {
                String productName = translations.get(productNameKey);
                String description = translations.getOrDefault(descriptionKey, "");
                
                ProductTranslation translation = new ProductTranslation();
                translation.setLanguage(language);
                translation.setProductName(productName);
                translation.setDescription(description);
                productTranslations.add(translation);
            }
        }
        
        if (!productTranslations.isEmpty()) {
            productService.addProductWithAllLanguages(price, productTranslations);
        }
        
        return "redirect:/";
    }
    
    @PostMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
    
    @GetMapping("/help")
    public String help() {
        return "help";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String query,
                        @RequestParam(value = "lang", defaultValue = "EN") String languageId,
                        Model model) {
        // Khởi tạo dữ liệu mẫu
        productService.initializeData();
        
        // Tìm kiếm sản phẩm
        List<ProductTranslation> products;
        if (query != null && !query.trim().isEmpty()) {
            products = productService.searchProductsByLanguage(languageId, query);
        } else {
            products = productService.getProductsByLanguage(languageId);
        }
        
        // Lấy danh sách ngôn ngữ
        List<Language> languages = productService.getAllLanguages();
        
        // Lấy ngôn ngữ hiện tại
        Language currentLanguage = productService.getLanguageById(languageId).orElse(null);
        
        model.addAttribute("products", products);
        model.addAttribute("languages", languages);
        model.addAttribute("currentLanguage", currentLanguage);
        model.addAttribute("currentLanguageId", languageId);
        model.addAttribute("searchTerm", query);
        model.addAttribute("isSearchPage", true);
        
        return "index";
    }
}