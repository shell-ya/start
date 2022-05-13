package com.starnft.star.management.exception;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author LaIWeiChun
 */
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class StarExceptionHandler {

    @ExceptionHandler({StarException.class})
    public RopResponse businessExceptionHandle(StarException e) {
        return RopResponse.fail(e.getArkError(), e.getExMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RopResponse ValidationExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult exceptions = ((MethodArgumentNotValidException) e).getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return RopResponse.fail(fieldError.getDefaultMessage());
            }
        }
        return RopResponse.fail("参数有误");
    }


}
