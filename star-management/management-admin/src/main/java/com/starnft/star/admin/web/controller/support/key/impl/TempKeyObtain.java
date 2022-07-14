package com.starnft.star.admin.web.controller.support.key.impl;

import com.starnft.star.business.support.ApiKeyConfig;
import com.starnft.star.common.exception.ServiceException;
import com.starnft.star.admin.web.controller.support.key.ITempKeyObtain;
import com.starnft.star.admin.web.controller.support.key.model.TempKey;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TreeMap;


/**
 * @Date 2022/6/22 10:26 AM
 * @Author ： shellya
 */
@Slf4j
@Service
public class TempKeyObtain extends ApiKeyConfig implements ITempKeyObtain {

    private static final String region = "ap-shanghai";


    @Override
    public TempKey obtainTempKey(final String bucketPrefix, final long uid) {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        config.put("secretId", "AKIDxCTl5SpCYvHbuEYsAQXYVODygLxW2TsD");
        // 替换为您的云 api 密钥 SecretKey
        config.put("secretKey", "Nfptusz6BEFB5wkS7jHoGdu7FHE8ykR5");
        config.put("durationSeconds", 1800);
        config.put("region", region);
        config.put("bucket", bucketPrefix + "-" + "1302318928");

        config.put("allowPrefixes", new String[]{
                "/*",
        });

        String[] allowActions = new String[]{
                // 简单上传
                "name/cos:PutObject",
                // 表单上传、小程序上传
                "name/cos:PostObject",
                // 分块上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
        };
        config.put("allowActions", allowActions);

        try {
            Response response = CosStsClient.getCredential(config);
            return new TempKey(response.credentials.tmpSecretId, response.credentials.tmpSecretKey, response.credentials.sessionToken,response.startTime,response.expiredTime,bucketPrefix + "-" + "1302318928");
        } catch (IOException e) {
            log.error("cos 异常", e);
            throw new ServiceException("COS 异常");
        }

    }
}

