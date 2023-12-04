package com.example.retailapp.repository;

import com.example.retailapp.entity.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Long> {
    List<ApprovalQueue> findAllByOrderByRequestDateAsc();
}
