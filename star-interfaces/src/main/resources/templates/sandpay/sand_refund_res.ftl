{
<#if param1?? && param1.head.respCode == '000000'>
    "code": 0,
    "message":"${param1.head.respMsg}",
    "context":
    {

       "orderSn":"${param1.body.orderCode}",
        "message":"交易成功"
    <#else>
        "status": 404,
        "message":"交易失败"
        }
    </#if>
}