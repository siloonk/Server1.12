package me.sildev.packets.clientbound.play;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class ClientJoinGamePacket extends Packet {
    public ClientJoinGamePacket() throws IOException {
        super(0x23);

        DataOutputStream out = getWrapper();
        out.writeInt(new Random().nextInt(Integer.MAX_VALUE));
        out.write(1);
        out.writeInt(0);
        out.writeByte(0);
        out.writeByte(10);
        DataTypes.writeString(out, "flat");
        out.writeBoolean(false);
    }
}
