package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.domain.UserStrategyExport;
import com.starnft.star.business.service.UserStrategyExportService;
import com.starnft.star.common.core.controller.BaseController;
import com.starnft.star.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business/userstrategy")
public class UserStrategyController extends BaseController {

//    @Autowired
//    private UserStrategyExportService userStrategyExportService;
//
//
//    @PreAuthorize("@ss.hasPermi('business:seckill:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(UserStrategyExport export){
//        startPage();
//        List<UserStrategyExport> list = userStrategyExportService.queryUserStrategyExport(export);
//        return getDataTable(list);
//    }
}

