package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class BlockChangePacket extends Packet {
    public BlockChangePacket(int x, int y, int z, int blockID) throws IOException {
        super(0x0B);
        System.out.println("X: %d, Y: %d, Z: %d, Position: %d".formatted(x, y, z, encodePosition(x, y, z)));
        getWrapper().writeLong(encodePosition(x, y, z));
        DataTypes.writeVarInt(getWrapper(), blockID);
    }

    private long encodePosition(int x, int y, int z) {
        long position = ((long) (0 & 0x3FFFFFF) << 38) | ((long) (20 & 0xFFF) << 26) | (0 & 0x3FFFFFF);
        return position;
    }
}
