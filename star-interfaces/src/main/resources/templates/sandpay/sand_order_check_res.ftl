{
<#--<#if param1?? && param1.head.respCode == '000000'>-->
<#--    "code":"200",-->
<#--    "message":"${param1.head.respMsg}"-->
<#--<#else>-->
<#--    "code":"-1",-->
<#--    "message":"${param1.head.respMsg}"-->
<#--</#if>-->

<#if param1?? && param1.head.respCode == '000000'>
    "context":
    {
    "orderSn":"${param1.body.orderCode}",
    "transSn":"${param1.body.payOrderCode}",
<#--    "totalMoney":"${helper.decimalToString(param1.body.totalMoney)}",-->
    "code":"200",
    "message":"${param1.head.respMsg}"
    }
</#if>

}