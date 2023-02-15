package bank.api.utils;

import bank.api.exceptions.NotFoundException;
import bank.api.exceptions.WrongArgumentException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logger {

	@Around("@within(org.springframework.stereotype.Service) || @within(org.springframework.web.bind.annotation.RestController)")
	public Object logAroundServicesAndControllers(ProceedingJoinPoint pjp) {

		String           classSimpleName = pjp.getTarget().getClass().getSimpleName();
		String           methodName      = pjp.getSignature().getName();
		Object[]         args            = pjp.getArgs();
		org.slf4j.Logger logger          = LoggerFactory.getLogger(pjp.getTarget().getClass());

		try {
			logger.info(String.format("%s.%s start with args: %s", classSimpleName, methodName, Arrays.toString(args)));

			Object result = pjp.proceed();

			logger.info(String.format("%s.%s end", classSimpleName, methodName));

			return result;
		} catch (NotFoundException e) {
			logger.error(String.format("%s.%s: Error message = %s", classSimpleName, methodName, e.getMessage()));
			throw new NotFoundException(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(String.format("%s.%s: Error message = %s", classSimpleName, methodName, e.getMessage()));
			throw new WrongArgumentException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error(String.format("%s.%s: Error message = %s", classSimpleName, methodName, e.getMessage()), e);
			throw new RuntimeException(e);
		}
	}

}