package me.sildev.world;

import me.sildev.data.Material;

public class BlockState {

    private final Material type;
    private final int offset;

    public BlockState(Material type) {
        this(type, 0);
    }

    public BlockState(Material type, int offset) {
        this.type = type;
        this.offset = offset;
    }

    public Material getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }
}
