package com.demo.gateway2x.decorator;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.scheduler.Schedulers.single;

@Slf4j
public class PartnerServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    PartnerServerHttpResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        final MediaType contentType = super.getHeaders().getContentType();
        if (ParamsUtils.CHAIN_MEDIA_TYPE.contains(contentType)) {
            if (body instanceof Mono) {
                final Mono<DataBuffer> monoBody = (Mono<DataBuffer>) body;
                return super.writeWith(monoBody.publishOn(single()).map(dataBuffer -> ResponseParamsHandle.chain(log, dataBuffer)));
            } else if (body instanceof Flux) {
                Mono<DataBuffer> mono = DataBufferUtils.join(body);
                final Flux<DataBuffer> monoBody = mono.publishOn(single()).map(dataBuffer -> ResponseParamsHandle.chain(log, dataBuffer)).flux();
                return super.writeWith(monoBody);
            }
        }
        return super.writeWith(body);
    }

}
