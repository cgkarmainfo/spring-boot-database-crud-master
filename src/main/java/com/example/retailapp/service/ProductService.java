package com.example.retailapp.service;

import com.example.retailapp.entity.ApprovalQueue;
import com.example.retailapp.entity.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> listActiveProducts();
    List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, Date minPostedDate, Date maxPostedDate);
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
    List<ApprovalQueue> getProductsInApprovalQueue();
    void approveProduct(Long approvalId);
    void rejectProduct(Long approvalId);
}
