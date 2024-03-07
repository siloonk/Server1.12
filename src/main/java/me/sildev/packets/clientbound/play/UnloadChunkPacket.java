package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;

import java.io.IOException;

public class UnloadChunkPacket extends Packet {


    public UnloadChunkPacket(int x, int z) throws IOException {
        super(0x1D);

        getWrapper().writeInt(x);
        getWrapper().writeInt(z);
    }
}
