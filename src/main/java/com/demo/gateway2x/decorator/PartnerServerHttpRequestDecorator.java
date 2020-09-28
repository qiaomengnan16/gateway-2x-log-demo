package com.demo.gateway2x.decorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.scheduler.Schedulers.single;

@Slf4j
public class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private Flux<DataBuffer> body;

    public PartnerServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        Flux<DataBuffer> flux = super.getBody();
        if (ParamsUtils.CHAIN_MEDIA_TYPE.contains(delegate.getHeaders().getContentType())) {
            Mono<DataBuffer> mono = DataBufferUtils.join(flux);
            body = mono.publishOn(single()).map(dataBuffer -> RequestParamsHandle.chain(delegate, log, dataBuffer)).flux();
        } else {
            body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

}
