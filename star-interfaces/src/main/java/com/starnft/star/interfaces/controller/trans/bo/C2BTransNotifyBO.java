package com.starnft.star.interfaces.controller.trans.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class C2BTransNotifyBO {


    /**
     * head
     */
    private HeadDTO head;
    /**
     * body
     */
    private BodyDTO body;
}
