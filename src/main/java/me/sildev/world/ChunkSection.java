package me.sildev.world;

import me.sildev.utils.DataTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChunkSection {

    private int bitsPerBlock;
    private int[] palette;
    private int[] dataArray;
    private byte[] blockLight;
    private byte[] skyLight;

    public ChunkSection(int bitsPerBlock, int[] palette, int[] dataArray, byte[] blockLight, byte[] skyLight) {
        this.bitsPerBlock = bitsPerBlock;
        this.palette = palette;
        this.dataArray = dataArray;
        this.blockLight = blockLight;
        this.skyLight = skyLight;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        // Write bits per block
        dataStream.writeByte(bitsPerBlock);

        // Write palette
        DataTypes.writeVarInt(dataStream, palette.length);
        for (int i = 0; i < palette.length; i++) {
            DataTypes.writeVarInt(dataStream, palette[i]);
        }

        // Write data array length
        DataTypes.writeVarInt(dataStream, dataArray.length);

        // Write data array
        for (int i = 0; i < dataArray.length; i++) {
            dataStream.writeLong(dataArray[i]);
        }

        // Write block light
        dataStream.write(blockLight);

        // Write sky light (optional)
        if (skyLight != null) {
            dataStream.write(skyLight);
        }

        // Close streams
        dataStream.close();
        byteStream.close();

        // Return the byte array
        return byteStream.toByteArray();
    }
}
