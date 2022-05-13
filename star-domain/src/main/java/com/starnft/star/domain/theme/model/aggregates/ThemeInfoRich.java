package com.starnft.star.domain.theme.model.aggregates;

import com.starnft.star.domain.theme.model.vo.ThemeDetailVO;
import com.starnft.star.domain.theme.model.vo.ThemeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Supplier;

@Data
public class ThemeInfoRich implements Serializable {

    private Long themeId;

    private ThemeVO themeVO;

    private Supplier<ThemeDetailVO> themeDetailVO;

    public ThemeInfoRich() {
    }

    public ThemeInfoRich(Long themeId, ThemeVO themeVO, Supplier<ThemeDetailVO> themeDetailVO) {
        this.themeId = themeId;
        this.themeVO = themeVO;
        this.themeDetailVO = themeDetailVO;
    }
}
