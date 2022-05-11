package com.starnft.star.domain.user.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AgreementIdDTO {

    private Long userId;

    private List<String> agreementIds;
}
