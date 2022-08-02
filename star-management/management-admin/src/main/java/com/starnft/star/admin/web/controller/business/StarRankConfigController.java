package com.starnft.star.admin.web.controller.business;

import com.google.common.collect.Lists;
import com.starnft.star.business.domain.StarNftThemeNumber;
import com.starnft.star.business.domain.StarRankConfig;
import com.starnft.star.business.domain.dto.AirdropRecordDto;
import com.starnft.star.business.domain.dto.RecordItem;
import com.starnft.star.business.domain.vo.RankData;
import com.starnft.star.business.service.IStarRankConfigService;
import com.starnft.star.business.service.impl.StarNftThemeNumberServiceImpl;
import com.starnft.star.business.support.rank.core.IRankService;
import com.starnft.star.business.support.rank.model.RankingsItem;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import com.starnft.star.common.utils.JsonUtil;
import com.starnft.star.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 排行榜Controller
 *
 * @author shellya
 * @date 2022-06-28
 */
@RestController
@RequestMapping("/business/rankconfig")
public class StarRankConfigController extends BaseController
{
    @Resource
    private IStarRankConfigService starRankConfigService;
    @Resource
    private IRankService rankService;
    @Resource
    private StarNftThemeNumberServiceImpl numberService;
    /**
     * 查询排行榜列表
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarRankConfig starRankConfig)
    {
        startPage();
        List<StarRankConfig> list = starRankConfigService.selectStarRankConfigList(starRankConfig);
        return getDataTable(list);
    }

    /**
     * 导出排行榜列表
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:export')")
    @Log(title = "排行榜", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StarRankConfig starRankConfig)
    {
        List<StarRankConfig> list = starRankConfigService.selectStarRankConfigList(starRankConfig);
        ExcelUtil<StarRankConfig> util = new ExcelUtil<StarRankConfig>(StarRankConfig.class);
        util.exportExcel(response, list, "排行榜数据");
    }

    /**
     * 获取排行榜详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(starRankConfigService.selectStarRankConfigById(id));
    }

    /**
     * 新增排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:add')")
    @Log(title = "排行榜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarRankConfig starRankConfig)
    {
        return toAjax(starRankConfigService.insertStarRankConfig(starRankConfig));
    }

    /**
     * 修改排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:edit')")
    @Log(title = "排行榜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarRankConfig starRankConfig)
    {
        return toAjax(starRankConfigService.updateStarRankConfig(starRankConfig));
    }

    /**
     * 删除排行榜
     */
    @PreAuthorize("@ss.hasPermi('business:rankconfig:remove')")
    @Log(title = "排行榜", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(starRankConfigService.deleteStarRankConfigByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('business:rankconfig:export')")
    @Log(title = "排行榜数据导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exportRank")
    public void exportRank(HttpServletResponse response, Integer page, Integer size)
    {
        int start = (page - 1) *size;
        int end = page * size -1;
        List<RankingsItem> launch_rank = rankService.getRankDatasByPage("launch_rank", start, end);
        for (RankingsItem item :
                launch_rank) {
            int valid = item.getValid().intValue();
            if (valid >= 25){
//                long l = item.getArgentum();
                item.setArgentum(1L);
            }
            long cuprum = valid / 5;
            if (cuprum >= 5L) cuprum = 5L;
            item.setCuprum(cuprum);
        }
//        List<StarRankConfig> list = starRankConfigService.selectStarRankConfigList(starRankConfig);
        ExcelUtil<RankingsItem> util = new ExcelUtil<RankingsItem>(RankingsItem.class);
        util.exportExcel(response, launch_rank, "排行榜数据");
    }

    @PostMapping("/getRankData")
    public List<RankData> getRankData(@RequestBody int[] userIds){
        List<RankData>  data = new ArrayList<>();
        for (int i = 0; i < userIds.length; i++) {
            Long launch_rank = rankService.getRankNum("launch_rank", userIds[i]);
//            Long rankNum = rankService.getRankNum(getNowRank().getRankName(), rankReq.getUserId());
            if (Objects.isNull(launch_rank))  launch_rank =   0L;
            launch_rank++;
            RankData rankData = new RankData();
            rankData.setRank(launch_rank);
            rankData.setUserId(userIds[i]);
            data.add(rankData);
        }
        data = data.stream().sorted(Comparator.comparing(RankData::getRank, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        return data;
    }

    @PostMapping("/getAirpordJson")
    public AjaxResult getAirprodJson( Integer page, Integer size,Long seriesId,Long seriesThemeId){
        //左节点
//        int rightIndex = 0;
//        //右节点
//        int leftIndex = 0;

//        List<AirdropRecordDto> airdropRecordDtos = new ArrayList<>();
//        for (Integer account :
//                airdripUser) {
//
//            AirdropRecordDto dto = new AirdropRecordDto();
//            dto.setAirdropType(0);
//            dto.setUserId(Long.valueOf(account));
//            RecordItem recordItem = new RecordItem();
//            recordItem.setSeriesId(seriesId);
//            recordItem.setSeriesThemeInfoId(seriesThemeId);
//            recordItem.setSeriesThemeId(starNftThemeNumbers.subList(rightIndex,rightIndex + 1 ));
//            rightIndex  += 1;
//            dto.setRecordItems(Lists.newArrayList(recordItem));
//            airdropRecordDtos.add(dto);
//        }
//        String s = JsonUtil.obj2Json(airdropRecordDtos);
//
//        return AjaxResult.success(s);



        //左节点
        int rightIndex = 0;
        //右节点
        int leftIndex = 0;
        List<Long> starNftThemeNumbers = numberService.selectThemeNumberOwberByIsNull(seriesThemeId);
        List<AirdropRecordDto> airdropRecordDtos = new ArrayList<>();
        int start = (page - 1) *size;
        int end = page * size -1;
        List<RankingsItem> launch_rank = rankService.getRankDatasByPage("launch_rank", start, end);
        for (RankingsItem item :
                launch_rank) {
            if (userIds.contains(item.getAccount().intValue())) continue;
            int valid = item.getValid().intValue();
            int argentum = 0;
            if (valid >= 25) argentum += 1;
            if (ten.contains(item.getAccount().intValue())) argentum += 2;
//            int cuprum = valid / 5;
//            if (cuprum >= 5L) cuprum = 5;
//            item.setCuprum(cuprum);
            if (hundres.contains(item.getAccount().intValue())) argentum += 1;
            AirdropRecordDto dto = new AirdropRecordDto();
            dto.setAirdropType(0);
            dto.setUserId(item.getAccount());
            RecordItem recordItem = new RecordItem();
            recordItem.setSeriesId(seriesId);
            recordItem.setSeriesThemeInfoId(seriesThemeId);
            recordItem.setSeriesThemeId(starNftThemeNumbers.subList(rightIndex,rightIndex + argentum ));
            rightIndex  += argentum;
            dto.setRecordItems(Lists.newArrayList(recordItem));
            airdropRecordDtos.add(dto);
        }

        String s = JsonUtil.obj2Json(airdropRecordDtos);

        return AjaxResult.success(s);

    }

    public static final List<Integer> ten = Lists.newArrayList(281850262,
            447303927,
            493433098,
            452774569,
            148169107,
            338496426,
            177704908);


    public static final List<Integer> hundres = Lists.newArrayList(381617169,
            547013759,
            174264051,
            175814560,
            747556896,
            432517536,
            391640513,
            718693278,
            403542645,
            551997115,
            667897821,
            654232977,
            696700174,
            189879367,
            166745224,
            989572091,
            884363953,
            876702588,
            673887355,
            574112667,
            252030894,
            202388374,
            581261597,
            346397942,
            309503360,
            365883122,
            181502750,
            515860835,
            145940619,
            165794866,
            578861304,
            872893274,
            865651772,
            330796421,
            409275553,
            377834770,
            373621681,
            660361310,
            264335057,
            155778869,
            146224314,
            644455309,
            997041809,
            452951043,
            428526577,
            836508943,
            145428489,
            793256162,
            181552651,
            450894037,
            187846516,
            366120090,
            658330706,
            346929293,
            766467607,
            266805775,
            495103605,
            672975576,
            922099233,
            636422054,
            184540276,
            933470544,
            472171155,
            431536597,
            851592036,
            292418826,
            529230007,
            918777703,
            767781767,
            546998827,
            499793312,
            100680580,
            229978589,
            494370089,
            315326229,
            274661434,
            399257314,
            985316524,
            515281046,
            492688609,
            945286912,
            676214902,
            410539691,
            850468258,
            569136892,
            997925786,
            219048153,
            209977811,
            888906822,
            806770984);

    public static final List<Integer> userIds = Lists.newArrayList(184540276,766467607,495103605,431536597,529230007,922099233,918777703,292418826,266805775,851592036,472171155,672975576,346929293,658330706,381617169,174264051,338496426,403542645,377834770,578861304,447303927,391640513,989572091,493433098,148169107,432517536,876702588,793256162,181502750,330796421,452774569,718693278,264335057,252030894,547013759,373621681,165794866,515860835,155778869,175814560,997041809,366120090,933470544,409275553,865651772,667897821,872893274,428526577,644455309,181552651,597870172,634592075,590125918,606698869,545637723,989816429,263993058,650944791,454843988,264979312,902710638,692456505,183548497,463946671,649095306,699608949,120013018,817424238,553840705,108621845,278773741,683815194,509285166,318362587,384604477,850468258,896070028,410539691,494370089,985316524,492688609,945286912,806770984,888906822,997925786,982137965,209977811,596819888,616188149,476391938,317157052,682480857,555724883,400869178,139887177,510360684,589739161,143057963,749465383,289568071);

    public static final List<Integer> airdripUser = Lists.newArrayList(                245635743,
            219048153,
            792419099,
            847153753,
            302483830,
            108122511,
            143339371,
            662608017,
            676214902,
            627234862,
            797725136,
            755485495,
            173543241,
            554657155,
            671749056,
            534529413,
            936805191,
            511395049,
            717924506,
            931943139,
            786172067,
            836508943,
            177704908,
            499793312,
            692568176,
            980681836,
            673322647,
            148563197,
            241136640,
            452676101,
            539465209,
            738163357,
            657995072,
            354646070,
            915512099,
            309503360,
            571629646,
            145940619,
            910303581,
            840412313,
            788013220,
            854428508,
            583729301,
            492130051,
            527908660,
            924013949,
            593523698,
            516003045);


}
