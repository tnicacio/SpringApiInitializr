package com.tnicacio.springapiinitializr.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class ListUtil {

    public <A, B> List<Pair<A, B>> zip(List<A> listA, List<B> listB) {
        if (listA.size() != listB.size()) {
            throw new IllegalArgumentException("Lists must have same size");
        }

        List<Pair<A, B>> pairList = new LinkedList<>();

        for (int index = 0; index < listA.size(); index++) {
            pairList.add(Pair.of(listA.get(index), listB.get(index)));
        }
        return pairList;
    }

}
