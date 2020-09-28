package com.demo.gateway2x.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloRest {

    @RequestMapping("hello")
    public Map<String, Object> hello(@RequestBody String body) {
        Map<String ,Object> result = new HashMap<>();
        result.put("data", body);
        result.put("code", 200);
        return result;
    }

}
