"head":{
"version":"1.0"
"method":"sandpay.trade.refund"
"productId":"00002000"
"accessType":"1"
"mid":"${param2.mid}"
"channelType":"07"
"reqTime":"${helper.getCurrentTime()}"
"body":{
"orderCode":"${param1.refundOrderSn}"
"oriOrderCode":"${param1.orderSn}"
"refundAmount":"${helper.yuanToFen(param1.totalMoney)}"
"notifyUrl":"${param2.notify}"
"refundReason":"${param1.reason}"
"extend":"${param1.composeCallback}"
}