package me.sildev.packets.serverbound.status;

import me.sildev.packets.Packet;

import java.io.IOException;

public class StatusPingRequestPacket extends Packet {

    long payload;

    public StatusPingRequestPacket(byte[] data) throws IOException {
        super(data);

        payload = getIStream().readLong();
    }

    public long getPayload() {
        return payload;
    }
}
