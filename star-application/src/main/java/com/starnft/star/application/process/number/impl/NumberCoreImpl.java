package com.starnft.star.application.process.number.impl;

import com.starnft.star.application.mq.producer.marketorder.MarketOrderProducer;
import com.starnft.star.application.process.number.INumberCore;
import com.starnft.star.application.process.number.req.MarketOrderReq;
import com.starnft.star.application.process.number.req.MarketOrderStatus;
import com.starnft.star.application.process.number.res.ConsignDetailRes;
import com.starnft.star.application.process.number.res.MarketOrderRes;
import com.starnft.star.application.process.order.model.dto.OrderMessageReq;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.page.RequestConditionPage;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.common.utils.Assert;
import com.starnft.star.domain.component.RedisLockUtils;
import com.starnft.star.domain.component.RedisUtil;
import com.starnft.star.domain.number.model.req.NumberConsignmentRequest;
import com.starnft.star.domain.number.model.req.NumberQueryRequest;
import com.starnft.star.domain.number.model.vo.NumberDetailVO;
import com.starnft.star.domain.number.model.vo.NumberVO;
import com.starnft.star.domain.number.model.vo.ThemeNumberVo;
import com.starnft.star.domain.number.serivce.INumberService;
import com.starnft.star.domain.order.model.vo.OrderVO;
import com.starnft.star.domain.order.service.IOrderService;
import com.starnft.star.domain.support.ids.IIdGenerator;
import com.starnft.star.domain.wallet.model.req.WalletInfoReq;
import com.starnft.star.domain.wallet.model.res.WalletResult;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.service.WalletConfig;
import com.starnft.star.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.starnft.star.common.exception.StarError.BALANCE_NOT_ENOUGH;

/**
 * @author Harlan
 * @date 2022/06/14 22:57
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NumberCoreImpl implements INumberCore {

    private final INumberService numberService;
    private final WalletService walletService;
    private final IOrderService orderService;
    private final RedisUtil redisUtil;
    private final RedisLockUtils redisLockUtils;
    private final MarketOrderProducer marketOrderProducer;
    @Resource
    private Map<StarConstants.Ids, IIdGenerator> map;
    @Override
    public NumberDetailVO obtainThemeNumberDetail(Long id) {
        return this.numberService.getNumberDetail(id);
    }

    @Override
    public ResponsePageResult<NumberVO> obtainThemeNumberList(RequestConditionPage<NumberQueryRequest> request) {
        return this.numberService.listNumber(request);
    }

    @Override
    public Boolean consignment(Long uid, NumberConsignmentRequest request) {
        return this.numberService.consignment(uid, request);
    }

    @Override
    public Boolean consignmentCancel(Long uid, Long numberId) {
        return this.numberService.consignmentCancel(uid, numberId);
    }

    @Override
    public ConsignDetailRes obtainConsignDetail(Long id) {
        // 获取市场相关费率配置
        WalletConfigVO config = WalletConfig.getConfig(StarConstants.PayChannel.Market);
        Assert.notNull(config, () -> new StarException(StarError.PARAETER_UNSUPPORTED, "市场相关费率配置不存在"));

        // 获取商品信息
        NumberDetailVO numberDetail = this.numberService.getNumberDetail(id);
        return ConsignDetailRes.builder()
                .id(id)
                .number(numberDetail.getNumber())
                .name(numberDetail.getName())
                .picture(numberDetail.getPicture())
                .price(numberDetail.getPrice())
                .copyrightRate(config.getCopyrightRate())
                .serviceRate(config.getServiceRate())
                .build();
    }

    @Override
    public MarketOrderRes marketOrder(MarketOrderReq marketOrderReq) {
        //钱包余额充足
        WalletResult walletResult = walletService.queryWalletInfo(new WalletInfoReq(marketOrderReq.getUserId()));
        ThemeNumberVo numberDetail = numberService.getConsignNumberDetail(marketOrderReq.getNumberId());
        if (walletResult.getBalance().compareTo(numberDetail.getPrice()) < 0){
            throw new StarException(StarError.BALANCE_NOT_ENOUGH);
        }
        //获取锁
        String isTransaction = String.format(RedisKey.MARKET_ORDER_TRANSACTION.getKey(), marketOrderReq.getNumberId());
        if (redisUtil.hasKey(isTransaction)) {
            throw new StarException(StarError.GOODS_NOT_FOUND);
        }
        if (redisLockUtils.lock(isTransaction, 3000L)) {
            try{
                //生成订单
                String orderSn = StarConstants.OrderPrefix.TransactionSn.getPrefix()
                        .concat(String.valueOf(map.get(StarConstants.Ids.SnowFlake).nextId()));
                if(createPreOrder(numberDetail, marketOrderReq.getUserId(),orderSn)){
                    //发送延时队列
                    marketOrderProducer.marketOrderRollback(new MarketOrderStatus(0,orderSn));
                    return new MarketOrderRes(orderSn,0, StarError.SUCCESS_000000.getErrorMessage());
                }
            }catch (Exception e){
                log.error("创建订单异常: userId: [{}] , themeNumberId: [{}] , context: [{}]",marketOrderReq.getUserId(),marketOrderReq.getNumberId(),numberDetail);
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new StarException(StarError.REQUEST_OVERFLOW_ERROR);
    }

    private boolean createPreOrder(ThemeNumberVo numberDetail,Long userId,String orderSn) {
        OrderVO orderVO = OrderVO.builder()
                .userId(userId)
                .orderSn(orderSn)
                .payAmount(numberDetail.getPrice())
                .seriesId(numberDetail.getSeriesId())
                .seriesName(numberDetail.getSeriesName())
                .seriesThemeInfoId(numberDetail.getThemeInfoId())
                .seriesThemeId(numberDetail.getNumberId())
                .themeName(numberDetail.getThemeName())
//                .payAmount(numberDetail.getPrice())
                .themePic(numberDetail.getThemePic())
                .themeType(numberDetail.getThemeType())
                .totalAmount(numberDetail.getPrice())
                .themeNumber(numberDetail.getThemeNumber())
                .themeType(numberDetail.getThemeType())
                .build();
        //创建订单
        return orderService.createOrder(orderVO);
        //        if (isSuccess) {
//            redisUtil.hset(RedisKey.SECKILL_ORDER_USER_MAPPING.getKey(), String.valueOf(message.getUserId()), orderVO);
//            return true;
//        }
//        return false;
    }
}
