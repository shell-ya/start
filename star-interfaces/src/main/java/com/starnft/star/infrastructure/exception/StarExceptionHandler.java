package com.starnft.star.infrastructure.exception;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author LaIWeiChun
 */
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class StarExceptionHandler {

    @ExceptionHandler({StarException.class})
    public RopResponse businessExceptionHandle(StarException e) {
        return RopResponse.fail(e.getArkError() , e.getExMessage());
    }


}
