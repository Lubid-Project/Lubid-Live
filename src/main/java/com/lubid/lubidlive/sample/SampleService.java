package com.lubid.lubidlive.sample;

import com.lubid.lubidlive.sample.mongomodel.SampleChatModel;
import com.lubid.lubidlive.sample.mongomodel.SampleRoomModel;
import com.lubid.lubidlive.sample.mongorespository.SampleChatModelRepository;
import com.lubid.lubidlive.sample.mongorespository.SampleRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.ReactiveFindOperation;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class SampleService {

    private final SampleRoomRepository roomRepository;

    private final SampleChatModelRepository chatModelRepository;

    // @tailable 어노테이션의 문제점이 발견되어, 템플레이트로 변경함, 원인은 나중에 찾아서 해결하겠음.
    private final ReactiveMongoTemplate reactiveMongoTemplate;


    /**
     * 채팅방 접속을 요청
     * tailable 을 통해 sse 요청 활성화
     * */
    public Flux<ResponseEntity<SampleChatModel>> requestRoom(int roomNum){
        Query query = new Query(Criteria.where("roomNum").is(roomNum));
        log.info("requestRoom : " + roomNum);
        ReactiveFindOperation.TerminatingFind<SampleChatModel> find = reactiveMongoTemplate
                .query(SampleChatModel.class).matching(query);

        return find.tail()
                .doOnSubscribe(subscription -> log.info("Subscription started"))
                .doOnNext(chatModel -> log.info("Received chat model: " + chatModel))
                .map(chatModel -> new ResponseEntity<>(chatModel, HttpStatus.OK))
                .doOnComplete(() -> log.info("Stream completed"))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No chat models found, returning BAD_REQUEST");
                    return Mono.just(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
                }));
    }


    /**
     * 메세지를 날려 메세지를 저장
     * */
    public Mono<SampleChatModel> sendMsg(SampleChatModel chatModel){

        // Room Check
        SampleRoomModel roomModel = roomRepository
                .roomCheck(chatModel.getRoomNum()).subscribeOn(Schedulers.immediate()).block();

        if(roomModel != null){
            log.info("send message : " + chatModel.getMsg());
            return chatModelRepository.save(chatModel);
        }else{
            log.info("not found room : " + chatModel.getMsg());
            return null;
        }

    }

    /**
     * 방 제작
     * 방을 만들어서 저장한다.
     * */
    public Mono<SampleRoomModel> makeRoom(SampleRoomModel room){
        return roomRepository.save(room);
    }

}
