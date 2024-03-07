package me.sildev.packets.serverbound.play;

import me.sildev.packets.Packet;

import java.io.IOException;

public class ClientKeepAlivePacket extends Packet {

    long payload;
    public ClientKeepAlivePacket(byte[] data) throws IOException {
        super(data);

        this.payload = getIStream().readLong();
    }

    public long getPayload() {
        return payload;
    }
}
