package me.sildev.world;

import me.sildev.ClientHandler;
import me.sildev.data.Location;
import me.sildev.packets.Packet;
import me.sildev.packets.clientbound.play.MultiBlockChangePacket;
import me.sildev.world.Block;
import me.sildev.world.generator.ChunkGenerator;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk {

    private int x;
    private int z;
    private final Map<Location, Block> blocks = new ConcurrentHashMap<>();
    private final List<ClientHandler> viewers = new ArrayList<>();

    private Chunk(int x, int z) {
        this.x =  x;
        this.z = z;
    }

    public static Chunk generateChunk(int x, int z, ChunkGenerator generator) {
        Chunk chk = new Chunk(x, z);
        for (Block block : generator.generateChunk(chk)) {
            chk.blocks.put(block.getBlockLocation(), block);
        }
        return chk;
    }

    public static Map<Integer, List<BlockChangeEntry>> convertToProtocol(Collection<Block> blocks) {
        Map<Integer, List<BlockChangeEntry>> entries = new HashMap<>();
        for (Block block : blocks) {
            Location loc = block.getBlockLocation();
            int y = Math.floorDiv(loc.getBlockY(), 16);
            if (!entries.containsKey(y)) entries.put(y, new ArrayList<>());
            BlockChangeEntry entry = new BlockChangeEntry(loc.getBlockX(),
                    loc.getBlockY() % 16,
                    loc.getBlockZ(),
                    block.getState());
            entries.get(y).add(entry);
        }
        return entries;
    }

    public void addViewer(ClientHandler player) {
        if (!viewers.contains(player)) viewers.add(player);
    }

    public void removeViewer(ClientHandler player) {
        viewers.remove(player);
    }

    public List<ClientHandler> getViewers() {
        return new ArrayList<>(viewers);
    }

    public boolean hasViewers() {
        return !viewers.isEmpty();
    }

    public void broadcastPacketToViewers(Packet packet) throws IOException {
        for (ClientHandler viewer : viewers)
            viewer.sendPacket(packet);
    }

    public void batchSetBlocks(Collection<Block> blocks) {
        for (Block block : blocks)
            this.blocks.put(block.getBlockLocation(), block);
        for (Map.Entry<Integer, List<BlockChangeEntry>> entry : convertToProtocol(blocks).entrySet()) {
            try {
                broadcastPacketToViewers(new MultiBlockChangePacket(getX(),
                        entry.getKey(),
                        getZ(),
                        entry.getValue()
                                .toArray(new BlockChangeEntry[0])));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Chunk{" + "blocks=" + blocks + ", x=" + x + ", z=" + z + '}';
    }

    public Collection<Block> getBlocks() {
        return blocks.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return x == chunk.x && z == chunk.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }


}
