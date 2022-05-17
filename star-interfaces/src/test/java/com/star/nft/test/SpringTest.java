package com.star.nft.test;

import com.starnft.star.domain.support.aware.ConfigurationHolder;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {StarApplication.class})
public class SpringTest {

    @Test
    public void repoTest() {
        System.out.println(ConfigurationHolder.getPayConfig().getChannel());
    }
}
