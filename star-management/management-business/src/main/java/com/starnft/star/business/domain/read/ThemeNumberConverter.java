package com.starnft.star.business.domain.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @Date 2022/6/15 10:10 PM
 * @Author ： shellya
 */
@SuppressWarnings("rawtypes")
public class ThemeNumberConverter implements Converter<Integer> {
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
        switch (str){
            case "发行未出售" : value = 0;
                break;
            case "发行已出售" : value = 1;
                break;
            case "未挂售" : value = 2;
                break;
            case "挂售中" : value = 3;
                break;
        }
        return value;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String str = "发行未出售";
        switch (value){
            case 0 : str = "发行未出售";
                break;
            case 1 : str =  "发行已出售";
                break;
            case 2 : str = "未挂售";
                break;
            case 3 : str = "挂售中";
                break;
        }
        return new CellData(str);
    }

}
