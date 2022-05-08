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
}
