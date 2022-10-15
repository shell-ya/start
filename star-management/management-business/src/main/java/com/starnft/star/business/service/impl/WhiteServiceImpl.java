package com.starnft.star.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starnft.star.business.domain.AccountUser;
import com.starnft.star.business.domain.WhiteListConfig;
import com.starnft.star.business.domain.WhiteListDetail;
import com.starnft.star.business.domain.vo.WhiteDetailVo;
import com.starnft.star.business.mapper.WhiteListConfigMapper;
import com.starnft.star.business.mapper.WhiteListDetailMapper;
import com.starnft.star.business.service.IWhiteService;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class WhiteServiceImpl implements IWhiteService {

    @Autowired
    private WhiteListDetailMapper whiteListDetailMapper;
    @Autowired
    private WhiteListConfigMapper whiteListConfigMapper;
    @Autowired
    private AccountUserServiceImpl accountUserMapper;

    @Override
    public WhiteListDetail queryWhite(Long whiteId, Long uid) {
        return whiteListDetailMapper.selectMappingWhite(whiteId,uid);
    }

    @Override
    public List<WhiteListDetail> queryCommon(Long whiteId) {
        QueryWrapper<WhiteListDetail> whiteListDetailQueryWrapper = new QueryWrapper<>();
        whiteListDetailQueryWrapper.eq("white_id",whiteId);
        return whiteListDetailMapper.selectList(whiteListDetailQueryWrapper);
    }


    @Override
    public List<WhiteListDetail> queryWhiteList(WhiteListDetail whiteListDetail) {
        LambdaQueryWrapper<WhiteListDetail> whiteListDetailQueryWrapper = new LambdaQueryWrapper<>();
        whiteListDetailQueryWrapper.eq(Objects.nonNull(whiteListDetail.getWhiteId()),WhiteListDetail::getWhiteId,whiteListDetail.getWhiteId());
        whiteListDetailQueryWrapper.eq(Objects.nonNull(whiteListDetail.getUid()),WhiteListDetail::getUid,whiteListDetail.getUid());
        return whiteListDetailMapper.selectList(whiteListDetailQueryWrapper);
    }

    @Override
    public List<WhiteListConfig> queryWhiteConfigList(WhiteListConfig config){
        LambdaQueryWrapper<WhiteListConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WhiteListConfig::getIsDeleted,0L);
        wrapper.eq(Objects.nonNull(config.getEffective()),WhiteListConfig::getEffective,config.getEffective());
        wrapper.eq(Objects.nonNull(config.getGoodsId()),WhiteListConfig::getGoodsId,config.getGoodsId());
        wrapper.orderByDesc(WhiteListConfig::getEndTime);
        return whiteListConfigMapper.selectList(wrapper);
    }

    @Override
    public int insertWhiteConfig(WhiteListConfig whiteListConfig) {

        whiteListConfig.setCreatedAt(new Date());
        whiteListConfig.setIsDeleted(Boolean.FALSE);
        whiteListConfig.setEffective(1);
        return whiteListConfigMapper.insert(whiteListConfig);
    }

    @Override
    public String importWhiteDetail(List<WhiteDetailVo> whiteDetailVos, Long whiteId) {
        //验证白名单id存在
        WhiteListConfig whiteListConfig = whiteListConfigMapper.queryById(whiteId);
        Optional.ofNullable(whiteListConfig).orElseThrow(() -> new StarException("白名单不存在"));

        if (StringUtils.isNull(whiteDetailVos) || whiteDetailVos.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //判断用户已在白名单内 在更新 不在新增
        for (WhiteDetailVo item :
                whiteDetailVos) {
            try{
                //用户不存在跳过
                //验证用户是否存在
                AccountUser accountUser = accountUserMapper.selectUserByAccount(item.getUserId());
                if (Objects.isNull(accountUser)){
                    failureNum++;
                    failureMsg.append("<br/>用户").append(item.getUserId()).append("不存在");
                    continue;
                }

                WhiteListDetail detail = whiteListDetailMapper.selectMappingWhite(whiteId, item.getUserId());
                if (detail == null){
                    detail = new WhiteListDetail();
                    detail.setWhiteId(whiteId);
                    detail.setUid(item.getUserId().intValue());
                    detail.setVersion(0);
                    detail.setSurplusTimes(item.getSurplusTime());
                    detail.setCreatedBy(1L);
                    detail.setIsDeleted(Boolean.FALSE);
                    detail.setCreatedAt(new Date());
                    whiteListDetailMapper.insertSurplus(detail);

                }else {
                    whiteListDetailMapper.addSurplus(whiteId,item.getUserId(),item.getSurplusTime(),detail.getVersion());
                }
                successNum++;
                successMsg.append("<br/>" + successNum + "、用户 " + item.getUserId() + " 导入成功");
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、用户 " + item.getUserId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();

    }
    @Override
    public WhiteListConfig getOneConfig(Long whiteId){
        WhiteListConfig whiteListConfig = whiteListConfigMapper.queryById(whiteId);
        return whiteListConfig;
    }

    @Override
    public int updateWhiteConfig(WhiteListConfig whiteListConfig) {
        return whiteListConfigMapper.updateById(whiteListConfig);
    }

}
