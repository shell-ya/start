package com.starnft.star.application.process.compose.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMaterialReq implements Serializable {
    private Long  categoryId;
    private Long  userId;
}
