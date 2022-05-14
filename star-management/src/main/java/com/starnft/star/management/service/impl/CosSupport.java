package com.starnft.star.management.service.impl;

import com.google.common.collect.Lists;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.retry.RetryPolicy;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.domain.support.key.ApiKeyConfig;
import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import com.starnft.star.management.service.ICosSupport;
import com.starnft.star.management.service.IDictManger;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CosSupport extends ApiKeyConfig implements ICosSupport {

    @Value("${cos.config.region-name: tap-shanghai}")
    private String regionName;

    @Value("${cos.config.fileByte: 20M}")
    private String fileByteLimit;

    @Value("${cos.config.fileMax: 5GB}")
    private String fileByteMax;

    @Value("${cos.config.downloadPath: /tmp/downloads}")
    private String downloadPath;

    private static final String cosDomain = "myqcloud.com/";
    private static final String bucketNameMapping = "Bucket_Name_Mapping";

    @Resource
    private IDictManger dictManger;


    /**
     * @return cos 客户端
     * @author Ryan Z / haoran
     * @description cos 客户端初始化
     * @date 2022/5/14
     */
    @Override
    public COSClient cosClientInit() {
        COSCredentials cred = new BasicCOSCredentials(getSecretId(), getSecretKey());
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 设置最大重试次数为 4 次
        clientConfig.setMaxErrorRetry(4);

        // 自定义重试策略
        RetryPolicy myRetryPolicy = new OnlyIOExceptionRetryPolicy();
        clientConfig.setRetryPolicy(myRetryPolicy);

        return new COSClient(cred, clientConfig);
    }


    /**
     * @param bucketPrefix 桶名称前缀
     * @param type         最好使用桶下目录
     * @return 桶
     * @author Ryan Z / haoran
     * @description 创建存储桶
     * @date 2022/5/14
     */
    @Override
    public Bucket createBucket(String bucketPrefix, String type, COSClient client) {

        String bucketName = new StringBuffer(bucketPrefix).append("-").append(getAppId()).toString();
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);

        Bucket bucketResult = null;
        COSClient cosClient = cosClientInit();
        try {
            bucketResult = Optional.of(client).orElse(cosClient).createBucket(createBucketRequest);

            if (!saveBucketMapping(bucketPrefix, type)) {
                throw new StarException(StarError.DB_RECORD_UNEXPECTED_ERROR, "桶映射字典插入失败！");
            }
        } catch (CosClientException | StarException serverException) {
            Optional.of(client).orElse(cosClient).shutdown();
            log.error("[{}] 创建存储桶失败: ", this.getClass().getSimpleName(), serverException);
            throw new StarException(StarError.SYSTEM_ERROR, "创建存储桶失败");
        }

        return bucketResult;
    }

    private boolean saveBucketMapping(String bucketPrefix, String type) {
        StarNftDict dict = new StarNftDict();
        dict.setDictCode(bucketPrefix);
        dict.setDictDesc(type);
        dict.setCategoryCode(bucketNameMapping);
        dict.setDataType(String.class.getSimpleName());
        dict.setDoSecret(0);
        dict.setEnabled(1);
        dict.setVersion(0);
        return 1 == dictManger.createDict(Lists.newArrayList(dict));
    }


    /**
     * @param client cos客户端
     * @return 桶列表
     * @author Ryan Z / haoran
     * @description 获取cos桶列表
     * @date 2022/5/14
     */
    @Override
    public List<Bucket> getBucketList(COSClient client) {
        return client.listBuckets();
    }


    public boolean isExist(String Key, COSClient client) {
        //todo
        return true;
    }

    private String getCompleteKey(String bucketName, String regionName, String key) {
        //对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名
        // examplebucket-1250000000.cos.ap-guangzhou.myqcloud.com/images/picture.jpg
        // 中，对象键为 images/picture.jpg
        return new StringBuilder(bucketName).append(".")
                .append("cos").append(".")
                .append((Strings.isNullOrEmpty(regionName) ? this.regionName : regionName)).append(".")
                .append(cosDomain).append(key).toString();
    }

    @Override
    public void shutdown(COSClient client) {
        // 关闭客户端(关闭后台线程)
        client.shutdown();
    }


    /**
     * @param in
     * @param bucketName
     * @param key
     * @param client
     * @return PutObjectResult
     * @author Ryan Z / haoran
     * @description cos文件上传
     * @date 2022/5/14
     */
    @Override
    public PutObjectResult fileUploadViaCos(InputStream in, String bucketName, String key, COSClient client) {
        //todo 验证文件大小
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, in, client.getObjectMetadata(bucketName, key));
        return client.putObject(putObjectRequest);
    }

    /**
     * @param bucketName
     * @param client
     * @param key
     * @return ObjectMetadata
     * @author Ryan Z / haoran
     * @description cos文件下载
     * @date 2022/5/14
     */
    @Override
    public ObjectMetadata fileDownloadViaCos(String bucketName, String key, COSClient client) throws IOException {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        // 指定文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示下载的文件 picture.jpg 在 folder 路径下
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = client.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        cosObjectInput.close();

        // 方法2 下载文件到本地的路径，例如 D 盘的某个目录
        File downFile = new File(downloadPath);
        getObjectRequest = new GetObjectRequest(bucketName, key);
        return client.getObject(getObjectRequest, downFile);
    }


    @Override
    public void fileDelete(String bucketName, String key, COSClient client) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        client.deleteObject(bucketName, key);
    }

    static class OnlyIOExceptionRetryPolicy extends RetryPolicy {

        @Override
        public <X extends CosServiceRequest> boolean shouldRetry(CosHttpRequest<X> request,
                                                                 HttpResponse response,
                                                                 Exception exception,
                                                                 int retryIndex) {
            // 如果是客户端的 IOException 异常则重试，否则不重试
            if (exception.getCause() instanceof IOException) {
                return true;
            }
            return false;
        }
    }


}
