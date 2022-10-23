package com.starnft.star.quartz.task;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.starnft.star.business.domain.StarNftSnapshot;
import com.starnft.star.business.domain.dto.SnapshotMaterialDto;
import com.starnft.star.business.domain.vo.UserInfo;
import com.starnft.star.business.service.IStarNftSnapshotService;
import com.starnft.star.business.service.IStarNftUserThemeService;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("snapshotTask")
//快照定时任务类
public class SnapshotTask {

    @Resource
    IStarNftUserThemeService userThemeService;
    @Resource
    IStarNftSnapshotService snapshotService;

    private List<String> rowName = Lists.newArrayList("用户ID", "用户昵称", "用户姓名", "手机号");

    public void snKuai(String taskId) {
        StarNftSnapshot snapshot = snapshotService.selectStarNftSnapshotById(Long.parseLong(taskId));
        if (Objects.isNull(snapshot) || StringUtils.isEmpty(snapshot.getSnapshotMaterial())){
            log.error("快照任务为空或配置主题为空");
            throw new StarException(StarError.SYSTEM_ERROR,"快照任务为空或配置主题为空");
        }
        try{
            //查询配置主题下用户持有数据
            List<Map<String, Object>> excelValue = getExcelValue(snapshot.getSnapshotMaterial());
            //导出excel
            String fileName = exportExcel(snapshot.getTaskName(),excelValue);
            //更新为成功
            snapshot.setFilePath(fileName);
            snapshot.setSuccessful(StarConstants.SnapshotState.COMPLETE.getCode());
            snapshotService.updateStarNftSnapshot(snapshot);
        }catch (Exception e){
            log.error("快照任务{}失败",snapshot.getTaskName(),e);
            //更新为失败
            snapshot.setSuccessful(StarConstants.SnapshotState.FAIL.getCode());
            snapshotService.updateStarNftSnapshot(snapshot);
        }

    }

    private String exportExcel(String taskName, List<Map<String, Object>> excelValue) throws IOException {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        try {
            HSSFSheet sheet = hssfWorkbook.createSheet(taskName);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            //设置头
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(taskName);
            HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
            cellStyle.setFillBackgroundColor(IndexedColors.YELLOW1.getIndex());
//        cellStyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());// 设置背景色
            cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
            HSSFFont font = hssfWorkbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 25);//设置字体大小
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            HSSFCellStyle row1CellStyle = hssfWorkbook.createCellStyle();
            row1CellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowName.size() - 1));
            HSSFRow row1 = sheet.createRow(1);
            for (int i = 0; i < rowName.size(); i++) {
                HSSFCell hssfCell = row1.createCell(i);
                hssfCell.setCellStyle(row1CellStyle);
                hssfCell.setCellValue(rowName.get(i));
            }

            for (int i = 0; i < excelValue.size(); i++) {
                HSSFRow row2 = sheet.createRow(i + 2);
                for (int j = 0; j < rowName.size(); j++) {
                    String cellVal = String.valueOf(excelValue.get(i).get(rowName.get(j)));
                    row2.createCell(j).setCellValue(StringUtils.isBlank(cellVal) || cellVal.equals("null") ? "" : cellVal);
                }
            }


            ExcelUtil<String> util = new ExcelUtil<String>(String.class);
            return util.exportExcel(taskName, hssfWorkbook);
        }catch (Exception e){
            log.error("文件生成失败",e);
            throw e;
        }finally {
            hssfWorkbook.close();
        }


    }

    private List<Map<String, Object>> getExcelValue(String snapshotMaterial){
        List<SnapshotMaterialDto> materialDtoList = JSONUtil.toList(snapshotMaterial, SnapshotMaterialDto.class);
        Map<String, Map<String, Object>> snapshotVal = new HashMap<>();
        for (SnapshotMaterialDto dto :
                materialDtoList) {
            rowName.add(dto.getThemeName());
            List<UserInfo> userInfos = userThemeService.selectHasThemeUser(dto.getThemeId());
            for (UserInfo u :
                    userInfos) {
                if (snapshotVal.containsKey(u.getUserId())) {
                    snapshotVal.get(u.getUserId()).put(dto.getThemeName(), u.getNums());
                } else {
                    Map<String, Object> val = new HashMap<>();
                    val.put("用户ID", u.getUserId());
                    val.put("用户昵称", u.getNickName());
                    val.put("用户姓名", u.getUserName());
                    val.put("手机号", u.getPhone());
                    val.put(dto.getThemeName(), u.getNums());
                    snapshotVal.put(u.getUserId(), val);
                }
            }
        }

        return snapshotVal.values().stream().collect(Collectors.toList());
    }

}
