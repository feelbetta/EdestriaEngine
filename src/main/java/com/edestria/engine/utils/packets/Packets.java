package com.edestria.engine.utils.packets;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Packets {

    public static void send(Player player, PacketContainer... packetContainers) {
        Arrays.stream(packetContainers).forEach(packetContainer -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        });
    }
}
