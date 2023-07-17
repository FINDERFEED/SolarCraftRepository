package com.finderfeed.solarcraft.local_library.helpers;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NumberRangeObjectGetter<T> {

    private List<T> values;
    private List<NumberRange> ranges;

    public NumberRangeObjectGetter(List<T> objects,List<NumberRange> ranges){
        if (objects.size() != ranges.size()){
            throw new IllegalStateException("Objects and number ranges sizes don't match!");
        }
        List<NumberRange> cranges = new ArrayList<>(ranges);
        if (cranges.size() != 1) {
            cranges.sort(Comparator.comparingDouble(NumberRange::getBegin));
            double max = cranges.get(0).getEnd();
            for (int i = 1; i < cranges.size();i++){
                double begin = cranges.get(i).getEnd();
                double end = cranges.get(i).getEnd();
                if (begin <= max){
                    throw new IllegalStateException("Number ranges shouldn't collide!");
                }
                max = end;
            }
        }
        this.values = Collections.unmodifiableList(objects);
        this.ranges = Collections.unmodifiableList(cranges);
    }

    public T getObject(double value){
        for (int i = 0; i < ranges.size();i++){
            NumberRange range = ranges.get(i);
            if (range.isInRangeEndExclusive(value)){
                return values.get(i);
            }
        }
        return null;
    }

    public Pair<T,T> getObjectAndNext(double value){
        for (int i = 0; i < ranges.size();i++){
            NumberRange range = ranges.get(i);
            if (range.isInRangeEndExclusive(value)){
                if (i != ranges.size() - 1){
                    return new Pair<>(values.get(i),values.get(i+1));
                }else{
                    return new Pair<>(values.get(i),null);
                }
            }
        }
        return null;
    }


    public List<NumberRange> getRanges() {
        return ranges;
    }

    public List<T> getValues() {
        return values;
    }
}
