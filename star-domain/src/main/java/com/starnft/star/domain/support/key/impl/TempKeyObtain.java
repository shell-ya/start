package com.starnft.star.domain.support.key.impl;

import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.support.key.ApiKeyConfig;
import com.starnft.star.domain.support.key.ITempKeyObtain;
import com.starnft.star.domain.support.key.model.TempKey;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TreeMap;

@Service
@Slf4j
public class TempKeyObtain extends ApiKeyConfig implements ITempKeyObtain {

    private static final String region = "ap-shanghai";
    private static final String defaultBucketPrefix = "avatar";

    @Override
    public TempKey obtainTempKey(final String bucketPrefix, final long uid) {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        config.put("secretId", getSecretId());
        // 替换为您的云 api 密钥 SecretKey
        config.put("secretKey", getSecretKey());
        config.put("durationSeconds", 1800);
        config.put("region", region);
        config.put("bucket", bucketPrefix + "-" + getAppId());

        config.put("allowPrefixes", new String[]{
                String.valueOf(uid) + "/*",
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
            return new TempKey(response.credentials.tmpSecretId, response.credentials.tmpSecretKey, response.credentials.sessionToken);
        } catch (IOException e) {
            log.error("cos 异常", e);
            throw new StarException(StarError.COS_ERROR);
        }

    }


    public TempKey obtainTempKeyDefaultBucket(final long uid) {
        return obtainTempKey(defaultBucketPrefix,uid);
    }
}
