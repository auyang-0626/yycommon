package com.yang.yy.common.limitcheck.web;

import com.yang.yy.common.limit.LimitFacade;
import com.yang.yy.common.limitcheck.domain.Resource;
import com.yang.yy.common.limitcheck.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Slf4j
@RestController
@RequestMapping("/check")
public class CheckController {

    private ThreadLocal<Resource> testResourceThreadLocal = new ThreadLocal<>();
    private ThreadLocal<Resource> userResourceThreadLocal = new ThreadLocal<>();
    @Autowired
    private LimitFacade limitFacade;
    @Autowired
    private ResourceRepository resourceRepository;

    @Transactional
    @RequestMapping(value = "/test-resource",method = {RequestMethod.GET})
    public String testResource(HttpServletResponse response) {
        long start = System.nanoTime();
        boolean acquire = limitFacade.acquireIgnoreException("test-resource");
        log.info("test 请求资源时间 {}", System.nanoTime() - start);
        if (!acquire) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

        if (testResourceThreadLocal.get() == null) {
            String name = String.format("test-resource-%d", Thread.currentThread().getId());
            Resource resource = resourceRepository.findByName(name);
            if (resource == null) {
                resource = new Resource(name, 0L);
                resourceRepository.save(resource);
            }
            testResourceThreadLocal.set(resource);
        }
        resourceRepository.increment(testResourceThreadLocal.get().getId());
        return "resource";
    }

    @Transactional
    @RequestMapping(value = "/user-resource",method = {RequestMethod.GET})
    public String userResource(HttpServletResponse response) {
        long start = System.nanoTime();
        boolean acquire = limitFacade.acquireIgnoreException("user-resource");
        log.info("test 请求资源时间 {}", System.nanoTime() - start);

        if (!acquire) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

        if (userResourceThreadLocal.get() == null) {
            String name = String.format("user-resource-%d", Thread.currentThread().getId());
            Resource resource = resourceRepository.findByName(name);
            if (resource == null) {
                resource = new Resource(name, 0L);
                resourceRepository.save(resource);
            }
            userResourceThreadLocal.set(resource);
        }
        resourceRepository.increment(userResourceThreadLocal.get().getId());
        return "resource";
    }
}
