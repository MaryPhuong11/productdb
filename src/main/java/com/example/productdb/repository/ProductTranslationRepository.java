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
    
    // Tìm kiếm sản phẩm theo tên hoặc mô tả trong ngôn ngữ cụ thể
    @Query("SELECT pt FROM ProductTranslation pt WHERE pt.language.id = :languageId AND " +
           "(LOWER(pt.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(pt.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<ProductTranslation> findByLanguageIdAndProductNameOrDescriptionContainingIgnoreCase(
        @Param("languageId") String languageId, 
        @Param("searchTerm") String searchTerm);
    
    // Tìm kiếm sản phẩm theo tên trong ngôn ngữ cụ thể
    @Query("SELECT pt FROM ProductTranslation pt WHERE pt.language.id = :languageId AND " +
           "LOWER(pt.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ProductTranslation> findByLanguageIdAndProductNameContainingIgnoreCase(
        @Param("languageId") String languageId, 
        @Param("searchTerm") String searchTerm);
    
    // Tìm kiếm sản phẩm theo mô tả trong ngôn ngữ cụ thể
    @Query("SELECT pt FROM ProductTranslation pt WHERE pt.language.id = :languageId AND " +
           "LOWER(pt.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ProductTranslation> findByLanguageIdAndDescriptionContainingIgnoreCase(
        @Param("languageId") String languageId, 
        @Param("searchTerm") String searchTerm);
    
    // Tìm kiếm sản phẩm trong tất cả ngôn ngữ
    @Query("SELECT pt FROM ProductTranslation pt WHERE " +
           "LOWER(pt.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(pt.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<ProductTranslation> findByProductNameOrDescriptionContainingIgnoreCase(@Param("searchTerm") String searchTerm);
}