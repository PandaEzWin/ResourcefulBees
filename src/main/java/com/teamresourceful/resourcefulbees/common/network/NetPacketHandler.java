package com.teamresourceful.resourcefulbees.common.network;

import com.teamresourceful.resourcefulbees.ResourcefulBees;
import com.teamresourceful.resourcefulbees.common.lib.constants.ModConstants;
import com.teamresourceful.resourcefulbees.common.network.packets.BeeconChangeMessage;
import com.teamresourceful.resourcefulbees.common.network.packets.LockBeeMessage;
import com.teamresourceful.resourcefulbees.common.network.packets.SyncGUIMessage;
import com.teamresourceful.resourcefulbees.common.network.packets.centrifuge.CommandMessage;
import com.teamresourceful.resourcefulbees.common.network.packets.centrifuge.CommandResponseMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.List;

public class NetPacketHandler {

    private NetPacketHandler() {
        throw new IllegalStateException(ModConstants.UTILITY_CLASS);
    }

    private static int id = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ResourcefulBees.MOD_ID, "main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(++id, LockBeeMessage.class, LockBeeMessage::encode, LockBeeMessage::decode, LockBeeMessage::handle);
        INSTANCE.registerMessage(++id, SyncGUIMessage.class, SyncGUIMessage::encode, SyncGUIMessage::decode, SyncGUIMessage::handle);
        INSTANCE.registerMessage(++id, BeeconChangeMessage.class, BeeconChangeMessage::encode, BeeconChangeMessage::decode, BeeconChangeMessage::handle);
        INSTANCE.registerMessage(++id, CommandMessage.class, CommandMessage::encode, CommandMessage::decode, CommandMessage::handle);
        INSTANCE.registerMessage(++id, CommandResponseMessage.class, CommandResponseMessage::encode, CommandResponseMessage::decode, CommandResponseMessage::handle);
    }

    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }

    public static void sendToAllLoaded(Object message, Level world, BlockPos pos) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), message);
    }

    public static void sendToPlayers(Object message, List<ServerPlayer> players) {
        for (ServerPlayer player : players) { sendToPlayer(message, player); }
    }

    public static void sendToPlayer(Object message, ServerPlayer playerEntity) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerEntity), message);
    }

    public static void sendToPlayersInRange(Object message, Level world, BlockPos pos, double range) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), range, world.dimension())), message);
    }
}
