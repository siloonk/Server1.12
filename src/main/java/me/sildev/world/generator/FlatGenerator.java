package me.sildev.world.generator;

import me.sildev.data.Location;
import me.sildev.data.Material;
import me.sildev.world.Block;
import me.sildev.world.Chunk;
import me.sildev.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FlatGenerator extends ChunkGenerator {

    public FlatGenerator(World world) {super(world);}

    @Override
    public Collection<Block> generateChunk(Chunk chk) {
        List<Block> blocks = new ArrayList<>();
        for (int x = 0; x < 16; x++)
            for (int y = 0; y < 4; y++)
                for (int z = 0; z < 16; z++) {
                    Material type = y == 0 ? Material.BEDROCK : y == 3 ? Material.GRASS_BLOCK : Material.DIRT;
                    blocks.add(new Block(new Location(x, y, z), type, type == Material.GRASS_BLOCK ? 1 : 0));
                }
        return blocks;
    }
}
