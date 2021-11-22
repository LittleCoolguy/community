package com.gao.community.interceptor;

import com.gao.community.entity.Notification;
import com.gao.community.entity.User;
import com.gao.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @description:
 *      登录拦截器
 *      实现三天免登录+获取用户未读信息数
 * @author: XiaoGao
 * @time: 2021/11/3 18:59
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private NotificationService notificationService;
    //有意思，实现类进行注释，接口也可以自动注入？

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        System.out.println("登录拦截器执行");
        Cookie[] cookies=request.getCookies();
        if (cookies==null||cookies.length==0)
            return true;
        for (Cookie cookie:cookies){
            if ("token".equals(cookie.getName())){
                System.out.println("自动登录功能执行");
                String token=cookie.getValue();
                User o=(User)redisTemplate.opsForValue().get(token);
                //根据cookie中存储的token获取redis中存储的User
                System.out.println(o);
                if(o!=null){
                    request.getSession().setAttribute("user",o);
                    Integer unreadCount=notificationService.unreadCount(o.getId());
                    request.getSession().setAttribute("unreadCount",unreadCount);
//                    System.out.println("================Session================");
//                    HttpSession session = request.getSession();
//                    for (Enumeration e = session.getAttributeNames(); e.hasMoreElements(); )
//                    {
//                        System.err.println(e.nextElement()+
//                                "-----"+session.getAttribute((String) e.nextElement()));
//                    }
////                    ======================================
                }
                break;

            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
