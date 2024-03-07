package me.sildev.packets.clientbound.play;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class SpawnPlayerPacket extends Packet {
    public SpawnPlayerPacket(Player player) throws IOException {
        super(0x05);

        DataOutputStream out = getWrapper();
        DataTypes.writeVarInt(out, new Random().nextInt(1000));
        out.writeLong(player.getUUID().getMostSignificantBits());
        out.writeLong(player.getUUID().getLeastSignificantBits());
        out.writeDouble(player.getLocation().getX());
        out.writeDouble(player.getLocation().getY());
        out.writeDouble(player.getLocation().getZ());
        out.writeFloat(player.getLocation().getYaw());
        out.writeFloat(player.getLocation().getPitch());
    }
}
