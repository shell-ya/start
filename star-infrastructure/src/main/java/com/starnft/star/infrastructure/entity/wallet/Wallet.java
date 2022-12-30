package com.starnft.star.infrastructure.entity.wallet;

import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nft_wallet")
public class Wallet extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @Id
    private Integer id;
    /**
     * 钱包id
     */
    @ApiModelProperty(name = "钱包id", notes = "")
    private String wId;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Long uid;
    /**
     * 余额
     */
    @ApiModelProperty(name = "余额", notes = "")
    private BigDecimal balance;
    /**
     * 钱包总收入
     */
    @ApiModelProperty(name = "钱包总收入", notes = "")
    private BigDecimal walletIncome;
    /**
     * 钱包总支出
     */
    @ApiModelProperty(name = "钱包总支出", notes = "")
    private BigDecimal walletOutcome;
    /**
     * 是否被冻结
     */
    @ApiModelProperty(name = "是否被冻结", notes = "")
    private Integer frozen;
    /**
     * 冻结金额
     */
    @ApiModelProperty(name = "冻结金额", notes = "")
    private BigDecimal frozenFee;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;

    // 天河链上地址
    private String thWId;

}
