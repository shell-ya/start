package com.starnft.star.domain.bulletin;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;

import java.util.List;

/**
 * @Date 2022/7/25 6:48 PM
 * @Author ： shellya
 */
public interface IBulletinService {


    ResponsePageResult<BulletinVo> queryBulletinList(BulletinPageDto bulletinPageDto);

    BulletinVo getBulletinDetail(Long id);
}
