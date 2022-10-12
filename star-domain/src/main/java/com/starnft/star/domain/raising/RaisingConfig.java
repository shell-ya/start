package com.starnft.star.domain.raising;

import com.starnft.star.domain.support.key.repo.IDictionaryRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RaisingConfig {
    private static final String categoryCode = "RAISING";

    private final static Map<String, RaisingInfo> keys = new HashMap<>();

    private static BigDecimal proportion = null;

    private static BigDecimal defaultPrice = null;
    @Resource
    private IDictionaryRepository dictionaryRepository;

    @PostConstruct
    public void initRaisingInfo() {
        RaisingInfo apiKeyInfo = dictionaryRepository.getDictCodes(categoryCode, RaisingInfo.class);
        keys.put(categoryCode, apiKeyInfo);
    }

    public static BigDecimal getRaisingProportion() {
        if (null == proportion){
            proportion = new BigDecimal(keys.get(categoryCode).getRaisingProportion());
            return proportion;
        }
        return proportion;
    }


    public static String getRaisingMsg() {
        return keys.get(categoryCode).getRaisingMsg();
    }

    public static BigDecimal getDefaultPrice(){
        if (null == defaultPrice){
            defaultPrice = new BigDecimal(keys.get(categoryCode).getDefaultPrice());
            return defaultPrice;
        }
        return defaultPrice;
    }
}
