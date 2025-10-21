package com.example.productdb.repository;

import com.example.productdb.entity.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Long> {
    
    @Query("SELECT pt FROM ProductTranslation pt WHERE pt.language.id = :languageId")
    List<ProductTranslation> findByLanguageId(@Param("languageId") String languageId);
    
    @Query("SELECT pt FROM ProductTranslation pt WHERE pt.product.id = :productId AND pt.language.id = :languageId")
    ProductTranslation findByProductIdAndLanguageId(@Param("productId") Long productId, @Param("languageId") String languageId);
}