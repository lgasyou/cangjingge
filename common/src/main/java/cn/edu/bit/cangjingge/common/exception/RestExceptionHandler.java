package cn.edu.bit.cangjingge.common.exception;

import cn.edu.bit.cangjingge.common.response.Result;
import cn.edu.bit.cangjingge.common.response.ResultStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    // 处理本应用程序中出现的常规非致命性异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<String> handleBusinessException(BusinessException exception) {
        return ResultUtil.error(exception.getCode(), exception.getMessage());
    }

    // 处理本应用程序中出现的UndeclaredThrowable异常
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseBody
    public Result<String> handleUndeclaredThrowableException(UndeclaredThrowableException exception) {
        Throwable throwable = exception.getCause();
        if (throwable instanceof BusinessException) {
            BusinessException cre = (BusinessException)throwable;
            return handleBusinessException(cre);
        }
        return handleUnknownError(exception);
    }

    // 处理未能正常捕获的异常，并将其记录
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> handleUnknownError(Exception exception) {
        logger.error("Unhandled Error：", exception);
        ResultStatusEnum error = ResultStatusEnum.INTERNAL_SERVER_ERROR;
        return ResultUtil.error(error.getStatus(), error.getReason());
    }

}
