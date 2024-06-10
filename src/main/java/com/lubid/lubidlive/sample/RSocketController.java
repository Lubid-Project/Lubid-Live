package com.lubid.lubidlive.sample;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * WebFlux RSocket Sample 입니다.
 *
 * */
@Controller
public class RSocketController {

    @MessageMapping("request-response")
    public Mono<String> requestResponse(@Payload String message) {
        return Mono.just("Echo: " + message);
    }
}

