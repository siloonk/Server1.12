package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;
import me.sildev.world.BlockChangeEntry;

import java.io.DataOutputStream;
import java.io.IOException;

public class MultiBlockChangePacket extends Packet {


    public MultiBlockChangePacket(int x, int y, int z, BlockChangeEntry... blocks) throws IOException {
        super(0x10);
        DataOutputStream out = getWrapper();

        out.writeInt(x);
        out.writeInt(z);
        DataTypes.writeVarInt(out, blocks.length);
        for (BlockChangeEntry entry : blocks) {
            byte relX = (byte) (entry.getX() & 0x0F); // Extract the lower 4 bits of x
            byte relZ = (byte) (entry.getZ() & 0x0F); // Extract the lower 4 bits of z

            // Encode horizontal position (X and Z) in a single byte
            byte horizontalPosition = (byte) ((relX << 4) | (relZ & 0x0F));
            out.writeByte(horizontalPosition);
            out.writeByte(y);
            DataTypes.writeVarInt(out, entry.getId());
        }

    }
}
