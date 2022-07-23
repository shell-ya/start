package com.starnft.star.admin.web.controller.business;

import com.starnft.star.business.service.IPromoteService;
import com.starnft.star.common.core.domain.AjaxResult;
import com.starnft.star.common.core.domain.entity.SysDictData;
import com.starnft.star.common.core.domain.sms.MobileModel;
import com.starnft.star.common.utils.poi.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@RestController
//@RequestMapping("/business/promote")
//@ApiOperation("")
//public class PromoteController {
//
//
//}
//