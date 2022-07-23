package com.starnft.star.business.service;

import com.starnft.star.business.domain.PromoteResult;

import java.io.InputStream;

public interface IPromoteService {
    PromoteResult promoteExcel(InputStream inputStream, String context, Integer types);

}
