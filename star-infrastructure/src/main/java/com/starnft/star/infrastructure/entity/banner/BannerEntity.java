package com.starnft.star.infrastructure.entity.banner;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2022/5/9 10:21 AM
 * @Author ： shellya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("banner")
public class BannerEntity extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 图片地址
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 展示位置：头部，TOP; 中间，MIDDLE；底部，BOTTOM;
     */
    @TableField(value = "`position`")
    private Integer position;

    /**
     * 是否展示：0，否；1，是
     */
    @TableField(value = "display")
    private Byte display;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 爬虫地址
     */
    @TableField(value = "jump_type")
    private Integer jumpType;

    /**
     * 外链地址
     */
    @TableField(value = "url")
    private String url;

}
