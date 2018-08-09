package com.yang.yy.common.limitcheck.web;

import com.yang.yy.common.limit.LimitFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private LimitFacade limitFacade;

    @RequestMapping(value = "/test-resource",method = {RequestMethod.GET})
    public String testResource(HttpServletResponse response) {
        long start = System.nanoTime();
        boolean acquire = limitFacade.acquireIgnoreException("test-resource",10000);
        log.info("test 请求资源时间 {} 纳秒", System.nanoTime() - start);
        if (!acquire) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return "resource";
    }

    @RequestMapping(value = "/user-resource",method = {RequestMethod.GET})
    public String userResource(HttpServletResponse response) {
        long start = System.nanoTime();
        boolean acquire = limitFacade.acquireIgnoreException("user-resource",0);
        log.info("user 请求资源时间 {} 纳秒", System.nanoTime() - start);

        if (!acquire) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return "resource";
    }
}
