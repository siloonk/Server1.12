package me.sildev.packets.serverbound.status;

import me.sildev.packets.Packet;

import java.io.IOException;

public class StatusRequestPacket extends Packet {
    public StatusRequestPacket(byte[] data) throws IOException {
        super(data);
    }
}
