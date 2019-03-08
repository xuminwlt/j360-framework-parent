package me.j360.framework.web.example.configuration;

import com.vip.vjtools.vjkit.mapper.JsonMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.exception.ServiceException;
import me.j360.framework.boot.error.ValidationException;
import me.j360.framework.common.exception.BizException;
import me.j360.framework.common.web.context.SessionContext;
import me.j360.framework.common.web.result.ApiResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: min_xu
 * @date: 2019/1/11 10:28 AM
 * 说明：
 */

@Slf4j
@RestController
@RestControllerAdvice
@Api(value = "/", tags = "错误显示")
public class RestrictedErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse error(HttpServletRequest request, Model model) {
        Map<String, Object> errorMap = errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        Throwable throwable = errorAttributes.getError(new ServletWebRequest(request)).getCause();
        log.warn("接口异常-ServiceException :[{}, {}]", SessionContext.getBaseSessionUser(), getExceptionHeadersMessage(request));
        return createExResult(throwable);
    }


    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse handleException(AuthorizationException e, Model model) {
        return ApiResponse.fail(403, "会话异常");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse handleException(NullPointerException e, Model model) {
        return ApiResponse.fail(1, "系统异常");
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse handleException(BizException e, Model model) {
        return ApiResponse.fail(e.getExceptionCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse exHandler(HttpServletRequest request, Exception e) {
        log.error("接口异常-Exception={}", getExceptionHeadersMessage(request) ,e);
        return createExResult(new RuntimeException(e));
    }

    @ExceptionHandler({ServletRequestBindingException.class,
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            TypeMismatchException.class,
    })
    @ResponseBody
    public ApiResponse ServletRequestBindingExHandler(HttpServletRequest request, Exception ex) {
        log.warn("接口异常-SpringException={}",getExceptionHeadersMessage(request), ex);
        return createExResult(new ValidationException(ex));
    }


    /**
     * 数据绑定异常处理
     *
     * @param bindEx 数据绑定异
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ApiResponse bindExHandler(HttpServletRequest request, BindException bindEx) {
        log.warn("接口异常-BindException={}", getExceptionHeadersMessage(request), bindEx);
        return createExResult(new ValidationException(bindEx, bindEx.getBindingResult()));
    }

    /**
     * 方法参数无效异常处理
     *
     * @param methodArgumentNotValidEx 方法参数无效异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse methodArgumentNotValidExHandler(MethodArgumentNotValidException methodArgumentNotValidEx) {
        log.warn("接口异常-MethodArgumentNotValidException",methodArgumentNotValidEx);
        return createExResult(new ValidationException(methodArgumentNotValidEx, methodArgumentNotValidEx.getBindingResult()));
    }

    /**
     * 创建异常结果
     *
     * @param unCheckedEx 异常
     * @return Result
     */
    public ApiResponse createExResult(Throwable unCheckedEx) {
        if (ValidationException.class.isInstance(unCheckedEx)) {
            //return createValidationResult(1, (ValidationException) unCheckedEx);
        }
        if (unCheckedEx instanceof ServiceException) {
            return ApiResponse.fail(((ServiceException) unCheckedEx).getExceptionCode(), ((ServiceException) unCheckedEx).getMessage());
        }
        if (unCheckedEx instanceof BizException) {
            return ApiResponse.fail(((BizException) unCheckedEx).getExceptionCode(), ((BizException) unCheckedEx).getMessage());
        }
        return ApiResponse.fail(1, unCheckedEx.getMessage());
    }

    private String getExceptionHeadersMessage(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<String, String>();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        //path
        String path = request.getRequestURI();
        headers.put("path",path);

        //method
        String method = request.getMethod();
        headers.put("method",method);

        //param
        Map<String, String[]> param = request.getParameterMap();
        headers.put("param", JsonMapper.INSTANCE.toJson(param));
        return JsonMapper.INSTANCE.toJson(headers);
    }
}
