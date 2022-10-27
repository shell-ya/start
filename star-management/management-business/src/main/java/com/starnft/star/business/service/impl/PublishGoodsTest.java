package com.starnft.star.business.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.common.chain.model.req.PublishGoodsReq;
import com.starnft.star.common.chain.model.res.PublishGoodsRes;
import org.apache.commons.collections4.ListUtils;

import java.util.Date;
import java.util.List;

public class PublishGoodsTest {


    /**
     * 测试调用天河链接口数字商品发行
     *
     * @param args
     */
    public static void main(String[] args) {



        int publishNumber = 2000;
        int pageSize = 600;

        // 请求次数
        // int requestTimes = (publishNumber % pageSize) == 0 ? (publishNumber / pageSize) : (publishNumber / pageSize + 1);
        String today = cn.hutool.core.date.DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String prefix = today + RandomUtil.randomString("0", String.valueOf(publishNumber).length());
        Long baseId = Long.valueOf(prefix);
        List<Long> container = Lists.newArrayListWithCapacity(publishNumber);
        for (int i = 0; i < publishNumber; i++) {
            long valueId = baseId + i;
            container.add(valueId);
        }

        List<List<Long>> subs = ListUtils.partition(container, pageSize);
        System.out.println(subs);

        // PublishGoodsRes.DataDTO result = new PublishGoodsRes.DataDTO();
        // result.getProducts().add(new PublishGoodsRes.DataDTO.ProductsDTO());
        // System.out.println(result);

        //
        // PublishGoodsReq publishGoodsReq = new PublishGoodsReq();
        // publishGoodsReq.setUserId("951029971223");
        // String userKey = SecureUtil.sha1("951029971223".concat("lywc"));
        // publishGoodsReq.setUserKey(userKey);
        // publishGoodsReq.setAuthor("链元文创");
        // publishGoodsReq.setInitPrice("1");
        // publishGoodsReq.setName(String.format("链元文创 %s-%s", "test", "主题"));
        //
        // publishGoodsReq.setProductIds(productIds);
        // publishGoodsReq.setPieceCount(publishNumber);
        //
        // JSONObject json = JSONUtil.parseObj(publishGoodsReq);
        // json.set("appId", "tichain449113");
        // json.set("appKey", "a070713d25c291127992bf096cbcb3462ca1e33c");
        //
        // String result = HttpUtil.post("https://api.tichain.tianhecloud.com/api/v2/nfr/publish", JSONUtil.toJsonStr(json));
        // System.out.println(result);
    }
}
