package com.starnft.star.infrastructure.entity.prop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "用户道具关联表", description = "")
@TableName("star_nft_prop_relation")
public class StarNftPropRelation extends BaseEntity implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(name = "用户id", notes = "")
    private Long uid;
    /**
     * 道具id
     */
    @ApiModelProperty(name = "道具id", notes = "")
    private Long propId;
    /**
     * 道具数量
     */
    @ApiModelProperty(name = "道具数量", notes = "")
    private Integer propCounts;
    /**
     * 过期时间
     */
    @ApiModelProperty(name = "过期时间", notes = "")
    private Date expire;
    /**
     * 道具图标
     */
    @ApiModelProperty(name = "道具图标", notes = "")
    private String propIcon;
    /**
     * 道具等级
     */
    @ApiModelProperty(name = "道具等级", notes = "")
    private Integer propLevel;
    /**
     * 道具类型
     */
    @ApiModelProperty(name = "道具类型", notes = "")
    private Integer propType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Integer getPropCounts() {
        return propCounts;
    }

    public void setPropCounts(Integer propCounts) {
        this.propCounts = propCounts;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getPropIcon() {
        return propIcon;
    }

    public void setPropIcon(String propIcon) {
        this.propIcon = propIcon;
    }

    public Integer getPropLevel() {
        return propLevel;
    }

    public void setPropLevel(Integer propLevel) {
        this.propLevel = propLevel;
    }

    public Integer getPropType() {
        return propType;
    }

    public void setPropType(Integer propType) {
        this.propType = propType;
    }
}
