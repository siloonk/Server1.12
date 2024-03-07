package me.sildev.packets.clientbound.play;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class PlayerPositionAndLookPacket extends Packet {


    public PlayerPositionAndLookPacket(Player player) throws IOException {
        super(0x2F);
        DataOutputStream out = getWrapper();
        out.writeDouble(player.getLocation().getX());
        out.writeDouble(player.getLocation().getY());
        out.writeDouble(player.getLocation().getZ());
        out.writeFloat(0);
        out.writeFloat(0);

        int X = 0x01;
        int Y = 0x02;
        int Z = 0x04;
        int Y_ROT = 0x08;
        int X_ROT = 0x10;

        // Create a byte mask initialized to all 1's (0xFF)
        int byteMask = 0xFF;

        // Set each flag to 0 in the byte mask
        byteMask &= ~X;
        byteMask &= ~Y;
        byteMask &= ~Z;
        byteMask &= ~Y_ROT;
        byteMask &= ~X_ROT;

        out.write((byte) byteMask);
        player.setLastTeleportID(new Random().nextInt(1000));
        DataTypes.writeVarInt(out, player.getLastTeleportID());
    }
}
