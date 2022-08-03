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


}
