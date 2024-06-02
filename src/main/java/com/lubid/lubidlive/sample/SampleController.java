package com.lubid.lubidlive.sample;

import com.lubid.lubidlive.sample.mongomodel.SampleChatModel;
import com.lubid.lubidlive.sample.mongomodel.SampleRoomModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<Object> requestRoom(@PathVariable Integer roomNum){
        return sampleService.requestRoom(roomNum)
                .filter(data-> data.getBody() != null)
                .mapNotNull(data -> ServerSentEvent
                        .builder(data)
                        .event("message")
                        .build());
    }

}
