# BreechSwap (Minecraft Java 1.21.11 / Fabric)

Client-side Fabric mod. Press **L** to toggle it. When enabled, it selects the configured mace hotbar slot at the beginning of Minecraft's normal left-click attack handler. The selected mace remains selected after the attack.

## Configure the mace slot

1. Launch Minecraft once with BreechSwap installed.
2. Close Minecraft.
3. Open `.minecraft/config/breechswap.properties`.
4. Set `maceSlot=1` through `maceSlot=9` (hotbar positions are counted from the left, starting at 1).
5. Save and relaunch.

The default is slot 1. The `enabled` value is also saved when you press L.

## Install

1. Install Fabric Loader for Minecraft **1.21.11** and launch that profile once.
2. Install Fabric API for Minecraft **1.21.11**.
3. Put `breechswap-1.0.0.jar` in `.minecraft/mods`.
4. Launch the Fabric 1.21.11 profile.
5. Press **L** in-game. A small action-bar message shows ON/OFF.

## Build

Requires Java 21 and Gradle 9.2.1. Run `gradle build`; the remapped release JAR is in `build/libs/`.

A GitHub Actions workflow is included at `.github/workflows/build.yml`. Upload this project to a GitHub repository, open **Actions → Build BreechSwap → Run workflow**, then download the `breechswap-minecraft-1.21.11` artifact from the completed run. It contains the compiled JAR.

## Notes

- The mod only runs on the client; it does not need to be installed on a server.
- It does not add the Breach enchantment. Put a mace with the vanilla **Breach** enchantment in the configured slot.
- The mod switches before the game's attack routine, so the mace is the item used by that attack and remains selected afterward.
- This project targets exactly Minecraft 1.21.11 and Java 21.
