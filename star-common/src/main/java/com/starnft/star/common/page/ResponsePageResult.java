package com.starnft.star.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageResult<T> {
    private List<T> list;
    private Integer page;
    private Integer size;
    private Long total;

    public static <S, R> ResponsePageResult<R> listReplace(ResponsePageResult<S> responsePageResult, List<R> expected) {
        ResponsePageResult<R> r = new ResponsePageResult<R>();
        r.setList(expected);
        r.setPage(responsePageResult.getPage());
        r.setTotal(responsePageResult.getTotal());
        r.setSize(responsePageResult.getSize());
        return r;
    }
}
