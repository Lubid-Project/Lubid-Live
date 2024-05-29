package com.lubid.lubidlive.sample.mongorespository;

import com.lubid.lubidlive.sample.mongomodel.SampleRoomModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SampleRoomRepository extends ReactiveMongoRepository<SampleRoomModel, Integer> {

    @Query("{roomNum : ?0}")
    Mono<SampleRoomModel> roomCheck(int roomNum);

}
