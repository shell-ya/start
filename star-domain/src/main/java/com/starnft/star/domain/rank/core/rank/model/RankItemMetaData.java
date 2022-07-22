package com.starnft.star.domain.rank.core.rank.model;

import lombok.Data;

import java.util.Date;

@Data
public class RankItemMetaData {
     private Long  childrenId;
     private String nickName;
     private String mobile;
     private String avatar;
     private Date invitationTime;
}
