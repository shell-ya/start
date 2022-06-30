package com.star.nft.test;

import com.starnft.star.domain.rank.core.rank.core.IRankService;
import com.starnft.star.domain.rank.core.rank.model.RankDefinition;
import com.starnft.star.interfaces.StarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {StarApplication.class})
public class RankTest {
    @Resource
    IRankService rankService;
  @Test
    public  void testRank(){
      RankDefinition rankDefinition = new RankDefinition();
      rankDefinition.setRankName("rank");
      rankDefinition.setRankRemark("3232");
      rankService.createRank("test",rankDefinition);
  }
}
