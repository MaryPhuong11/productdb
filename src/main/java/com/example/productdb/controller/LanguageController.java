package com.example.productdb.controller;

import com.example.productdb.entity.Language;
import com.example.productdb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String listLanguages(Model model) {
        List<Language> languages = productService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "languages";
    }
    
    @GetMapping("/add")
    public String addLanguageForm(Model model) {
        model.addAttribute("language", new Language());
        return "add-language";
    }
    
    @PostMapping("/add")
    public String addLanguage(@ModelAttribute Language language, RedirectAttributes redirectAttributes) {
        try {
            productService.addLanguage(language);
            redirectAttributes.addFlashAttribute("successMessage", "Language added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding language: " + e.getMessage());
        }
        return "redirect:/languages";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteLanguage(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteLanguage(id);
            redirectAttributes.addFlashAttribute("successMessage", "Language deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting language: " + e.getMessage());
        }
        return "redirect:/languages";
    }
}
