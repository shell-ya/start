package com.starnft.star.domain.publisher.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublisherVO implements Serializable {
    private Long authorId;
    private String authorPic;
    private String authorName;
    private String publisherName;
    private String publisherPic;
    private Long  publisherId;
}
