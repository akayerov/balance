package com.example.balance.interceptor;

import com.example.balance.annotation.TestAnnotation;
import com.example.balance.entities.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomHandlerIntercepter extends HandlerInterceptorAdapter {
    @Override
// вызывается до выполнения метода контролера - крутая вещь можно например проверять здесь аннотацию а по ней права
// пользователя
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("CustomHandlerIntercepter.preHandle");
        if (handler instanceof HandlerMethod) {
            System.out.println("request = " + request + ", response = " + response + ", handler = " + handler);
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            TestAnnotation annotation = handlerMethod.getMethodAnnotation(TestAnnotation.class);
            if (annotation != null) {
                System.out.println("annotation = " + annotation);
                System.out.println("annotation.role() = " + annotation.role());
                User user = (User) request.getAttribute("user");
                System.out.println("handler - user = " + user);

                // do stuff
                if(annotation.role().equals("ADMIN"))
                    return true;
                else {
                    response.setStatus(406);
                    return false;
                }
            }
        }
        return true;
    }
}