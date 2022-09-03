package com.starnft.star.infrastructure.entity.activity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "藏品持有时间记录表", description = "")
@Data
@TableName("goods_having_time_record")
public class GoodsHavingTimeRecord extends BaseEntity implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private long id;
    /**
     * 藏品主题id
     */
    @ApiModelProperty(name = "藏品主题id", notes = "")
    private Long themeInfoId;
    /**
     * 持有人id
     */
    @ApiModelProperty(name = "持有人id", notes = "")
    private Long uid;
    /**
     * 持有累积天数
     */
    @ApiModelProperty(name = "持有累积天数", notes = "")
    private Integer countTimes;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;
}
