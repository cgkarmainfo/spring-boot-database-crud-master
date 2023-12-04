package com.example.retailapp.service;

import com.example.retailapp.entity.ApprovalQueue;
import com.example.retailapp.entity.Product;
import com.example.retailapp.repository.ApprovalQueueRepository;
import com.example.retailapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.math.BigDecimal;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApprovalQueueRepository approvalQueueRepository;

    @Override
    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc("active");
    }

    @Override
    public List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, Date minPostedDate, Date maxPostedDate) {
        // Implement search logic based on criteria
        return productRepository.searchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
    }

    @Override
    public Product createProduct(Product product) {
        // Implement create logic with price validation
        validateProductPrice(product.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        // Implement update logic with price validation
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BigDecimal newPrice = product.getPrice();
        BigDecimal existingPrice = existingProduct.getPrice();

        if (newPrice != null && existingPrice != null) {
            BigDecimal priceDifference = newPrice.subtract(existingPrice);
            BigDecimal priceThreshold = existingPrice.multiply(new BigDecimal("0.5"));

            if (priceDifference.compareTo(priceThreshold) > 0) {
                // Price change exceeds 50%, push to approval queue
                pushToApprovalQueue(existingProduct);
            }
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(newPrice);
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        // Implement delete logic
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        pushToApprovalQueue(product);
        productRepository.delete(product);
    }

    @Override
    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueRepository.findAllByOrderByRequestDateAsc();
    }

    @Override
    public void approveProduct(Long approvalId) {
        // Implement approval logic
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        Product product = productRepository.findById(approvalQueue.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus("active");
        productRepository.save(product);
        approvalQueueRepository.delete(approvalQueue);
    }

    @Override
    public void rejectProduct(Long approvalId) {
        // Implement rejection logic
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId)
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        approvalQueueRepository.delete(approvalQueue);
    }

    private void validateProductPrice(BigDecimal price) {
        if (price != null && price.compareTo(new BigDecimal("10000")) > 0) {
            throw new RuntimeException("Price cannot exceed $10,000");
        }

        if (price != null && price.compareTo(new BigDecimal("5000")) > 0) {
            // Price exceeds $5,000, push to approval queue
            pushToApprovalQueue(new Product(null, null, price, null, null));
        }
    }

    private void pushToApprovalQueue(Product product) {
        ApprovalQueue approvalQueue = new ApprovalQueue();
        approvalQueue.setProductId(product.getId());
        approvalQueue.setRequestDate(new Date());
        approvalQueueRepository.save(approvalQueue);
    }
}
