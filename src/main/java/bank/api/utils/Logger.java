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

        org.slf4j.Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass().getSimpleName() + ".class");

        try {
            logger.info(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + " start with args: " + Arrays.toString(pjp.getArgs()));

            Object result = pjp.proceed();

            logger.info(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + " end");

            return result;
        } catch (NotFoundException e) {
            logger.error(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + ": Error message = " + e.getMessage());
            throw new NotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName() + ": Error message = " + e.getMessage());
            throw new WrongArgumentException(e.getMessage(), e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


}
