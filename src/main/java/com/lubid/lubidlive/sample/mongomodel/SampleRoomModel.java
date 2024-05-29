package com.lubid.lubidlive.sample.mongomodel;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "sampleRoomModel")
public class SampleRoomModel {

    @Id
    private String id;

    @Indexed(unique = true)
    private Integer roomNum;

    private String rommTitle;
    private String videoUrl;
    private String thumbnail;
    private int chiefId;
    private String chief;
    private LocalDateTime createAt;
}
