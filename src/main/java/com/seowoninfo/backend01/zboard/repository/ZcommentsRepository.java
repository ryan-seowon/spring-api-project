package com.seowoninfo.backend01.zboard.repository;

import com.seowoninfo.backend01.zboard.entity.Zcomments;
import com.seowoninfo.backend01.zboard.repository.custom.ZcommentsCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 댓글
 */
public interface ZcommentsRepository extends JpaRepository<Zcomments, Long>, ZcommentsCustom {

}
