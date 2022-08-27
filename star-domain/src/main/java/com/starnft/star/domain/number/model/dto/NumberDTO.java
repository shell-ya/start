package com.starnft.star.domain.number.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberDTO implements Serializable {
    private Long themeId;
    private Long ownerBy;

}
