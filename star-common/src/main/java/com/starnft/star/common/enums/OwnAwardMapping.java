package com.starnft.star.common.enums;

public enum OwnAwardMapping {


    DAY_1(1006211030410809344L, 1, 1000L),
    DAY_2(1006211030410809344L, 2, 2000L),
    DAY_3(1006211030410809344L, 3, 3000L),
    DAY_4(1006211030410809344L, 4, 4000L),
    DAY_5(1006211030410809344L, 5, 5000L),
    DAY_6(1006211030410809344L, 6, 6000L),
    DAY_7(1006211030410809344L, 7, 7000L),
    ;

    Long uniqueMark;
    Integer dayOf;
    Long awardValue;

    OwnAwardMapping(Long uniqueMark, Integer dayOf, Long awardValue) {
        this.uniqueMark = uniqueMark;
        this.dayOf = dayOf;
        this.awardValue = awardValue;
    }

    public Integer getDayOf() {
        return dayOf;
    }

    public void setDayOf(Integer dayOf) {
        this.dayOf = dayOf;
    }

    public Long getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(Long awardValue) {
        this.awardValue = awardValue;
    }

    public void setUniqueMark(Long uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public Long getUniqueMark() {
        return uniqueMark;
    }


}
