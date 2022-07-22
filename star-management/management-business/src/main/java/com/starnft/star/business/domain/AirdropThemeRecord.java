package com.starnft.star.business.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.starnft.star.common.annotation.Excel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@TableName("star_airdrop_theme_record")
public class AirdropThemeRecord {

    @TableId
    private Long id;
    //用户id
    private Long userId;
    //系列id
    private Long seriesId;
    //主题id
    private Long seriesThemeInfoId;
    //编号id
    private Long seriesThemeId;
    //空投编号
    private String airdropSn;
    // 空投类型 1 付费 0 免费
    private Integer airdropType;

    private Integer isDeleted;
    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date createdAt;

    /** 创建人 */
    private String createdBy;

    /** 修改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date modifiedAt;

    /** 修改人 */
    private String modifiedBy;
}
