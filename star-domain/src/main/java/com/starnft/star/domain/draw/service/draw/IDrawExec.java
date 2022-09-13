package com.starnft.star.domain.draw.service.draw;


import com.starnft.star.common.Result;
import com.starnft.star.common.page.ResponsePageResult;
import com.starnft.star.domain.activity.model.vo.DrawActivityVO;
import com.starnft.star.domain.activity.model.vo.DrawOrderVO;
import com.starnft.star.domain.draw.model.req.DrawAwardExportsReq;
import com.starnft.star.domain.draw.model.req.DrawReq;
import com.starnft.star.domain.draw.model.req.PartakeReq;
import com.starnft.star.domain.draw.model.res.DrawResult;
import com.starnft.star.domain.draw.model.vo.DrawAwardExportVO;

/**
 * @description: 抽奖执行接口
 */
public interface IDrawExec {

    /**
     * 抽奖方法
     *
     * @param req 抽奖参数；用户ID、策略ID
     * @return 中奖结果
     */
    DrawResult doDrawExec(DrawReq req);

    /**
     * 领取活动
     *
     * @param partakeReq
     * @return
     */
    DrawActivityVO getDrawActivity(PartakeReq partakeReq);

    /**
     * 保存奖品单
     *
     * @param drawOrder 奖品单
     * @return 保存结果
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);


    /**
     * 更新消息发送状态
     *
     * @param uId
     * @param orderId
     * @param mqState
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);

    /**
     * 更新发奖状态
     *
     * @param uId
     * @param orderId
     * @param awardId
     * @param grantState
     */
    void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState);


    /**
     * 查询中奖记录
     *
     * @param req
     * @return
     */
    ResponsePageResult<DrawAwardExportVO> queryDrawRecords(DrawAwardExportsReq req);

    /**
     * 查询中奖次数
     *
     * @param req
     * @return
     */
    Integer drawTimes(DrawAwardExportsReq req);

}
