package com.starnft.star.infrastructure.repository;

import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.draw.model.aggregates.StrategyRich;
import com.starnft.star.domain.draw.model.vo.AwardBriefVO;
import com.starnft.star.domain.draw.model.vo.StrategyBriefVO;
import com.starnft.star.domain.draw.model.vo.StrategyDetailBriefVO;
import com.starnft.star.domain.draw.repository.IStrategyRepository;
import com.starnft.star.infrastructure.entity.draw.Award;
import com.starnft.star.infrastructure.entity.draw.Strategy;
import com.starnft.star.infrastructure.entity.draw.StrategyDetail;
import com.starnft.star.infrastructure.mapper.draw.IAwardDao;
import com.starnft.star.infrastructure.mapper.draw.IStrategyDao;
import com.starnft.star.infrastructure.mapper.draw.IStrategyDetailDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 策略表仓储服务
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.queryStrategyDetailList(strategyId);

        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        strategyBriefVO.setStrategyId(strategy.getStrategyId());
        strategyBriefVO.setStrategyDesc(strategy.getStrategyDesc());
        strategyBriefVO.setStrategyMode(strategy.getStrategyMode());
        strategyBriefVO.setGrantType(strategy.getGrantType());
        strategyBriefVO.setGrantDate(strategy.getGrantDate());
        strategyBriefVO.setExtInfo(strategy.getExtInfo());

        List<StrategyDetailBriefVO> strategyDetailBriefVOList = new ArrayList<>();
        for (StrategyDetail strategyDetail : strategyDetailList) {
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            strategyDetailBriefVO.setStrategyId(strategyDetail.getStrategyId());
            strategyDetailBriefVO.setAwardId(strategyDetail.getAwardId());
            strategyDetailBriefVO.setAwardName(strategyDetail.getAwardName());
            strategyDetailBriefVO.setAwardCount(strategyDetail.getAwardCount());
            strategyDetailBriefVO.setAwardSurplusCount(strategyDetail.getAwardSurplusCount());
            strategyDetailBriefVO.setAwardRate(strategyDetail.getAwardRate());
            strategyDetailBriefVOList.add(strategyDetailBriefVO);
        }

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOList);
    }

    @Override
    public AwardBriefVO queryAwardInfo(String awardId) {

        Award award = awardDao.queryAwardInfo(awardId);

        // 可以使用 BeanUtils.copyProperties(award, awardBriefVO)、或者基于ASM实现的Bean-Mapping，但在效率上最好的依旧是硬编码
        // 以使用自研 vo2dto 插件，帮助生成 x.set(y.get) 插件安装包：https://github.com/fuzhengwei/guide-idea-plugin/releases/tag/v2.0.2

        AwardBriefVO awardBriefVO = new AwardBriefVO();
        awardBriefVO.setAwardId(award.getAwardId());
        awardBriefVO.setAwardType(award.getAwardType());
        awardBriefVO.setAwardName(award.getAwardName());
        awardBriefVO.setAwardContent(award.getAwardContent());

        return awardBriefVO;
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return strategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        String drawStockKey = String.format(RedisKey.DRAW_AWARD_STOCK_MAPPING.getKey(), strategyId);
        Long stock = redisUtil.hdecr(drawStockKey, awardId, 1L);
        if (stock >= 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
//        StrategyDetail req = new StrategyDetail();
//        req.setStrategyId(strategyId);
//        req.setAwardId(awardId);
//        int count = strategyDetailDao.deductStock(req);
//        return count == 1;
    }

}
