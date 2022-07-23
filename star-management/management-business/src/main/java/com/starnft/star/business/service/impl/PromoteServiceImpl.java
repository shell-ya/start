package com.starnft.star.business.service.impl;

import com.google.common.collect.Lists;
import com.starnft.star.business.domain.PromoteResult;
import com.starnft.star.business.domain.StarNftPromoteRecord;
import com.starnft.star.business.service.IPromoteService;
import com.starnft.star.business.service.IStarNftPromoteRecordService;
import com.starnft.star.business.strategy.promote.PromoteRunnable;

import com.starnft.star.common.core.domain.sms.MobileModel;

import com.starnft.star.common.utils.poi.ExcelUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PromoteServiceImpl implements IPromoteService, ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Resource
    PromoteRunnable promoteRunnable;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    @SneakyThrows
    public PromoteResult promoteExcel(InputStream inputStream, String context, Integer types) {
        ExcelUtil<MobileModel> excelResult = new ExcelUtil<MobileModel>(MobileModel.class);
        List<String> mobiles = excelResult.importEasyExcel(inputStream).stream().map(item -> item.getMobile()).collect(Collectors.toList());
        List<List<String>> partitions = Lists.partition(mobiles, 500);
        List<Future<Boolean>> futures = new ArrayList<>();
        for (List<String> partition : partitions) {
            Future<Boolean> submit = threadPoolTaskExecutor.submit(() -> promoteRunnable.running(partition, types, context));
            futures.add(submit);
        }
        Integer successCount = 0;
        Integer errorCount = 0;

        for (int i = 0; i < futures.size(); i++) {
            try {
                Future<Boolean> booleanFuture = futures.get(i);
                Boolean isSuccess = booleanFuture.get();
                if (isSuccess) successCount+=partitions.get(i).size();
            } catch (Exception e) {
                errorCount+=partitions.get(i).size();
            }

        }
        PromoteResult promoteResult = new PromoteResult();
        promoteResult.setErrorCount(errorCount);
        promoteResult.setSuccessCount(successCount);
        return promoteResult;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
