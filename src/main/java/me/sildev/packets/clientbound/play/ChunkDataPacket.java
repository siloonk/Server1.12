package me.sildev.packets.clientbound.play;

import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ChunkDataPacket extends Packet {

    public ChunkDataPacket(int chunkX, int chunkY) throws IOException {
        super(0x20);

        int[] blockStateIDs = new int[16 * 16 * (16 - 0 + 1)]; // 16x16x(yMax - yMin + 1)
        for (int i = 0; i < blockStateIDs.length; i++) {
            blockStateIDs[i] = 1; // Block state ID for stone block
        }
        byte[] chunkData = generateChunkData(blockStateIDs);

        DataOutputStream out = getWrapper();
        out.writeInt(chunkX);
        out.writeInt(chunkY);
        out.writeBoolean(true);
        DataTypes.writeVarInt(out, calculatePrimaryBitMask(chunkData, 8, false));
        DataTypes.writeVarInt(out, chunkData.length);
        out.write(chunkData);

        DataTypes.writeVarInt(out, 0);

    }

    public static byte[] generateChunkData(int[] blockStateIDs) throws IOException {
        // Ensure the blockStateIDs array is not empty
        if (blockStateIDs == null || blockStateIDs.length == 0) {
            throw new IllegalArgumentException("Block state IDs array is empty");
        }

        // Calculate the number of chunk sections needed
        int numSections = (int) Math.ceil(blockStateIDs.length / (16 * 16 * 16.0)); // 16x16x16 blocks per section

        // Create a ByteArrayOutputStream to store the chunk data
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // Write the number of chunk sections
        DataTypes.writeVarInt(byteStream, numSections);
        System.out.println(numSections + " Sections!");

        // Write chunk sections
        for (int i = 0; i < numSections; i++) {
            int startIndex = i * 16 * 16 * 16;
            int endIndex = Math.min(startIndex + 16 * 16 * 16, blockStateIDs.length);
            int sectionSize = endIndex - startIndex;

            // Write chunk section
            writeChunkSection(byteStream, blockStateIDs, startIndex, sectionSize);
        }

        // Return generated chunk data
        return byteStream.toByteArray();
    }


    private static void writeChunkSection(ByteArrayOutputStream byteStream, int[] blockStateIDs, int startIndex, int sectionSize) throws IOException {
        // Write bits per block (hardcoded to 8 for simplicity)
        byteStream.write(8); // Bits per block

        // Write palette size (always 0 for simplicity)
        DataTypes.writeVarInt(byteStream, 0); // Palette size

        // Write data array length
        DataTypes.writeVarInt(byteStream, sectionSize);

        // Write data array (block state IDs)
        for (int i = startIndex; i < startIndex + sectionSize; i++) {
            DataTypes.writeVarInt(byteStream, blockStateIDs[i]);
        }

        // Write block light array (all zeros for simplicity)
        for (int i = 0; i < 16 * 16 * 16; i++) {
            byteStream.write(0); // Block light
        }

        // Write sky light array (all zeros for simplicity)
        for (int i = 0; i < 16 * 16 * 16; i++) {
            byteStream.write(0); // Sky light
        }
    }

    private static int calculatePrimaryBitMask(byte[] chunkData, int bitsPerBlock, boolean includeSkyLight) {
        // Calculate the number of sections in the chunk data
        int numSections = calculateNumberOfSections(chunkData, bitsPerBlock, includeSkyLight);

        // Calculate the primary bit mask based on the number of sections
        int primaryBitMask = (1 << numSections) - 1;
        System.out.println(primaryBitMask + " BitMask!");

        return primaryBitMask;
    }

    private static int calculateNumberOfSections(byte[] chunkData, int bitsPerBlock, boolean includeSkyLight) {
        // Determine the size of each section's data
        int sectionSize = calculateSectionSize(bitsPerBlock, includeSkyLight);

        // Calculate the total size of the chunk data
        int totalSize = chunkData.length;

        // Calculate the number of sections based on the total size and the size of each section
        int numSections = totalSize / sectionSize;

        return numSections;
    }

    private static int calculateSectionSize(int bitsPerBlock, boolean includeSkyLight) {
        // Calculate the size of block state IDs component
        int blockStateIdSize = bitsPerBlock * 16 * 16 * 16 / 8; // In bytes

        // Calculate the size of block light component
        int blockLightSize = 16 * 16 * 16; // In bytes

        // Calculate the size of sky light component if included
        int skyLightSize = includeSkyLight ? (16 * 16 * 16) : 0; // In bytes

        // Total size of the section's data
        int totalSize = blockStateIdSize + blockLightSize + skyLightSize;

        return totalSize;
    }

}
