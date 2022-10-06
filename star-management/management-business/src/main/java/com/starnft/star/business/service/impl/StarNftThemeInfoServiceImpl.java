package com.starnft.star.business.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.starnft.star.business.domain.StarNftSeries;
import com.starnft.star.business.domain.StarNftThemeInfo;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.vo.StarNftThemeInfoVo;
import com.starnft.star.business.domain.vo.ThemeInfoVo;
import com.starnft.star.business.mapper.StarNftSeriesMapper;
import com.starnft.star.business.mapper.StarNftThemeInfoMapper;
import com.starnft.star.business.mapper.StarNftThemeNumberMapper;
import com.starnft.star.business.service.IStarNftThemeInfoService;
import com.starnft.star.common.chain.TiChainFactory;
import com.starnft.star.common.chain.model.req.PublishGoodsReq;
import com.starnft.star.common.chain.model.res.PublishGoodsRes;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 主题Service业务层处理
 *
 * @author shellya
 * @date 2022-06-03
 */
@Slf4j
@Service
public class StarNftThemeInfoServiceImpl implements IStarNftThemeInfoService
{
    @Autowired
    private StarNftThemeInfoMapper starNftThemeInfoMapper;
    @Autowired
    private StarNftThemeNumberMapper themeNumberMapper;
    @Autowired
    private TiChainFactory tiChainServer;

    @Autowired
    private StarNftSeriesMapper starNftSeriesMapper;
    /**
     * 查询主题
     *
     * @param id 主题主键
     * @return 主题
     */
    @Override
    public StarNftThemeInfo selectStarNftThemeInfoById(Long id)
    {
        return starNftThemeInfoMapper.selectStarNftThemeInfoById(id);
    }

    @Override
    public List<StarNftThemeInfo> selectStarNftThemeInfoByIds(Collection ids) {
        return starNftThemeInfoMapper.selectStarNftThemeInfoByIds(ids);
    }

    /**
     * 查询主题列表
     *
     * @param starNftThemeInfo 主题
     * @return 主题
     */
    @Override
    public List<StarNftThemeInfo> selectStarNftThemeInfoList(StarNftThemeInfo starNftThemeInfo)
    {
        return starNftThemeInfoMapper.selectStarNftThemeInfoList(starNftThemeInfo);
    }

    /**
     * 新增主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    @Override
    public int insertStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo, Long userId)
    {

        Long themeInfoId = SnowflakeWorker.generateId();
        starNftThemeInfo.setId(themeInfoId);
        starNftThemeInfo.setCreateBy(userId.toString());
        starNftThemeInfo.setUpdateBy(userId.toString());
        starNftThemeInfo.setCreateAt(new Date());
        starNftThemeInfo.setUpdateAt(new Date());
        starNftThemeInfo.setIsDelete(0);
        if (null == starNftThemeInfo.getPublisherId()) starNftThemeInfo.setPublisherId(userId);
        return starNftThemeInfoMapper.insertStarNftThemeInfo(starNftThemeInfo);
    }

    /**
     * 修改主题
     *
     * @param starNftThemeInfo 主题
     * @return 结果
     */
    @Override
    public int updateStarNftThemeInfo(StarNftThemeInfo starNftThemeInfo)
    {
        return starNftThemeInfoMapper.updateStarNftThemeInfo(starNftThemeInfo);
    }

    /**
     * 批量删除主题
     *
     * @param ids 需要删除的主题主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeInfoByIds(Long[] ids)
    {
        return starNftThemeInfoMapper.deleteStarNftThemeInfoByIds(ids);
    }

    /**
     * 删除主题信息
     *
     * @param id 主题主键
     * @return 结果
     */
    @Override
    public int deleteStarNftThemeInfoById(Long id)
    {
        return starNftThemeInfoMapper.deleteStarNftThemeInfoById(id);
    }

    @Override
    public List<ThemeInfoVo> selectThemeInfoByPublisherId(Long publisherId) {
        return starNftThemeInfoMapper.selectThemeInfoByPublisherId(publisherId);
    }

    @Override
    public List<StarNftThemeInfoVo> selectStarNftThemeInfoVoList(StarNftThemeInfo starNftThemeInfo) {
        return starNftThemeInfoMapper.selectStarNftThemeInfoVoList(starNftThemeInfo);
    }

    @Override
    @Transactional
    public Boolean publishTheme(StarNftThemeInfo themeInfo,Long adminId) {
        //500个藏品调用一次接口
        StarNftSeries starNftSeries = starNftSeriesMapper.selectStarNftSeriesById(themeInfo.getSeriesId());
        PublishGoodsRes.DataDTO dataDTO = publishGoods(themeInfo,starNftSeries);
        String contractAddress = dataDTO.getContractAddress();
        themeInfo.setContractAddress(contractAddress);
        //设置主题返回数据

        Long themeInfoId = SnowflakeWorker.generateId();
        themeInfo.setId(themeInfoId);
        themeInfo.setCreateBy(adminId.toString());
        themeInfo.setUpdateBy(adminId.toString());
        themeInfo.setCreateAt(new Date());
        themeInfo.setUpdateAt(new Date());
        themeInfo.setIsDelete(0);
        if (null == themeInfo.getPublisherId()) themeInfo.setPublisherId(2L);
        this.starNftThemeInfoMapper.insertStarNftThemeInfo(themeInfo);
//        System.out.println(createAccountRes);
        //遍历发行商品，设置发行藏品编号
        List<PublishGoodsRes.DataDTO.ProductsDTO> products = dataDTO.getProducts();
        long count = products.stream().map(item -> createNumber(item, contractAddress, themeInfo.getId(),adminId)).filter(Boolean.TRUE::equals)
                .count();

        return count == themeInfo.getPublishNumber();
    }

    private Boolean createNumber(PublishGoodsRes.DataDTO.ProductsDTO dto,String contractAddress,Long seriesThemeInfoId,Long adminId){
        StarNftThemeNumber number = new StarNftThemeNumber();
        Long numberId = SnowflakeWorker.generateId();
        number.setId(numberId);
        number.setCreateAt(new Date());
        number.setUpdateAt(new Date());
        number.setStatus(0);
        number.setIsDelete(0);
        number.setCreateBy(adminId.toString());
        number.setUpdateBy(adminId.toString());
        number.setSeriesThemeInfoId(seriesThemeInfoId);
        number.setThemeNumber(Long.valueOf(dto.getTokenId()));
        number.setChainIdentification(dto.getProductId());
        number.setContractAddress(contractAddress);
        number.setPrice(new BigDecimal(dto.getInitPrice()));
        return this.themeNumberMapper.insertStarNftThemeNumber(number) == 1;
    }

    public PublishGoodsRes.DataDTO publishGoods(StarNftThemeInfo themeInfo, StarNftSeries starNftSeries){
//        发行数量  第一次调用接口最大发行500个
        PublishGoodsRes.DataDTO result = new PublishGoodsRes.DataDTO();

        int nums= themeInfo.getPublishNumber().intValue();
        //调用天河链发行商品
        Map<String,Object> map=new HashMap<>();
        List<String> ids=new CopyOnWriteArrayList<>();

        //时间加上发行数量位置
        String pushDay = DateUtil.dateFormat(DateUtil.getDaDate(), "yyyyMMdd");
        //先拼接字符串
        StringBuilder prifixTemplate = new StringBuilder().append(pushDay);
        for (int i = 0; i < String.valueOf(nums).length(); i++) {
            prifixTemplate.append(0);
        }
        long prifix=Long.parseLong(prifixTemplate.toString());

        for (int i = 1; i <= nums; i++) {
            ids.add(String.format("%s",prifix+i));
        }
        String contractAddress = null;

        PublishGoodsRes createAccountRes = null;
        map.put("images",themeInfo.getThemePic());
        PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        publishGoodsReq.setUserId("951029971223");
        String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        publishGoodsReq.setUserKey(userKey);
        publishGoodsReq.setAuthor("链元文创");
        publishGoodsReq.setInitPrice(themeInfo.getLssuePrice().toString());
        publishGoodsReq.setName(String.format("链元文创 %s-%s",starNftSeries.getSeriesName(),themeInfo.getThemeName()));
        publishGoodsReq.setFeature(JSONUtil.toJsonStr(map));
        int publishNum = ids.size() / 500;
        for (int i = 0; i < publishNum + 1; i++) {
            if (ids.size() < 500){
                publishGoodsReq.setProductIds(ids.toArray(new String[ids.size()]));
                publishGoodsReq.setPieceCount(ids.size());
                publishGoodsReq.setContractAddress(contractAddress);
                createAccountRes = tiChainServer.publishGoods(publishGoodsReq);
                result.getProducts().addAll(createAccountRes.getData().getProducts());
            }else {
                List<String> currentIds = ids.subList(0,500);
                publishGoodsReq.setProductIds(currentIds.toArray(new String[500]));
                publishGoodsReq.setPieceCount(500);
                ids = ids.subList((i + 1) * 500,ids.size());
                createAccountRes = tiChainServer.publishGoods(publishGoodsReq);
                contractAddress = createAccountRes.getData().getContractAddress();
                result = createAccountRes.getData();
            }
        }

        if (nums != result.getProducts().size())  throw new StarException("发行异常");
        log.info("发行结果：{}",createAccountRes.toString());
        return result;
    }


}
