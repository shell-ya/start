package com.starnft.star.infrastructure.entity.prop;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "道具信息表", description = "")
@TableName("star_nft_prop_info")
public class StarNftPropInfo extends BaseEntity implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(name = "id", notes = "")
    @TableId
    private Long id;
    /**
     * 道具类型
     */
    @ApiModelProperty(name = "道具类型", notes = "")
    private Integer propType;
    /**
     * 道具名称
     */
    @ApiModelProperty(name = "道具名称", notes = "")
    private String propName;
    /**
     * 道具等级
     */
    @ApiModelProperty(name = "道具等级", notes = "")
    private Integer propLevel;
    /**
     * 道具过期时间
     */
    @ApiModelProperty(name = "道具过期时间", notes = "")
    private Date expire;
    /**
     * 道具图标
     */
    @ApiModelProperty(name = "道具图标", notes = "")
    private String propIcon;
    /**
     * 道具可使用时间
     */
    @ApiModelProperty(name = "道具可使用时间", notes = "")
    private Date propTime;
    /**
     * 道具描述
     */
    @ApiModelProperty(name = "道具描述", notes = "")
    private String propDesc;

    /**
     * 道具描述
     */
    @ApiModelProperty(name = "道具执行器", notes = "")
    private String execution;

    /**
     * 是否上架
     */
    @ApiModelProperty(name = "是否上架 0未上架 1上架", notes = "")
    private Integer onSell;

    /**
     * 是否可以购买
     */
    @ApiModelProperty(name = "是否可以购买", notes = "")
    private Integer canBuy;

    /**
     * 出售价格
     */
    @ApiModelProperty(name = "出售价格", notes = "")
    private BigDecimal price;

    /**
     * 出售数量 -1 不限量
     */
    @ApiModelProperty(name = "出售数量 -1 不限量", notes = "")
    private BigDecimal stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPropType() {
        return propType;
    }

    public void setPropType(Integer propType) {
        this.propType = propType;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Integer getPropLevel() {
        return propLevel;
    }

    public void setPropLevel(Integer propLevel) {
        this.propLevel = propLevel;
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

    public Date getPropTime() {
        return propTime;
    }

    public void setPropTime(Date propTime) {
        this.propTime = propTime;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public Integer getOnSell() {
        return onSell;
    }

    public void setOnSell(Integer onSell) {
        this.onSell = onSell;
    }

    public Integer getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(Integer canBuy) {
        this.canBuy = canBuy;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
}
