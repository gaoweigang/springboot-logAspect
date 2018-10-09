package com.gwg.user.config.log;

import com.alibaba.fastjson.JSONObject;
import com.gwg.user.common.Result;
import com.gwg.user.common.ResultEnum;
import com.gwg.user.exception.BussinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 业务API请求切面
 * 
 */
@Aspect
@Component
public class ApiFlowAspect {
	private final Logger logger = LoggerFactory.getLogger(ApiFlowAspect.class);

	//@Autowired
	//private SaveFlowService saveFlowService;

	@Pointcut(value = "@annotation(apiFlow)", argNames = "apiFlow")
	private void apiLog(ApiFlow apiFlow) {
	}

	@Around(value = "apiLog(apiFlow)", argNames = "apiFlow")
	public Object aroundAdvice(ProceedingJoinPoint pjp, ApiFlow apiFlow) throws Throwable {
		long startTime = System.currentTimeMillis();
		// 1.入参打印
		final StringBuilder params = new StringBuilder();
		for (final Object o : pjp.getArgs()) {
			if (o instanceof String)
				params.append(o);
		}
		final String className = pjp.getSignature().getDeclaringTypeName();
		final String methodName = pjp.getSignature().getName(); // 2.打印出参，返回结果
		final String request = params.toString();
		logger.info("***request:{}.{}-参数:{}--START***", new Object[] { className, methodName, request });
		Result<?> response = new Result<>();
		try {
			response = (Result<?>) pjp.proceed();
		} catch (final IllegalArgumentException e) {
			response.setStatusCode(ResultEnum.FAILURE.getCode());
			response.setMessage("参数不合法:" + e.getMessage());
			logger.error("参数异常", e);
		} catch (final BussinessException e) {
			response.setStatusCode(e.getCode());
			response.setMessage(e.getMessage());
			logger.error("服务异常", e);
		} catch (final Throwable e) {
			response.setStatusCode(ResultEnum.UNKNOW_ERROR.getCode());
			response.setMessage("系统内部异常,请稍后重试");
			logger.error("系统异常", e);
			//MailUtils.allTo("系统内部异常", e.getMessage(), MailUtils.ERROR.ERROR);
		} finally {
			//请求参数与响应结果保存MongDB
			//saveFlowService.saveApiFlow(request, response, apiFlow.value().type());
		}
		long endTime = System.currentTimeMillis();
		long cosTime = endTime - startTime;
		// 2.打印出参，返回结果
		logger.info("***reponse:{}.{}-输出：{}--cosTime:{}ms--END***",
				new Object[] { className, methodName, JSONObject.toJSONString(response), cosTime });
		return response;
	}
}
