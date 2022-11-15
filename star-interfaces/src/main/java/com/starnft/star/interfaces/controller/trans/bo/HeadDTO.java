package com.starnft.star.interfaces.controller.trans.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HeadDTO
 */
@NoArgsConstructor
@Data
public class HeadDTO {
    /**
     * version
     */
    private String version;
    /**
     * respTime
     */
    private String respTime;
    /**
     * respCode
     */
    private String respCode;
    /**
     * respMsg
     */
    private String respMsg;
}
