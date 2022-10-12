package com.starnft.star.infrastructure.entity.raising;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("star_raising_detail")
public class StarRaisingDetail extends BaseEntity {

    @TableId
    private Long id;
    @TableField("theme_info_id")
    private Long themeInfoId;
    @TableField("limit_price")
    private BigDecimal limitPrice;
    @TableField("opening_price")
    private BigDecimal openingPrice;
    @TableField("is_raising")
    private Boolean isRaising;

}
