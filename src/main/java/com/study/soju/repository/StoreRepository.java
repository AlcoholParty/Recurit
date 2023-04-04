package com.study.soju.repository;

import com.study.soju.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Object> {
    List<Store> findByCategory(String category);
    Store findByItemName(String itemName);

    Long countByGoods(String goods);

    @Modifying
    @Query(value = "SELECT * FROM (SELECT *, RANK() OVER (ORDER BY storeIdx DESC)AS ranking FROM Store WHERE goods=:goods)AS ranking WHERE ranking BETWEEN :start AND :end", nativeQuery = true)
    List<Store> findStoreList(@Param("start") int start, @Param("end") int end, @Param("goods") String goods);
}
