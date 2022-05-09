package com.starnft.star.domain.wallet.service;

import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.model.vo.WalletConfigVO;
import com.starnft.star.domain.wallet.repository.IWalletRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class WalletConfig {

    @Resource
    private IWalletRepository walletRepository;

    public static Map<StarConstants.PayChannel, WalletConfigVO> configs = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        List<WalletConfigVO> walletConfigs = walletRepository.loadAllWalletConfig();
        configs = walletConfigs.stream().collect(Collectors.toMap(WalletConfigVO::getChannel, walletConfigVO -> walletConfigVO));
    }

    /**
     * 获取配置
     * @param channel 渠道
     * @return 配置
     */
    public static WalletConfigVO getConfig(StarConstants.PayChannel channel) {
        return configs.get(channel);
    }

    public static boolean isSupport(StarConstants.PayChannel channel) {
        return configs.containsKey(channel);
    }

    public static boolean isSupport(String channel) {
        return configs.containsKey(StarConstants.PayChannel.valueOf(channel));
    }

    /**
     * 手动刷新配置
     */
    public void manualRefresh() {
        List<WalletConfigVO> walletConfigs = walletRepository.loadAllWalletConfig();
        configs = walletConfigs.stream().collect(Collectors.toMap(WalletConfigVO::getChannel, walletConfigVO -> walletConfigVO));
    }

    /**
     * 定时刷新配置 10min
     */
    public void scheduleRefresh() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(this::manualRefresh, 10, TimeUnit.MINUTES);
    }
}
