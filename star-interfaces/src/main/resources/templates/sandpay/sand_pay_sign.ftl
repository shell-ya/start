{
    "head": {
        "method": "sandpay.trade.pay",
        "productId": "00000030",
        "mid": "${param2.mid}",
        "channelType": "08",
        "reqTime": "${helper.getCurrentTime()}",
        "version": "1.0",
        "accessType": "1"
    },
    "body": {
        "subject": "${param1.getOrderTypeDesc()}",
        "payMode": "sand_upsdk",
        "limitPay": "1",
        "frontUrl": "${param1.frontUrl}",
        "body": "${param1.getOrderTypeDesc()}",
        "totalAmount": "${helper.yuanToFen(param1.totalMoney)}",
        "txnTimeOut": "${helper.outLineTime()}",
        "clientIp": "${param1.clientIp}",
        "notifyUrl": "${param2.notify}",
        "orderCode": "${param1.orderSn}",
"extend": "${helper.extendReplace(param1.composeCallback())}"
    }
}