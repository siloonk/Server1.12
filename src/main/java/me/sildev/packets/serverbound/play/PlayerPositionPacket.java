package me.sildev.packets.serverbound.play;

import me.sildev.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;

public class PlayerPositionPacket extends Packet {

    double x;
    double feetY;
    double z;
    boolean isGrounded;

    public PlayerPositionPacket(byte[] data) throws IOException {
        super(data);

        DataInputStream in = getIStream();

        this.x = in.readDouble();
        this.feetY = in.readDouble();
        this.z = in.readDouble();
        this.isGrounded = in.readBoolean();
    }

    public double getX() {
        return x;
    }

    public double getFeetY() {
        return feetY;
    }

    public double getY() {
        return feetY + 1.62;
    }

    public double getZ() {
        return z;
    }

    public boolean isGrounded() {
        return isGrounded;
    }
}
