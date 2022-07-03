{
    <#if param2?? && param2.head.respCode == '000000'>
            "code": 0 ,
            "message":"${param2.head.respMsg}",
        <#else >
            "code": -1 ,
            "message":"${param2.head.respMsg}",
    </#if>
<#--<#if param2?? && param2.head.respCode == '000000'>-->
    "context":
        {
<#if param2?? && param2.head.respCode == '000000'>
            "orderSn":"${param2.body.orderCode}",
            "totalMoney":"${helper.decimalToString(param1.totalMoney)}",
            "thirdPage":"${helper.escapeStr(param2.body.credential)}",

                "status": 0,
                "message":"${param2.head.respMsg}"
            <#else >
                "status": -1,
                "message":"${param2.head.respMsg}"
            </#if>
        }
<#--</#if>-->
}