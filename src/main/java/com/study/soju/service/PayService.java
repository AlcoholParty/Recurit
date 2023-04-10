package com.study.soju.service;

import com.study.soju.entity.Member;
import com.study.soju.entity.Pay;
import com.study.soju.entity.Store;
import com.study.soju.entity.StoreLike;
import com.study.soju.repository.MemberRepository;
import com.study.soju.repository.PayRepository;
import com.study.soju.repository.StoreLikeRepository;
import com.study.soju.repository.StoreRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Builder
public class PayService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    PayRepository payRepository;

    @Autowired
    StoreLikeRepository storeLikeRepository;

    //멤버 객체 정보 조회
    public Member findAll(String emailId){
        Member member = memberRepository.findByEmailId(emailId);
        return member;
    }

    //모든 상품 리스트로 끌어오기
    public List<Store> findList(String category){
        List<Store> list = storeRepository.findByCategory(category);
        return list;
    }

    //상품 이름으로 상품정보 가져가기
    public Store findStore(String itemName){
        Store payItem = storeRepository.findByItemName(itemName);
        return payItem;
    }

    //결제 완료된 항목을 데이터베이스에 저장
    public void insertPay(Pay requestPay){
        //결제가 완료 되었으면 0로 저장
        requestPay.setIsPaid(0);
        payRepository.save(requestPay);
    }

    //유저 이메일에 맞는 결제완료된 모든 항목을 반환
    public List<Pay> findPay(String emailId){
        List<Pay> payList = payRepository.findByBuyerEmail(emailId);
        return payList;
    }

    //impUid 에 맞는 항목을 찾아온다음 반환
    public Pay refundCheck(String impUid){
        Pay resPay = payRepository.findByImpUid(impUid);
        return resPay;
    }

    //환불된 항목을 저장
    public void updatePay(Pay pay){
        //결제 완료 된것을 환불로 변경하기 위해서 1로 고정
        pay.setIsPaid(1);
        payRepository.save(pay);
    }

    public int rowTotal(String goods) {
        return Long.valueOf(storeRepository.countByGoods(goods)).intValue();
    }

    public List<Store> storeListAll(HashMap<String, Integer> map, String goods) {
        List<Store> storeList = storeRepository.findStoreList(map.get("start"), map.get("end"), goods);
        return storeList;
    }

    public long returnIdx(String emailId) {
        Member member = memberRepository.findByEmailId(emailId);
        return member.getIdx();
    }
    public int likeCheck(long storeIdx, long memberIdx) {
        int check = 0;
        StoreLike storeLike = storeLikeRepository.findByLikeIdxAndMemberIdx(storeIdx, memberIdx);
        if(storeLike != null) {
            check = 1;
        }
        return check;
    }

    public Store likeUpdate(StoreLike storeLike) {
        StoreLike storeLike1 = storeLikeRepository.findByLikeIdxAndMemberIdx(storeLike.getLikeIdx(), storeLike.getMemberIdx());
        Store afterStore = null;
        if(storeLike1 == null) {
            storeLikeRepository.save(storeLike);
            storeRepository.updateStoreLikeCount(storeLike.getLikeIdx());
            System.out.println(storeLike.getLikeIdx().getClass().getName());
            afterStore = storeRepository.findByStoreIdx(storeLike.getLikeIdx());
        } else {
            storeLikeRepository.delete(storeLike1);
            storeRepository.updateStoreLikeCount(storeLike1.getLikeIdx());
            afterStore = storeRepository.findByStoreIdx(storeLike1.getLikeIdx().intValue());
        }
        return afterStore;
    }

}
