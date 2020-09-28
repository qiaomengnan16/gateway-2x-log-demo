package com.demo.gateway2x.decorator;

import org.slf4j.Logger;
import org.springframework.core.io.buffer.DataBuffer;

public class ResponseParamsHandle {

    public static <T extends DataBuffer> T chain(Logger log, T buffer) {
        ParamsUtils.BodyDecorator bodyDecorator = ParamsUtils.buildBodyDecorator(buffer);
        // 参数校验 和 参数打印
        log.info("Payload: {}", bodyDecorator.getBody());
        return (T) bodyDecorator.getDataBuffer();
    }


}
