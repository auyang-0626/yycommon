package com.yang.yy.common.limitcheck.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {

    @RequestMapping(value = "/resource",method = {RequestMethod.GET})
    public String resource(){

        return "resource";
    }
}
