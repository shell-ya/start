package com.starnft.star.infrastructure.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "白名单配置表", description = "")
@TableName("white_list_config")
public class WhiteListConfig extends BaseEntity implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 白名单描述
     */
    @ApiModelProperty(name = "白名单描述", notes = "")
    private String whiteDesc;
    /**
     * 白名单类型
     */
    @ApiModelProperty(name = "白名单类型", notes = "")
    private Integer whiteType;
    /**
     * 是否生效
     */
    @ApiModelProperty(name = "是否生效", notes = "")
    private String effective;

}
