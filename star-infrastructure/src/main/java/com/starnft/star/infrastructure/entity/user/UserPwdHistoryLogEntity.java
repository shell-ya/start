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
@TableName("user_pwd_history_log")
public class UserPwdHistoryLogEntity extends BaseEntity {

    @Id
    @TableField("id")
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("password")
    private String password;
}
