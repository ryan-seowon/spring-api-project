package com.seowoninfo.backend01.zzboard.mapper;

import com.seowoninfo.backend01.zboard.dto.ZboardResponseDto;
import com.seowoninfo.backend01.zboard.dto.ZboardSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * FileName    : IntelliJ IDEA
 * Author      : Seowon
 * Date        : 2025-02-06
 * Description :
 */
@Mapper
public interface ZzboardMapper {
    List<ZboardResponseDto> boardList(ZboardSearchDto zboardSearchDto);
}
