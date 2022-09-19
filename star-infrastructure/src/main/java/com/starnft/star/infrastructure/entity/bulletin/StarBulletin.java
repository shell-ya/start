package com.starnft.star.infrastructure.entity.bulletin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2022/7/25 6:57 PM
 * @Author ： shellya
 */
@Data
@TableName("star_bulletin")
public class StarBulletin extends BaseEntity {

    @TableId
    private Long id;

    private Integer bulletinType;

    private String title;

    private Date publishTime;

    private String picUrl;

    private String content;

    private Integer linkType;

    private String linkUrl;
}
