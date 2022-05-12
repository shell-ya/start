package com.starnft.star.interfaces.exception;

import com.starnft.star.common.RopResponse;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

/**
 * @author LaIWeiChun
 */
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class StarExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public RopResponse businessExceptionHandle(Exception e) {
        if (e instanceof StarException) {
            StarException starException = (StarException) e;
            String exMessage = null;
            if (StringUtils.isNotBlank(starException.getExMessage())) {
                exMessage = starException.getExMessage();
            } else {
                exMessage = starException.getArkError().getErrorMessage();
            }
            return RopResponse.fail(starException.getArkError(), exMessage);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult exceptions = validException.getBindingResult();
            // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
            if (exceptions.hasErrors()) {
                List<ObjectError> errors = exceptions.getAllErrors();
                if (!errors.isEmpty()) {
                    FieldError fieldError = (FieldError) errors.get(0);
                    return RopResponse.fail(fieldError.getDefaultMessage());
                }
            }
        } else if (e instanceof RuntimeException) {
            RuntimeException runtimeException = (RuntimeException) e;
            return RopResponse.fail(runtimeException.getMessage());
        }

        return RopResponse.fail("系统错误");

    }


}
