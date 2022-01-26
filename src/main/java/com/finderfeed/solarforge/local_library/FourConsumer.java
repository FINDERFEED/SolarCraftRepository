package com.finderfeed.solarforge.local_library;


@FunctionalInterface
public interface FourConsumer<A,B,C,D> {

    void accept(A a ,B b, C c ,D d);

}
