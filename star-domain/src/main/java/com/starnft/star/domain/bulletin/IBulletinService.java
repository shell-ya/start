package com.starnft.star.domain.bulletin;

import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinTypeVo;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;
import com.starnft.star.domain.number.model.vo.DingBulletinVo;

import java.util.List;

/**
 * @Date 2022/7/25 6:48 PM
 * @Author ： shellya
 */
public interface IBulletinService {


    ResponsePageResult<BulletinVo> queryBulletinList(BulletinPageDto bulletinPageDto);

    BulletinVo getBulletinDetail(Long id);

    List<BulletinTypeVo> queryBulletinType();

    List<DingBulletinVo> queryDingBulletin();
}
