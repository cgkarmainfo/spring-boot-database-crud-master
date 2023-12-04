package com.example.retailapp.controller;

import com.example.retailapp.entity.ApprovalQueue;
import com.example.retailapp.entity.Product;
import com.example.retailapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> listActiveProducts() {
        return productService.listActiveProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String productName,
                                        @RequestParam(required = false) BigDecimal minPrice,
                                        @RequestParam(required = false) BigDecimal maxPrice,
                                        @RequestParam(required = false) Date minPostedDate,
                                        @RequestParam(required = false) Date maxPostedDate) {
        return productService.searchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/approval-queue")
    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return productService.getProductsInApprovalQueue();
    }

    @PutMapping("/approval-queue/{approvalId}/approve")
    public void approveProduct(@PathVariable Long approvalId) {
        productService.approveProduct(approvalId);
    }

    @PutMapping("/approval-queue/{approvalId}/reject")
    public void rejectProduct(@PathVariable Long approvalId) {
        productService.rejectProduct(approvalId);
    }

}
