package com.fastcampus.boardserver.aop;

import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Aspect
public class LoginCheckAspect {
    @Around("@annotation(com.fastcampus.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = (HttpSession) ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();

        String userId = null;

        String userType = loginCheck.type().toString();

        switch(userType) {
            case "ADMIN":
                userId = SessionUtil.getLoginAdminID(session);
                break;
            case "USER":
                userId = SessionUtil.getLoginMemberId(session);
                break;
        }

        if(userId == null) {
            log.error(proceedingJoinPoint.toString() + " userId : " + userId);
            throw new BoardServerException(HttpStatus.UNAUTHORIZED, "로그인 id를 확인해 주세요.");
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();

        int index = 0;
        if(proceedingJoinPoint.getArgs()!=null)
            modifiedArgs[index] = userId;

        return proceedingJoinPoint.proceed(modifiedArgs);
    }


}
