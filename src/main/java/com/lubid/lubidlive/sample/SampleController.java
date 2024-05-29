package com.lubid.lubidlive.sample;

import com.lubid.lubidlive.sample.mongomodel.SampleChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/lubid-live/auth/")
@RestController
public class SampleController {

    @PostMapping("send-message")
    public Mono<SampleChatModel> send(SampleChatModel sampleChatModel){

        return null;
    }

}
