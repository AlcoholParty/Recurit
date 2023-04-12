package com.study.soju.repository;

import com.study.soju.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayRepository extends JpaRepository<Pay, Object> {
    List<Pay> findByBuyerEmail(String buyerEmail); //결제 항목들을 리스트로 이메일이 일치하는 모든값을 가져가기
    Pay findByImpUid(String impUid); //impUid 로 일치하는 결제 항목 가져가기

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM Pay WHERE itemName=:itemName", nativeQuery = true)
    List<Pay> commentList(@Param("itemName") String itemName);
}
