package com.starnft.star.management.controller;

import com.starnft.star.common.RopResponse;
import com.starnft.star.infrastructure.entity.dict.StarNftDict;
import com.starnft.star.management.service.IDictManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/dict")
public class DictionaryOperationController {

    private IDictManger dictManger;

    @PostMapping("/create")
    public RopResponse<?> createDict(@RequestBody List<@Valid StarNftDict> list) {

        if (dictManger.createDict(list) > 0) {
            return RopResponse.successNoData();
        }
        return RopResponse.fail("插入失败");

    }

    @Autowired
    public void setDictManger(IDictManger dictManger) {
        this.dictManger = dictManger;
    }
}
