{
<#if param1?? && param1.head.respCode == '000000'>
    "code": 0,
    "message":"${param1.head.respMsg}",
    "context":
    {

    "orderSn":"${param1.body.oriOrderCode}",
    <#if param1.body.payOrderCode?? >
        "transSn":"${param1.body.payOrderCode}",
    </#if>
    <#if param1.body.orderStatus?? &&param1.body.orderStatus=='00' >
        "status": 0,
        "totalAmount":"${helper.fenToYuan(param1.body.totalAmount)}",
        <#if param1.body.extend?? >
            "uid":"${helper.parseStr(helper.parseObj(param1.body.extend),'userId')}",
            "payChannel":"${helper.parseStr(helper.parseObj(param1.body.extend),'payChannel')}",
            "topic":"${helper.parseStr(helper.parseObj(param1.body.extend),'multicastTopic')}",
        </#if>
        "message":"交易成功"
    <#else>
        "status": 404,
        "message":"交易失败",
    </#if>
    }
<#else>
    "code": -1,
    "message":"${param1.head.respMsg}",
    "context":{
    "status": 404,
    "message":"${param1.head.respMsg}",
    }
</#if>

}