package me.sildev.packets;

import me.sildev.utils.DataTypes;

import java.io.*;

public class Packet {


    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final DataOutputStream wrapper = new DataOutputStream(buffer);


    /**
     * A constructor for incoming packets
     * @param data
     * @throws IOException
     */
    protected Packet(byte[] data) throws IOException {
        wrapper.write(data);
        wrapper.flush();
    }

    /**
     * A constructor for outgoing packets
     * @param id
     */
    protected Packet(int id) throws IOException {
        DataTypes.writeVarInt(wrapper, id);
    }

    protected DataInputStream getIStream() {
        return new DataInputStream(new ByteArrayInputStream(buffer.toByteArray()));
    }

    protected  DataOutputStream getWrapper() {
        return wrapper;
    }

    public byte[] getData() throws IOException {
        byte[] raw = buffer.toByteArray();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataTypes.writeVarInt(buffer, raw.length);
        buffer.write(raw);
        return buffer.toByteArray();
    }
}
