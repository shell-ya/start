package com.starnft.star.domain.wallet.service.stateflow.event;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

@Component
public class PayingState extends AbstractState {

    @Override
    public Result waitPay(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        boolean isSuccess = walletRepository.updateWalletRecordStatus(orderNo, StarConstants.Pay_Status.WAIT_PAY.name());
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改失败");
    }

    @Override
    public Result paying(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(orderNo, payStatus.name());
        if (null == walletRecordVO) {
            return Result.buildErrorResult("订单不存在");
        }
        return Result.buildErrorResult("订单已经在支付中状态");
    }

    @Override
    public Result paySuccess(String orderNo, String outTradeNo, Enum<StarConstants.Pay_Status> payStatus) {
        boolean isSuccess = walletRepository.updateWalletRecordSuccess(orderNo, outTradeNo, StarConstants.Pay_Status.PAY_SUCCESS.name());
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改为成功失败");
    }

    @Override
    public Result payFailure(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        boolean isSuccess = walletRepository.updateWalletRecordStatus(orderNo, StarConstants.Pay_Status.PAY_FAILED.name());
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改失败");
    }

    @Override
    public Result payClose(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return Result.buildErrorResult("该状态不可修改为支付关闭");
    }
}
