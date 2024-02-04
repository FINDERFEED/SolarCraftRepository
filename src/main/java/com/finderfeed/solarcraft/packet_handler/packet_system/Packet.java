package com.finderfeed.solarcraft.packet_handler.packet_system;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Packet {

    String value();

    PacketType type() default PacketType.PLAY;

}
