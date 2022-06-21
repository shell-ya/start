{
<#if param1?? && param1.head.respCode == '000000'>
    "code": 0,
    "message":"${param1.head.respMsg}",
    "context":
    {
        "status": 0,
        "tradeSn":"${param1.body.tradeNo}",
        "orderSn":"${param1.body.orderCode}",
        "refundAmount":"${param1.body.refundAmount}",
        "refundTime":"${param1.body.refundTime}",
         "requestTime":"${param1.body.refundTime}",
        "message":"退款成功"
    }
    <#else>
        "code": 404,
        "message":"${param1.head.respMsg}",
        "context":
        {
        "status": 404,
         requestTime:"${param1.head.respTime}",
         "message":"${param1.head.respMsg}"
        }
    </#if>

}



<#--{"body":{"tradeNo":"1539131722380099584","refundTime":"20220621142300","extend":null,"orderCode":"1539131722380099584","refundAmount":"000000000001"},"head":{"version":"1.0","respTime":"20220621142301","respMsg":"成功","respCode":"000000"}}-->
