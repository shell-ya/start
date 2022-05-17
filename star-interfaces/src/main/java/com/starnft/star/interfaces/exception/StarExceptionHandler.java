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

/**
 * @author LaIWeiChun
 */
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class StarExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({StarException.class})
    public RopResponse businessExceptionHandle(StarException e) {
        String exMessage = null;
        if (StringUtils.isNotBlank(e.getExMessage())) {
            exMessage = e.getExMessage();
        } else {
            exMessage = e.getArkError().getErrorMessage();
        }
        return RopResponse.fail(e.getArkError(), exMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RopResponse valueRuleError(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return RopResponse.fail(fieldError.getDefaultMessage());
            }
        }
        return RopResponse.fail(StarError.SYSTEM_ERROR);
    }



}
