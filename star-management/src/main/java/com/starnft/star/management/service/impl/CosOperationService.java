package com.starnft.star.management.service.impl;

import com.qcloud.cos.COSClient;
import com.starnft.star.management.service.ICosOperationService;
import com.starnft.star.management.service.ICosSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class CosOperationService implements ICosOperationService {

    @Resource
    private ICosSupport cosSupport;

    /**
     * @param operation 操作
     * @param resClazz  返回值类型
     * @return 返回值
     * @author Ryan Z / haoran
     * @description 需要返回值
     * @date 2022/5/14
     */
    @Override
    public <T> T doCos(Function<COSClient, T> operation, Class<T> resClazz) {
        COSClient cosClient = cosSupport.cosClientInit();
        T apply;
        try {
            apply = operation.apply(cosClient);
        } finally {
            cosSupport.shutdown(cosClient);
        }
        return apply;
    }

    /**
     * @param operation 操作
     * @author Ryan Z / haoran
     * @description 无需返回值
     * @date 2022/5/14
     */
    @Override
    public void doCos(Consumer<COSClient> operation) {
        COSClient cosClient = cosSupport.cosClientInit();
        try {
            operation.accept(cosClient);
        } finally {
            cosSupport.shutdown(cosClient);
        }
    }

    /**
     * 使用案例：
     public void main() {

     doCos(client -> {
        fileDelete("a", "b",  COSClient client)
     });

     Bucket bucket = doCos(client -> {
        return createBucket("a", "b", client);
     }, Bucket.class);
     }
     */

}
