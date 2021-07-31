package com.finderfeed.solarforge.for_future_library;


@FunctionalInterface
public interface FourConsumer<A,B,C,D> {

    void accept(A a ,B b, C c ,D d);

}
