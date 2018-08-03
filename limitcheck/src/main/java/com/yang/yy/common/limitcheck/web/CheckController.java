package com.yang.yy.common.limitcheck.web;

import com.yang.yy.common.limit.LimitFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private LimitFacade limitFacade;
    @RequestMapping(value = "/resource",method = {RequestMethod.GET})
    public String resource(){
        boolean b = limitFacade.acquirIgnoreException("test-resource");
        return "resource";
    }
}
