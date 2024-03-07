package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;
import me.sildev.world.Chunk;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChunkDataPacket extends Packet {


    public ChunkDataPacket(int x, int z) throws IOException {
        super(0x20);

        DataOutputStream out = getWrapper();
        out.writeInt(x);
        out.writeInt(z);
        out.writeBoolean(true);
        DataTypes.writeVarInt(out, 0);
        DataTypes.writeVarInt(out, 0);
        DataTypes.writeVarInt(out, 0);

        System.out.println(getData().length);

    }
}
