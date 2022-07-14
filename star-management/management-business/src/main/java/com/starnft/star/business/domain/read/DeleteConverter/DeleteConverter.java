package com.starnft.star.business.domain.read.DeleteConverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @Date 2022/6/15 10:00 PM
 * @Author ： shellya
 */
@SuppressWarnings("rawtypes")
public class DeleteConverter implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Integer value = 0;
        String str = cellData.getStringValue();
        if("是".equals(str)){
            value = 1;
        }else if ("否".equals(str)){
            value = 0;
        }
        return value;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String str = "否";
        if (0 == value){
            str = "否";
        }else if (1 == value){
            str = "是";
        }
        return new CellData(str);
    }
}
