package com.study.soju.repository;

import com.study.soju.entity.StoreLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Object> {
    StoreLike findByLikeIdxAndMemberIdx(long likeIdx, long memberIdx);
}
