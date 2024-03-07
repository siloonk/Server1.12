package me.sildev.packets.clientbound.status;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class StatusResponsePacket extends Packet {


    public StatusResponsePacket(String json) throws IOException {
        super(0x00);
        DataTypes.writeString(getWrapper(), json);
    }
}
