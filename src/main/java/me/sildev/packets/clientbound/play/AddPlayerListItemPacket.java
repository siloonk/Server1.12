package me.sildev.packets.clientbound.play;

import me.sildev.data.Player;
import me.sildev.packets.Packet;
import me.sildev.utils.DataTypes;

import java.io.IOException;

public class AddPlayerListItemPacket extends Packet {

    public AddPlayerListItemPacket(Player player) throws IOException {
        super(0x2E);

        DataTypes.writeVarInt(getWrapper(), 0);
        DataTypes.writeVarInt(getWrapper(), 1);
        getWrapper().writeLong(player.getUUID().getMostSignificantBits());
        getWrapper().writeLong(player.getUUID().getLeastSignificantBits());
        DataTypes.writeString(getWrapper(), player.getUsername());

        DataTypes.writeVarInt(getWrapper(), 1);

        DataTypes.writeString(getWrapper(), "textures");
        DataTypes.writeString(getWrapper(), "ewogICJ0aW1lc3RhbXAiIDogMTU5MDAwMTUwMjIyMywKICAicHJvZmlsZUlkIiA6ICI3ZGEyYWIzYTkzY2E0OGVlODMwNDhhZmMzYjgwZTY4ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJHb2xkYXBmZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdjOGNkOGUzZGNjYmJiZjQ5YjYzZmQ5OTQ0Yjk5YmQ5NzFlZThjNjM5NmI5ODMxNjZhZjVmOWE5YjZlN2ExYiIKICAgIH0KICB9Cn0=");
        getWrapper().writeBoolean(true);
        DataTypes.writeString(getWrapper(), "JG7MIzmrfqNhM6Sf5mqDJ/QX+Whq1WAesMuZY0LbL9ggucSARZU7GYKJiWPwppRb9Ctj5Vts4dZc0TR0HkB9Endg9a0nfHoEO4D/9UeLqHAVK2is0Pogin0pvTUq8u3GYC9uuAxrYK4VswV/Vvr4wmTlVNiUEmMCO2SCPmZxTIl3UPtWRuXQrOEuWBMIyxImu4Pib/d7ugAemr5LiQqH4CBQbU027EP6n/hhr+DUCFnosA8RTqyp7TvI0Wex53LRfCBfpa1x2ArbMxf0G+GO7TyxEntOeKZVEB4ljPHSjUCniP+H5dlC+XtNYR11ANg5z7l1oWxrkQBQ38Js51Tmih3MJfJERIsieJADPhvZR2sAA5/5bUIqHc0JtgEOn1/5FSRk9mO8Lv5NuFkKlhxbxHbjPoeT3VP96GO8PJSE5/Q4lAgxsmnebwiFPE6KsoxPi6VgdvCjCoEPcTwjH+O+tllMfHwnqYfWcWkLvmrHDg4ihsvSIZwCTBYPLBRNH0XeWZ4WivlbL4Lw0lbMFUjAkZzjcJG01HywklS1N6FmJnyoquAidmp6l0nzPVZTnSAjnS3oRrlHExmXv8d3wbkm0E+giiRcKZjqlD6py83JR/GyB+NAf5ZIxHvxBW4TtG6JG8GIGQGkcWu4CtTWol52IF67XkFVxI5grGwpRDUBkAs=");


        DataTypes.writeVarInt(getWrapper(), 0);
        DataTypes.writeVarInt(getWrapper(), 1);
        DataTypes.writeVarInt(getWrapper(), 50);
        getWrapper().writeBoolean(false);

    }
}
