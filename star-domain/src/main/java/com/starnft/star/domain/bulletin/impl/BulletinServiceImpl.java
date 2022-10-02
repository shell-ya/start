package com.starnft.star.domain.bulletin.impl;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.KeyConvertor;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.bulletin.IBulletinService;
import com.starnft.star.domain.bulletin.model.dto.BulletinPageDto;
import com.starnft.star.domain.bulletin.model.vo.BulletinTypeVo;
import com.starnft.star.domain.bulletin.model.vo.BulletinVo;
import com.starnft.star.domain.bulletin.repository.IBulletinRepository;
import com.starnft.star.domain.number.model.vo.DingBulletinVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Date 2022/7/25 6:48 PM
 * @Author ： shellya
 */
@Slf4j
@Service
public class BulletinServiceImpl implements IBulletinService {

    @Resource
    IBulletinRepository bulletinRepository;

    @Override
    @Cached(name = StarConstants.BULLETIN_LIST_CACHE_NAME,
            expire = 3,
            cacheType = CacheType.REMOTE,
            timeUnit = TimeUnit.MINUTES,
            keyConvertor = KeyConvertor.NONE)
    @CacheRefresh(refresh = 2,timeUnit = TimeUnit.MINUTES,stopRefreshAfterLastAccess = 3)
    public ResponsePageResult<BulletinVo> queryBulletinList(BulletinPageDto dto) {
        return bulletinRepository.pageBulletin(dto);
    }

    @Override
    @Cached(name = StarConstants.BULLETIN_CACHE_NAME,
            expire = 3,
            cacheType = CacheType.REMOTE,
            timeUnit = TimeUnit.MINUTES,
            keyConvertor = KeyConvertor.FASTJSON)
    @CacheRefresh(refresh = 2,timeUnit = TimeUnit.MINUTES,stopRefreshAfterLastAccess = 3)
    public BulletinVo getBulletinDetail(Long id) {
        BulletinVo bulletinVo = bulletinRepository.queryBulletinById(id);

        Optional.ofNullable(bulletinVo)
                .orElseThrow(() -> new StarException(StarError.BULLETIN_NOT_FOUND));

        return bulletinVo;
    }

    @Override
    public List<BulletinTypeVo> queryBulletinType() {
        //取出枚举封装
        return  Arrays.stream(StarConstants.BulletinType.values()).map(this::bulletinTypeVo).collect(Collectors.toList());
    }

    @Override
    public List<DingBulletinVo> queryDingBulletin() {
        return bulletinRepository.selectDingBulletin();
    }

    private BulletinTypeVo bulletinTypeVo(StarConstants.BulletinType bulletinType){
        return new BulletinTypeVo(bulletinType.getType(),bulletinType.getVal());
    }
}
