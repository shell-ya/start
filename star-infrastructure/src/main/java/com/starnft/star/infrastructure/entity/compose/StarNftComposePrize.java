package com.starnft.star.infrastructure.entity.compose;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "star_nft_compose_prize")
public class StarNftComposePrize implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 合成id
     */
    @TableField(value = "compose_id")
    private Long composeId;

    /**
     * 奖励类型
     */
    @TableField(value = "prize_type")
    private Integer prizeType;

    /**
     * 奖励标记
     */
    @TableField(value = "prize_stamp")
    private String prizeStamp;

    /**
     * 奖励几率
     */
    @TableField(value = "prize_probability")
    private BigDecimal prizeProbability;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 奖品数量
     */
    @TableField(value = "prize_number")
    private BigDecimal prizeNumber;


    @TableField(value = "current_prize_number")
    private BigDecimal currentPrizeNumber;

    /**
     * 修改时间
     */
    @TableField(value = "modified_at")
    private Date modifiedAt;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_COMPOSE_ID = "compose_id";

    public static final String COL_PRIZE_TYPE = "prize_type";

    public static final String COL_PRIZE_STAMP = "prize_stamp";

    public static final String COL_PRIZE_PROBABILITY = "prize_probability";

    public static final String COL_IS_DELETED = "is_deleted";

    public static final String COL_PRIZE_NUMBER = "prize_number";

    public static final String COL_MODIFIED_AT = "modified_at";

    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_CURRENT_PRIZE_NUMBER = "current_prize_number";
}