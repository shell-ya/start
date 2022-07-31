package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.StarRankConfig;
import com.starnft.star.business.domain.vo.RankData;
import com.starnft.star.business.service.IStarRankConfigService;
import com.starnft.star.business.support.rank.core.IRankService;
import com.starnft.star.business.support.rank.model.RankItemMetaData;
import com.starnft.star.business.support.rank.model.RankingsItem;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
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
    public void exportRank(HttpServletResponse response, Integer size)
    {
        List<RankingsItem> launch_rank = rankService.getRankDatasByPage("launch_rank", 1, size);
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
