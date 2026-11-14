package com.breechswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public final class BreechSwapClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.MISC;
    private static KeyMapping toggleKey;
    private static boolean enabled = true;
    private static int maceSlot = 1; // 1-based hotbar slot

    @Override
    public void onInitializeClient() {
        Config.load();
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.breechswap.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, CATEGORY));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                enabled = !enabled;
                Config.save();
                if (client.player != null) {
                    client.player.displayClientMessage(Component.literal(
                            "BreechSwap " + (enabled ? "ON" : "OFF") + " | Mace slot: " + maceSlot), true);
                }
            }
        });
    }

    public static boolean isEnabled() { return enabled; }
    public static int getMaceSlotZeroBased() { return Math.max(0, Math.min(8, maceSlot - 1)); }

    private static final class Config {
        private static final java.nio.file.Path FILE = net.fabricmc.loader.api.FabricLoader.getInstance()
                .getConfigDir().resolve("breechswap.properties");
        static void load() {
            java.util.Properties p = new java.util.Properties();
            if (java.nio.file.Files.exists(FILE)) {
                try (var in = java.nio.file.Files.newInputStream(FILE)) { p.load(in); }
                catch (Exception e) { System.err.println("[BreechSwap] Could not read config: " + e); }
            }
            try { maceSlot = Math.max(1, Math.min(9, Integer.parseInt(p.getProperty("maceSlot", "1")))); }
            catch (NumberFormatException ignored) { maceSlot = 1; }
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
        }
        static void save() {
            java.util.Properties p = new java.util.Properties();
            p.setProperty("maceSlot", Integer.toString(maceSlot));
            p.setProperty("enabled", Boolean.toString(enabled));
            try {
                java.nio.file.Files.createDirectories(FILE.getParent());
                try (var out = java.nio.file.Files.newOutputStream(FILE)) {
                    p.store(out, "BreechSwap settings. maceSlot is 1-9.");
                }
            } catch (Exception e) { System.err.println("[BreechSwap] Could not save config: " + e); }
        }
    }
}
