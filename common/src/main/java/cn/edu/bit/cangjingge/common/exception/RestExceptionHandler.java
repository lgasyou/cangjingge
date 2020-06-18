package cn.edu.bit.cangjingge.common.exception;

import cn.edu.bit.cangjingge.common.response.Response;
import cn.edu.bit.cangjingge.common.response.ResponseStatusEnum;
import cn.edu.bit.cangjingge.common.response.ResponseUtil;
import com.netflix.client.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.security.access.AccessDeniedException;
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
    public Response<String> handleBusinessException(BusinessException exception) {
        return ResponseUtil.error(exception.getCode(), exception.getMessage());
    }

//    // 处理本应用程序中出现的拒绝访问异常
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseBody
//    public Response<String> handleAccessDeniedException() {
//        return handleBusinessException(new BusinessException(ResponseStatusEnum.ACCESS_DENIED));
//    }

    // 处理本应用程序中出现的UndeclaredThrowable异常
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseBody
    public Response<String> handleUndeclaredThrowableException(UndeclaredThrowableException exception) {
        Throwable throwable = exception.getCause();
        if (throwable instanceof BusinessException) {
            BusinessException cre = (BusinessException)throwable;
            return handleBusinessException(cre);
        }
//        if (throwable instanceof AccessDeniedException) {
//            return handleAccessDeniedException();
//        }
        if (throwable instanceof RuntimeException) {
            RuntimeException runtimeException = (RuntimeException)throwable;
            return handleRuntimeException(runtimeException);
        }
        return handleUnknownError(exception);
    }

    // 处理本应用程序中出现的拒绝访问异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response<String> handleRuntimeException(RuntimeException exception) {
        Throwable throwable = exception.getCause();
        if (throwable instanceof ClientException) {
            return handleBusinessException(new BusinessException(ResponseStatusEnum.UPSTREAM_SERVER_DOWN));
        }
        return handleUnknownError(exception);
    }

    // 处理未能正常捕获的异常，并将其记录
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<String> handleUnknownError(Exception exception) {
        logger.error("Unhandled Error：", exception);
        ResponseStatusEnum error = ResponseStatusEnum.INTERNAL_SERVER_ERROR;
        return ResponseUtil.error(error.getStatus(), error.getReason());
    }

}
