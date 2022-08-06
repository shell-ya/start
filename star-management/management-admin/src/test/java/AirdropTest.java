import com.starnft.star.admin.RuoYiApplication;
import com.starnft.star.business.domain.AirdropThemeRecord;
import com.starnft.star.business.service.IAirdropThemeRecordService;
import com.starnft.star.common.constant.RedisKey;
import com.starnft.star.common.utils.redis.RedisUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/7/21 8:34 PM
 * @Author ： shellya
 */
@SpringBootTest(classes = RuoYiApplication.class )
public class AirdropTest {

    private final IAirdropThemeRecordService airdropThemeRecordService;
    //注册用户链上地址 密钥使用加密后密码

    //铸造创世金章 创世银章 创世铜章 首发白羊座 创世摩羯座
    private final RedisUtil redisUtil;
    @Autowired
    public AirdropTest(IAirdropThemeRecordService airdropThemeRecordService, RedisUtil redisUtil) {
        this.airdropThemeRecordService = airdropThemeRecordService;
        this.redisUtil = redisUtil;
    }

    @Test
    public void addYou(){
        List<String> list = Lists.newArrayList(" 219048153",
                "177704908  ",
                "108122511  ",
//                "357623589  ",
                "452676101  ",
                "927060606  ",
                "511395049  ",
                "692568176  ",
                "673887355  ",
                "661271244  ",
                "661271244  ",
                "173481049  ",
                "633197642",
                "786172067  ",
                "243914627  ",
                "173543241  ",
                "931943139  ",
                "652862439  ",
                "468098931  ",
                "551997115  ");

        for (String item :
                list) {
            redisUtil.hincr(RedisKey.SECKILL_GOODS_PRIORITY_TIMES.getKey(),item.trim(), 1L);
        }


    }

    @Test
    public void test(){
        AirdropThemeRecord record = new AirdropThemeRecord();
        record.setAirdropType(0);
        record.setUserId(810769555L);
        record.setSeriesId(3L);
        record.setSeriesThemeInfoId(999728276368560128L);
        record.setSeriesThemeId(999729133542039552L);
        boolean b = airdropThemeRecordService.addUserAirdrop(record);
        assert b;
    }

    @Test
    public void tsetList(){
        AirdropThemeRecord record = new AirdropThemeRecord();
        record.setAirdropType(0);
        record.setUserId(810769555L);
        record.setSeriesId(3L);
        record.setSeriesThemeInfoId(998977713737334784L);
        record.setSeriesThemeId(999279204334088192L);
        AirdropThemeRecord themeRecord = new AirdropThemeRecord();
        themeRecord.setAirdropType(0);
        themeRecord.setUserId(810769555L);
        themeRecord.setSeriesId(3L);
        themeRecord.setSeriesThemeInfoId(998977713737334784L);
        themeRecord.setSeriesThemeId(999279223153012736L);

        AirdropThemeRecord airdropThemeRecord = new AirdropThemeRecord();
        airdropThemeRecord.setAirdropType(0);
        airdropThemeRecord.setUserId(810769555L);
        airdropThemeRecord.setSeriesId(3L);
        airdropThemeRecord.setSeriesThemeInfoId(999728276368560128L);
        airdropThemeRecord.setSeriesThemeId(999728906840514560L);

        ArrayList<AirdropThemeRecord> airdropThemeRecords = Lists.newArrayList(record, themeRecord, airdropThemeRecord);

//        boolean b = airdropThemeRecordService.addUserAirdropList(airdropThemeRecords);
//        assert b;


    }
}
