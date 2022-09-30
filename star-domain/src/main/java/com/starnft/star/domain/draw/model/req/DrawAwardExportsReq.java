package com.starnft.star.domain.draw.model.req;

import com.starnft.star.common.page.RequestPage;
import lombok.Data;

import java.io.Serializable;

@Data
public class DrawAwardExportsReq extends RequestPage implements Serializable {

    private String uId;

    private String activityId;

}
