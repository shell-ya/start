package com.starnft.star.domain.wallet.service.stateflow.event;

import com.starnft.star.common.Result;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.domain.wallet.model.vo.WalletRecordVO;
import com.starnft.star.domain.wallet.service.stateflow.AbstractState;
import org.springframework.stereotype.Component;

@Component
public class WaitPayState extends AbstractState {

    @Override
    public Result waitPay(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        WalletRecordVO walletRecordVO = walletRepository.queryWalletRecordBySerialNo(orderNo, payStatus.name());
        if (null == walletRecordVO) {
            return Result.buildErrorResult("订单不存在");
        }
        return Result.buildErrorResult("订单已经在等待支付状态");
    }

    @Override
    public Result paying(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        boolean isSuccess = walletRepository.updateWalletRecordStatus(orderNo, StarConstants.Pay_Status.PAY_ING.name());
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改为支付中失败");
    }

    @Override
    public Result paySuccess(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return Result.buildErrorResult("该状态不可修改为支付成功");
    }

    @Override
    public Result payFailure(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        return Result.buildErrorResult("该状态不可修改为支付失败");
    }

    @Override
    public Result payClose(String orderNo, Enum<StarConstants.Pay_Status> payStatus) {
        boolean isSuccess = walletRepository.updateWalletRecordStatus(orderNo, StarConstants.Pay_Status.PAY_CLOSE.name());
        return isSuccess ? Result.buildSuccessResult() : Result.buildErrorResult("状态修改失败");
    }
}
