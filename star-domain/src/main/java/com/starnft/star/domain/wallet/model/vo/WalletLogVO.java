package com.starnft.star.domain.wallet.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletLogVO {

    /** id */
    @ApiModelProperty(name = "id",notes = "")

    private Long id ;
    /** 交易流水号 */
    @ApiModelProperty(name = "交易流水号",notes = "")
    private String recordSn ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Long uid ;
    /** 钱包id */
    @ApiModelProperty(name = "钱包id",notes = "")
    private String wId ;
    /** 变动金额 增+ 减- */
    @ApiModelProperty(name = "变动金额 增+ 减-",notes = "")
    private BigDecimal balanceOffset ;
    /** 变动后的金额 */
    @ApiModelProperty(name = "变动后的金额",notes = "")
    private BigDecimal currentBalance ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 是否显示 0不显示 1显示 */
    @ApiModelProperty(name = "是否显示 0不显示 1显示",notes = "")
    private Integer display ;

    @ApiModelProperty(name = "渠道",notes = "")
    private String channel;
}
