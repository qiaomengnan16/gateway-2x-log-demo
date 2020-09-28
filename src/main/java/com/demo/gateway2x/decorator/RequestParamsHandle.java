package com.demo.gateway2x.decorator;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParamsHandle {

    public static <T extends DataBuffer> T chain(ServerHttpRequest delegate, Logger log, T buffer) {
        ParamsUtils.BodyDecorator bodyDecorator = ParamsUtils.buildBodyDecorator(buffer);
        // 参数校验 和 参数打印
        log.info("Payload: {}", JSON.toJSONString(validParams(getParams(delegate, bodyDecorator.getBody()))));
        return (T) bodyDecorator.getDataBuffer();
    }

    public static Map<String,Object> getParams(ServerHttpRequest delegate, String body) {
        // 整理参数
        Map<String,Object> params = new HashMap<>();
        if (delegate.getQueryParams() != null) {
            params.putAll(delegate.getQueryParams());
        }
        if (!StringUtils.isEmpty(body)) {
            params.putAll(JSON.parseObject(body));
        }
        return params;
    }

    public static Map<String,Object> validParams(Map<String,Object> params) {
        // todo 参数校验
        return params;
    }

}
