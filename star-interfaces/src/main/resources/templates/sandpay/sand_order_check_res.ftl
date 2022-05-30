{
<#if param1?? && param1.head.respCode == '000000'>
    "code":"200",
    "message":"${param1.head.respMsg}",
    "context":
    {

    "orderSn":"${param1.body.oriOrderCode}",
    <#if param1.body.payOrderCode?? >
        "transSn":"${param1.body.payOrderCode}",
    </#if>
    <#if param1.body.orderStatus?? &&param1.body.orderStatus=='00' >
        "status":"200",
        "totalAmount":"${helper.fenToYuan(param1.body.totalAmount)}",
        "message":"交易成功"
    <#else>
        "status":"404",
        "message":"交易失败",
    </#if>
    }
<#else>
    "code":"-1",
    "message":"${param1.head.respMsg}",
    "context":{
    "status":"404",
    "message":"${param1.head.respMsg}",
    }
</#if>

}