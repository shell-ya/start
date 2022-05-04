package com.starnft.star.infrastructure.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;

/**
 * @author GW00206137
 */
@Slf4j
public class ExportUtil {

    public static void export(HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Collection data, String fileName) {
        try {
            log.info("fileName--->" + fileName);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, fileName), clazz, data);
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            fileName += "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException | ExcelExportException e) {
            log.error("failed to export excel", e);
//            throw new NEException(NEError.WRITE_EXCEL_ERROR);
        } finally {
            try {
                IOUtils.closeQuietly(response.getOutputStream());
            } catch (IOException e) {
                log.error("{}", e);
            }
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     *
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }
}