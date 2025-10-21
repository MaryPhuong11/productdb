package com.example.productdb.controller;

import com.example.productdb.entity.Language;
import com.example.productdb.entity.ProductTranslation;
import com.example.productdb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String index(@RequestParam(value = "lang", defaultValue = "EN") String languageId, Model model) {
        // Khởi tạo dữ liệu mẫu
        productService.initializeData();
        
        // Lấy danh sách sản phẩm theo ngôn ngữ
        List<ProductTranslation> products = productService.getProductsByLanguage(languageId);
        
        // Lấy danh sách ngôn ngữ
        List<Language> languages = productService.getAllLanguages();
        
        // Lấy ngôn ngữ hiện tại
        Language currentLanguage = productService.getLanguageById(languageId).orElse(null);
        
        model.addAttribute("products", products);
        model.addAttribute("languages", languages);
        model.addAttribute("currentLanguage", currentLanguage);
        model.addAttribute("currentLanguageId", languageId);
        
        return "index";
    }
    
    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        List<Language> languages = productService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "add-product";
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
    
    @PostMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}