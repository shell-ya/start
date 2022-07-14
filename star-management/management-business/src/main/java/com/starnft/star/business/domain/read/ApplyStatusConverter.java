package com.starnft.star.business.domain.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @Date 2022/6/16 9:29 AM
 * @Author ： shellya
 */
public class ApplyStatusConverter implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey()  {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Integer value = 0;
        String str = cellData.getStringValue();
        if("申请中".equals(str)){
            value = 0;
        }else if ("提现成功".equals(str)){
            value = 1;
        }else if ("提现驳回".equals(str)){
            value = 2;
        }else if ("提现取消".equals(str)){
            value = -1;
        }
        return value;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String str = "申请中";
        if (0 == value){
            str = "申请中";
        }else if (1 == value){
            str = "提现成功";
        }else if (2 == value){
            str = "提现驳回";
        }else if (-1 == value){
            str = "提现取消";
        }
        return new CellData(str);
    }
}
