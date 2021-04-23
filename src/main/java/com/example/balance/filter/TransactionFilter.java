package com.example.balance.filter;

import com.example.balance.annotation.TestAnnotation;
import com.example.balance.controllers.BalanceController;
import com.example.balance.entities.User;
import com.example.balance.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
@Order(1)
public class TransactionFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceController.class);
    @Autowired
    UserService userService;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("Starting a autorization filter req : {}",
                req.getRequestURI());

        if( isSuccessAuthorisation( req )) {
// идти дальше
            User user = (User) req.getAttribute("user");
            System.out.println("user = " + user);
            chain.doFilter(request, response);
        }
        else {
// либо прервать цепочку
// и вернуть ответ
            HttpServletResponse resout = (HttpServletResponse) response;
            resout.setStatus(405);
         }
        LOG.info(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }

    private boolean isSuccessAuthorisation(HttpServletRequest req) {
        String path = req.getRequestURI();
        if(path.contains("/credit")) {
            System.out.println("Control path");
            if (isSuccessAuthorisationUser(req))
                return true;
            else
                return false;
        }
        else
            System.out.println("Public path");
        return true;
    }

    private boolean isSuccessAuthorisationUser(HttpServletRequest req) {
        String token = req.getHeader("token");
        if( token == null) return false;
        User user = userService.findUserByToken(token);
        if( user != null) {
            req.setAttribute("user", user);
            //            isUserRight(req);
            return true;
        }
        else
           return false;
    }

// пробую прочитать анотацию к методу
// Если знать контроллер/метод то права их аннотации можно проверять прямо здесь и далее
// решать пропускать далее или нет
// это можно делать в интерсепторе
    private void isUserRight(HttpServletRequest req) {

        for (Method m : BalanceController.class.getDeclaredMethods())
        {
            if (m.isAnnotationPresent(TestAnnotation.class))
            {
                System.out.println("!!!! annotation enable " + m.getName());
                TestAnnotation anno = m.getAnnotation(TestAnnotation.class);
                System.out.println("!!!!!annotation value " + anno.role());
            }
        }

    }

    // other methods
}
