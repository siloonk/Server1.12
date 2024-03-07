package me.sildev.world;

import me.sildev.ClientHandler;
import me.sildev.data.Location;
import me.sildev.world.generator.ChunkGenerator;
import me.sildev.world.generator.FlatGenerator;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class World {

    private final long created = System.currentTimeMillis();
    private final long seed = System.currentTimeMillis();
    private final Map<Location, Chunk> chunks = new ConcurrentHashMap<>();
    private final ChunkGenerator generator = new FlatGenerator(this);
    private int time = 6000;
    private Weather weather = Weather.CLEAR;
    private World() {}

    public static World createWorld() {
        return new World();
    }

    public long getAgeTicks() {
        return (System.currentTimeMillis() - created) / 50;
    }

    public int getTime() {
        return time % 24000;
    }

    public Weather getWeather() {
        return weather;
    }


    public String getParsedTime() {
        int time = ((this.time + 6000) % 24000);
        int hours = time / 1000;
        int seconds = (int) (((double) (time % 1000) / 1000) * 60);

        long hourM = TimeUnit.HOURS.toMillis(1);
        return new SimpleDateFormat("HH:ss").format(new Date(hours * hourM + seconds * 1000L - hourM));
    }

    public void batchFillBlocks(Map<Location, BlockState> blocks) {
        Map<Chunk, List<Block>> chunked = new HashMap<>();
        for (Map.Entry<Location, BlockState> entry : blocks.entrySet()) {
            Location abs = entry.getKey();
            Location loc = abs.toChunkLocation();
            Chunk chk = getChunkAt(loc.getBlockX(), loc.getBlockZ());
            if (!chunked.containsKey(chk)) chunked.put(chk, new ArrayList<>());
            BlockState state = entry.getValue();
            chunked.get(chk)
                    .add(new Block(new Location(abs.getBlockX() % 16, abs.getBlockY(), abs.getBlockZ() % 16),
                            state.getType(),
                            state.getOffset()));
        }

        for (Map.Entry<Chunk, List<Block>> entry : chunked.entrySet()) {
            entry.getKey().batchSetBlocks(entry.getValue());
        }
    }

    public Chunk getChunkAt(int x, int z) {
        Location loc = new Location(x, 0, z);
        if (!chunks.containsKey(loc)) chunks.put(loc, Chunk.generateChunk(x, z, generator));
        return chunks.get(loc);
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return "World{" + "seed=" + seed + ", chunks=" + chunks + ", generator=" + generator + '}';
    }

    public void dropViewer(ClientHandler player) {
        for (Chunk chk : player.getViewingChunks()) {
            chk.removeViewer(player);
            reviewChunk(new Location(chk.getX(), 0, chk.getZ()));
        }
        for (Chunk chk : chunks.values())
            chk.removeViewer(player);
    }

    public void reviewChunk(Location loc) {
        Chunk chk = getChunkAt(loc.getBlockX(), loc.getBlockZ());
        if (!chk.hasViewers()) dropChunk(loc);
    }

    public void dropChunk(Location loc) {
        chunks.remove(loc);
    }

    public enum Weather {
        CLEAR,
        RAIN,
        THUNDER
    }
}
