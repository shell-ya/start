package com.starnft.star.infrastructure.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "白名单详情表", description = "")
@TableName("white_list_detail")
public class WhiteListDetail extends BaseEntity implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 白名单id
     */
    @ApiModelProperty(name = "白名单id", notes = "")
    private Integer whiteId;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Integer uid;
    /**
     * 剩余次数
     */
    @ApiModelProperty(name = "剩余次数", notes = "")
    private Integer surplusTimes;
    /**
     * 乐观锁
     */
    @ApiModelProperty(name = "乐观锁", notes = "")
    private Integer version;
}
