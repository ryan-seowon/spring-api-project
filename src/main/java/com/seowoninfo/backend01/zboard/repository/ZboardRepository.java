package com.seowoninfo.backend01.zboard.repository;

import com.seowoninfo.backend01.zboard.entity.Zboard;
import com.seowoninfo.backend01.zboard.repository.custom.ZboardCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  게시판
 */
public interface ZboardRepository extends JpaRepository<Zboard, Long>, ZboardCustom {

}
