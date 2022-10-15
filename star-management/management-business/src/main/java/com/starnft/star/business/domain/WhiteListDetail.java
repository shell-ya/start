package com.starnft.star.business.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "白名单详情表", description = "")
@TableName("white_list_detail")
public class WhiteListDetail  implements Serializable {

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
    private Long whiteId;
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

    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("created_at")
    private Date createdAt;

    @TableField("created_by")
    private Long createdBy;

    @TableField("modified_at")
    private Date modifiedAt;

    @TableField("modified_by")
    private Long modifiedBy;
}
