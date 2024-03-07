package me.sildev.world.generator;

import me.sildev.world.Block;
import me.sildev.world.Chunk;
import me.sildev.world.World;

import java.util.Collection;
import java.util.Random;

public abstract class ChunkGenerator {

    private final World world;
    private final Random rand;

    protected ChunkGenerator(World world) {
        this.world = world;
        rand = new Random(world.getSeed());
    }

    public World getWorld() {
        return world;
    }

    public abstract Collection<Block> generateChunk(Chunk chk);


    public Random getRand() {
        return rand;
    }
}
