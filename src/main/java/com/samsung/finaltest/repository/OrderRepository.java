package com.samsung.finaltest.repository;

import com.samsung.finaltest.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
