package com.lubid.lubidlive.sample.mongorespository;

import com.lubid.lubidlive.sample.mongomodel.SampleChatModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface SampleChatModelRepository extends ReactiveMongoRepository<SampleChatModel , String> {

    @Tailable
    @Query("{sender: ?0, receiver: ?1}")
    Flux<SampleChatModel> mFindBySender(String sender, String receiver);

    @Query("{roomNum : ?0}")
    Flux<SampleChatModel> findNonTailableByRoomNum(int roomNum);
}
