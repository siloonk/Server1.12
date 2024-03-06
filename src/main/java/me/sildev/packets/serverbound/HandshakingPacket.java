package me.sildev.packets.serverbound;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.DataInputStream;
import java.io.IOException;

public class HandshakingPacket extends Packet {

    int protocolVersion;
    String address;
    int port;
    int nextState;

    public HandshakingPacket(byte[] data) throws IOException {
        super(data);

        DataInputStream in = getIStream();

        protocolVersion = DataTypes.readVarInt(in);
        address = DataTypes.readString(in);
        port = in.readUnsignedShort();
        nextState = DataTypes.readVarInt(in);
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getNextState() {
        return nextState;
    }
}
