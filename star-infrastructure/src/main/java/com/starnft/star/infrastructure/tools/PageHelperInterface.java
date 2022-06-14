package com.starnft.star.infrastructure.tools;

import com.github.pagehelper.PageInfo;
import com.starnft.star.common.page.ResponsePageResult;

import java.util.List;

public interface PageHelperInterface {

    default <S, R> ResponsePageResult<R> listReplace(PageInfo<S> pageInfo, List<R> list) {
        ResponsePageResult<R> rResponsePageResult = new ResponsePageResult<>();
        rResponsePageResult.setList(list);
        rResponsePageResult.setPage(pageInfo.getPageNum());
        rResponsePageResult.setSize(pageInfo.getPageSize());
        rResponsePageResult.setTotal(pageInfo.getTotal());
        return rResponsePageResult;
    }
}
