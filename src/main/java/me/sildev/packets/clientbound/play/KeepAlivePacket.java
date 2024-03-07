package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;

import java.io.IOException;
import java.util.Random;

public class KeepAlivePacket extends Packet {
    public KeepAlivePacket() throws IOException {
        super(0x1F);
        getWrapper().writeLong(new Random().nextLong(Long.MAX_VALUE));
    }
}
