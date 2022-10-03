package com.starnft.star.infrastructure.entity.bulletin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("star_ding_bulletin")
public class StarDingBulletin extends BaseEntity {

    @TableId
    private Long id;

    private String title;

    private String image;

    private String link;

    private Date updateAt;

    private Long updateBy;

}
