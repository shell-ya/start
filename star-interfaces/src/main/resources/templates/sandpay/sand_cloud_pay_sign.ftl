accsplit_flag=NO
&create_ip=127_0_0_1
&create_time=${createTime}
&extend=${param1.composeCallback()}
&mer_key=${param2.merKey!''}
&mer_no=${param2.mid!''}
&mer_order_no=${param1.orderSn!''}
&notify_url=${param2.notify!''}
&order_amt=${param1.totalMoney!''}
&pay_extra={"nickName":"${param1.payExtend.nickName!''}","userId":"${param1.payExtend.userId!''}"}
&return_url=${param1.frontUrl!''}
&sign_type=MD5&store_id=000000
&version=10
&key=${param2.md5Key!''}