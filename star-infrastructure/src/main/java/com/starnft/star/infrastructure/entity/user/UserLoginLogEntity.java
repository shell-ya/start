package com.starnft.star.infrastructure.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.starnft.star.infrastructure.entity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author WeiChunLAI
 */
@Data
@TableName("use_login_log")
public class UserLoginLogEntity extends BaseEntity {

    @Id
    @TableField("id")
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("login_status")
    private Integer loginStatus;
}
