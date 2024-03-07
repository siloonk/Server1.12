package me.sildev.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DataTypes {

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static String readString(DataInputStream in) throws IOException {
        int len = readVarInt(in);
        byte[] buffer = new byte[len];
        in.readFully(buffer);
        return new String(buffer);
    }

    public static void writeString(OutputStream out, String string) throws IOException {
        byte[] data = string.getBytes(StandardCharsets.UTF_8);
        writeVarInt(out, data.length);
        out.write(data);
    }

    public static int getVarIntLength(int value) {
        int length = 0;
        do {
            value >>>= 7;
            length++;
        } while (value != 0);
        return length;
    }

    public static int readVarInt(DataInputStream in) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = in.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public static long readVarLong(DataInputStream in) throws IOException {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = in.readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }


    public static void writeVarInt(OutputStream out, int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                out.write(value);
                return;
            }

            out.write((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }
    public static void writeVarLong(OutputStream out, long value) throws IOException {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                out.write((int) value);
                return;
            }

            out.write((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

}
