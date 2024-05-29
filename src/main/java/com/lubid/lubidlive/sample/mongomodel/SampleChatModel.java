package com.lubid.lubidlive.sample.mongomodel;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collation = "sampleChatModel")
public class SampleChatModel {

    @Id
    private String id;
    private String msg;
    private String sender;
    private String receiver;
    private String profile;
    private int roomNum;
    private LocalDateTime createAt;


}
