package com.finderfeed.solarforge.local_library;



@FunctionalInterface
public interface FiveConsumer<A,B,C,D,F> {

    void accept(A a ,B b, C c ,D d,F f);

}
