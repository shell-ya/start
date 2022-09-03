package com.starnft.star.common.enums;

public enum OwnAwardMapping {


    DAY_1(1006211030410809344L, 1, 500L),
    DAY_2(1006211030410809344L, 2, 700L),
    DAY_3(1006211030410809344L, 3, 900L),
    DAY_4(1006211030410809344L, 4, 1100L),
    DAY_5(1006211030410809344L, 5, 1300L),
    DAY_6(1006211030410809344L, 6, 1500L),
    DAY_7(1006211030410809344L, 7, 2000L),
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
