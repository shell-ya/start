{
    "head": {
        "method": "sandpay.trade.pay",
        "productId": "00000030",
        "mid": "${conf.mid}",
        "channelType": "08",
        "reqTime": "${helper.getCurrentTime()}",
        "version": "1.0",
        "accessType": "1"
    },
    "body": {
        "subject": "${payment.getOrderTypeDesc()}",
        "payMode": "sand_upsdk",
        "limitPay": "1",
        "frontUrl": "${payment.frontUrl}",
        "body": "${payment.getOrderTypeDesc()}",
        "totalAmount": "${helper.yuanToFen(payment.totalMoney)}",
        "txnTimeOut": "${helper.outLineTime()}",
        "clientIp": "${payment.clientIp}",
        "notifyUrl": "${conf.notify}",
        "orderCode": "${payment.orderSn}",
        "extend": "${payment.composeCallback()}"
    }
}