package me.j360.framework.boot.aop;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.constant.BaseErrorCode;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.domain.rpc.result.DefaultPageResult;
import me.j360.framework.base.domain.rpc.result.DefaultResult;
import me.j360.framework.base.exception.RepositoryException;
import me.j360.framework.base.exception.ServiceException;
import me.j360.framework.core.kit.beanvalidator.BeanValidators;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * service层aop切面
 * (切点匹配根据阿里java开发规范，读：get/list/find/count
 * 写:insert/save/add/remove/delete/update)
 *
 * @description
 **/
@Slf4j
@Aspect
@Component
public abstract class AbstractServiceAspect {

    //用于读写后期方法的日志收集功能
    private final static Set<String> PREFIX_READ_SERVICE_NAMES = Sets.newHashSet("get", "list", "find", "query", "count");

    // 方法切点,只切本地的方法,不切外部调用的方法 @Pointcut("execution(* xx.*.service.*Impl.*(..))")
    protected abstract void allPointcut();

    /**
     * 拦截所有的Service实现类的方法,拦截异常并打印
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("allPointcut()")
    public Object serviceAround(ProceedingJoinPoint proceedingJoinPoint) {
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = className + "#" + proceedingJoinPoint.getSignature().getName();
        Class returnType = ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType();
        //log.info("serviceAround: {} ",returnType.getSimpleName());
        try {
            Object result = proceedingJoinPoint.proceed();
            return result;
        } catch (ConstraintViolationException e) {
            List<String> list = BeanValidators.extractMessage(e);
            log.warn("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), e);
            if (Objects.equals(returnType.getSimpleName(), "DefaultPageResult")) {
                return DefaultPageResult.fail(DefaultErrorCode.PARAM_ERROR.getErrorCode(), StringUtils.join(list.toArray(), ";"));
            }
            return DefaultResult.fail(DefaultErrorCode.PARAM_ERROR.getErrorCode(), StringUtils.join(list.toArray(), ";"));
        } catch (NullPointerException e) {
            log.error("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), e);
            if (Objects.equals(returnType.getSimpleName(), "DefaultPageResult")) {
                return DefaultPageResult.fail(DefaultErrorCode.PARAM_ERROR.getErrorCode(), e.getMessage());
            }
            return DefaultResult.fail(DefaultErrorCode.PARAM_ERROR.getErrorCode(), e.getMessage());
        } catch (RepositoryException e) {
            log.error("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), e);
            if (Objects.equals(returnType.getSimpleName(), "DefaultPageResult")) {
                return DefaultPageResult.fail(e.getExceptionCode(), e.getMessage());
            }
            return DefaultResult.fail(e.getExceptionCode(), e.getMessage());
        } catch (ServiceException e) {
            if (e.getExceptionCode() < BaseErrorCode.ERROR_MAX_CODE) {
                log.error("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), e);
            } else {
                log.warn("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), e);
            }
            if (Objects.equals(returnType.getSimpleName(), "DefaultPageResult")) {
                return DefaultPageResult.fail(e.getExceptionCode(), e.getMessage());
            }
            return DefaultResult.fail(e.getExceptionCode(), e.getMessage());
        } catch (Throwable th) {
            log.error("{} failure:[{}]", methodName, getParamInfo(proceedingJoinPoint), th);
            if (Objects.equals(returnType.getSimpleName(), "DefaultPageResult")) {
                return DefaultPageResult.fail(DefaultErrorCode.SYS_ERROR.getErrorCode(), DefaultErrorCode.SYS_ERROR.getErrorMsg());
            }
            return DefaultResult.fail(DefaultErrorCode.SYS_ERROR.getErrorCode(), DefaultErrorCode.SYS_ERROR.getErrorMsg());
        }
    }

    // 获取方法入参
    public String getParamInfo(ProceedingJoinPoint proceedingJoinPoint) {
        StringBuilder strBuilder = new StringBuilder();
        Object[] args = proceedingJoinPoint.getArgs();
        if (Objects.isNull(args) || args.length == 0) {
            return "";
        }
        Arrays.asList(args).stream().forEach(
                arg -> {
                    if (Objects.nonNull(arg)) {
                        strBuilder.append(arg.toString() + ", ");
                    }
                }
        );
        return strBuilder.toString();
    }
}
