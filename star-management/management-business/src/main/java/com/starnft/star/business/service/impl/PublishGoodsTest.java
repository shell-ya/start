package com.starnft.star.business.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.common.chain.model.req.PublishGoodsReq;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.IntStream;

public class PublishGoodsTest {

    /**
     * 测试调用天河链接口数字商品发行
     *
     * @param args
     */
    public static void main(String[] args) {
        int publishNumber = 5;
        int pageSize = 2;
        String baseString = "0";
        String today = cn.hutool.core.date.DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        String prefix = today + RandomUtil.randomString(baseString, String.valueOf(publishNumber).length());
        List<String> container = Lists.newArrayListWithCapacity(publishNumber);
        IntStream.range(0, publishNumber).forEach(item -> container.add(String.valueOf(Long.parseLong(prefix) + item)));
        List<List<String>> subs = ListUtils.partition(container, pageSize);
        subs.parallelStream().forEach(item -> {
            PublishRequest request = new PublishRequest();
            request.setName("test");
            request.setProductIds(item);
            request.setInitPrice("1");
            request.setPieceCount(item.size());
            String result = HttpUtil.post("https://api.tichain.tianhecloud.com/api/v2/nfr/publish", JSONUtil.toJsonStr(request));
            System.out.println(result);
        });
    }

}

@NoArgsConstructor
@Data
class PublishRequest {

    /**
     * appId
     */
    private String appId = "tichain449113";

    /**
     * appKey
     */
    private String appKey = "a070713d25c291127992bf096cbcb3462ca1e33c";

    /**
     * userId
     */
    private String userId = "951029971223";

    /**
     * userKey
     */
    private String userKey = SecureUtil.sha1("951029971223".concat("lywc"));

    /**
     * productIds
     */
    private List<String> productIds;

    /**
     * name
     */
    private String name;

    /**
     * pieceCount
     */
    private Integer pieceCount;

    /**
     * author
     */
    private String author = "链元文创";

    /**
     * feature
     */
    private String feature;

    /**
     * 发行价格，字符长度不超过 20
     */
    private String initPrice;
}
