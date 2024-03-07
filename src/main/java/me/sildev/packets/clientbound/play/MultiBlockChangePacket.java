package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.DataOutputStream;
import java.io.IOException;

public class MultiBlockChangePacket extends Packet {


    public MultiBlockChangePacket(int chunkX, int chunkY, int blockID) throws IOException {
        super(0x10);
        DataOutputStream out = getWrapper();
        out.writeInt(chunkX);
        out.writeInt(chunkY);
        DataTypes.writeVarInt(out, 16*90*16);

        // Write records for each block to change
        for (int y = 0; y < 90; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    // Calculate the block's horizontal position within the chunk
                    int horizontalPosition = (x << 4) | (z & 0x0F);

                    // Write record
                    out.writeByte(horizontalPosition);
                    out.writeByte(y);
                    DataTypes.writeVarInt(out, blockID); // Block ID for stone (you may need to adjust this based on your server's block palette)
                }
            }
        }
    }
}
