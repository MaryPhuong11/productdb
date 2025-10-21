package com.example.productdb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "language")
public class Language {
    
    @Id
    @Column(name = "id", length = 2)
    private String id;
    
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    
    // Constructors
    public Language() {}
    
    public Language(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Language{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}