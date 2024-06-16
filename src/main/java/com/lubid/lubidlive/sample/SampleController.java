package com.lubid.lubidlive.sample;

import com.lubid.lubidlive.sample.mongomodel.SampleChatModel;
import com.lubid.lubidlive.sample.mongomodel.SampleRoomModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/lubid-live/auth/sample")
@RestController
public class SampleController {

    private final SampleService sampleService;

    @PostMapping("make-room")
    public Mono<SampleRoomModel> makeRoom(@RequestBody SampleRoomModel roomModel){

        return sampleService.makeRoom(roomModel);
    }

    @PostMapping("send-message")
    public Mono<SampleChatModel> send(@RequestBody SampleChatModel sampleChatModel){

        return sampleService.sendMsg(sampleChatModel);
    }

    @GetMapping(value = "live/request-room/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<SampleChatModel>> requestRoom(@PathVariable Integer roomNum) {
        return sampleService.requestRoom(roomNum)
                .filter(data -> data.getBody() != null)
                .doOnNext(data -> {
                    log.info("Data received: {}", data.getBody());
                })
                .map(data -> ServerSentEvent.builder(data.getBody())
                        .event("message")
                        .build())
                .doOnComplete(() -> log.info("Completed sending events"))
                .doOnError(error -> log.error("Error occurred: {}", error.getMessage()));
    }

    @GetMapping(value = "live/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> requestSseTest() {
        log.info("sse test start");
        return Flux.interval(Duration.ofSeconds(1))
                .doOnNext(data -> {
                    log.info("Data received: {}", data);
                })
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("message")
                        .data("Sample Event - " + sequence)
                        .build());
    }

    // 아래 코드부터는 sse 방식이 아닌 webSocket 방식 입니다.

    /**
     * 웹 소켓 컨트롤러
     * */
    @GetMapping(value = "live-socket/test")
    public Flux<?> requestSocketTest(){
        log.info("socket test start");

        return null;
    }

}
