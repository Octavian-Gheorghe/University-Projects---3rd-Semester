package model.ADT;

import java.util.List;

public class Tuple {
    public Integer first;
    public List<Integer> second;
    public Integer third;

    public Tuple(Integer first, List<Integer> second, Integer third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Integer getFirst() {
        return first;
    }

    public List<Integer> getSecond() {
        return second;
    }

    public Integer getThird() {
        return third;
    }
}
