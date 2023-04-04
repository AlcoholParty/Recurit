package com.study.soju.repository;

import com.study.soju.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Object> {
    List<Store> findByCategory(String category);
    Store findByItemName(String itemName);
}
