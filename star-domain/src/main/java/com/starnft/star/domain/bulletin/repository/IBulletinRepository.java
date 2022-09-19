package com.starnft.star.domain.bulletin.repository;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;

import java.util.List;

/**
 * @Date 2022/7/25 6:49 PM
 * @Author ： shellya
 */
public interface IBulletinRepository {

    public ResponsePageResult<BulletinVo> pageBulletin(BulletinPageDto dto);

    BulletinVo queryBulletinById(Long id);
}
