{
    <#if param2?? && param2.head.respCode == '000000'>
            "code":"200",
            "message":"${param2.head.respMsg}",
        <#else >
            "code":"-1",
            "message":"${param2.head.respMsg}",
    </#if>
    "context":
        {
            "orderSn":"${param2.body.orderCode}",
            "totalMoney":"${helper.decimalToString(param1.totalMoney)}",
            "thirdPage":"${helper.escapeStr(param2.body.credential)}",
            <#if param2?? && param2.head.respCode == '000000'>
                "code":"200",
                "message":"${param2.head.respMsg}"
            <#else >
                "code":"-1",
                "message":"${param2.head.respMsg}"
            </#if>
        }
}