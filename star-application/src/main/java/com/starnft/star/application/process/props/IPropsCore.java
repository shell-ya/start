package com.starnft.star.application.process.props;

import com.starnft.star.application.process.props.model.req.PropBuyReq;
import com.starnft.star.application.process.props.model.req.PropInvokeReq;

public interface IPropsCore {

    Boolean propInvoke(PropInvokeReq req);

    Boolean propPay(PropBuyReq propBuyReq);
}
