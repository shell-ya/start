{"body":{"oriOrderCode":"${param1.orderSn}",
"refundReason":"${param1.reason}",
"extend":"${helper.extendReplace(param1.composeCallback())}",
"notifyUrl":"http://jacqueshuang.natapp1.cc/kfc/refund/notify",
"orderCode":"${param1.refundOrderSn}",
"refundAmount":"${helper.yuanToFen(param1.totalMoney)}"},
"head":{"method":"sandpay.trade.refund",
"productId":"00002000",
"mid":"${param2.mid}",
"channelType":"08",
"reqTime":"${helper.getCurrentTime()}",
"version":"1.0",
"accessType":"1"
}
}