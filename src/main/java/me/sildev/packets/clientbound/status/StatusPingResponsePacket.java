package me.sildev.packets.clientbound.status;

import me.sildev.packets.Packet;

import java.io.IOException;

public class StatusPingResponsePacket extends Packet {

    public StatusPingResponsePacket(long payload) throws IOException {
        super(0x01);
        getWrapper().writeLong(payload);
    }
}
