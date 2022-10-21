package com.starnft.star.admin.web.controller.business;

import com.starnft.star.admin.process.NftSnapshotProcess;
import com.starnft.star.business.domain.StarNftSnapshot;
import com.starnft.star.business.service.IStarNftSnapshotService;
import com.starnft.star.common.annotation.Log;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.page.TableDataInfo;
import com.starnft.star.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/business/snapshot")
public class StarNftSnapshotController extends BaseController {
    @Autowired
    private IStarNftSnapshotService starNftSnapshotService;
    @Autowired
    private NftSnapshotProcess snapshotProcess;

    /**
     * 查询快照列表
     */
    @PreAuthorize("@ss.hasPermi('business:snapshot:list')")
    @GetMapping("/list")
    public TableDataInfo list(StarNftSnapshot starNftSnapshot) {
        startPage();
        List<StarNftSnapshot> list = starNftSnapshotService.selectStarNftSnapshotList(starNftSnapshot);
        return getDataTable(list);
    }


    /**
     * 获取快照详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:snapshot:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {

        return AjaxResult.success(starNftSnapshotService.selectStarNftSnapshotById(id));
    }

    /**
     * 新增快照
     */
    @PreAuthorize("@ss.hasPermi('business:snapshot:add')")
    @Log(title = "快照", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StarNftSnapshot starNftSnapshot) {
        starNftSnapshot.setCreateBy(getUsername());
        return toAjax(snapshotProcess.saveSnapshot2job(starNftSnapshot));
    }

    /**
     * 修改快照
     */
    @PreAuthorize("@ss.hasPermi('business:snapshot:edit')")
    @Log(title = "快照", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StarNftSnapshot starNftSnapshot) {
        return toAjax(snapshotProcess.updateSnapshot2Job(starNftSnapshot));
    }

    /**
     * 删除快照
     */
    @PreAuthorize("@ss.hasPermi('business:snapshot:remove')")
    @Log(title = "快照", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(starNftSnapshotService.deleteStarNftSnapshotByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('business:snapshot:export')")
    @RequestMapping(value = "/downExcel", method = RequestMethod.POST)
    public void exportTheme(HttpServletResponse response, StarNftSnapshot snapshot) throws IOException {
        snapshotProcess.downloadExcel(response,snapshot);
    }
}

