package com.starnft.star.domain.rank;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class RankExecutor<T extends Comparable> implements Serializable {

    protected List<T> ranker;
    public RankExecutor(List<T> ranker) {
        this.ranker = ranker;
    }
    public List<T> sort() {
        return this.ranker.stream().sorted().collect(Collectors.toList());
    }
}
