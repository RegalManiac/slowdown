package com.August.slowdown;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MoveListeners implements Listener {

    public MoveListeners() {
    }

    @EventHandler
    public void onElytraLimit(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getEnvironment() != World.Environment.NETHER || player.getLocation().getY() < 127.5 || !SlowDownPlugin.ELYTRA_JUMP) return;

        ItemStack chest = player.getInventory().getChestplate();
        if (chest == null || chest.getType() != Material.ELYTRA) return;

        boolean nearGround = !player.getLocation().clone().subtract(0, 0.1, 0).getBlock().isPassable();
        if (!nearGround) return;

        double dx = event.getTo().getX() - event.getFrom().getX();
        double dz = event.getTo().getZ() - event.getFrom().getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        double maxDistance = 0.6;

        PotionEffect speedEffect = player.getPotionEffect(PotionEffectType.SPEED);
        if (speedEffect != null) {
            int speedLevel = speedEffect.getAmplifier() + 1;
            maxDistance += (0.13 * speedLevel);
        }

        PotionEffect jumpEffect = player.getPotionEffect(PotionEffectType.JUMP_BOOST);
        if (jumpEffect != null) {
            int jumpLevel = jumpEffect.getAmplifier() + 1;
            maxDistance += (0.05 * jumpLevel);
        }

        if (distance > maxDistance) {

            double ratio = maxDistance / distance;
            double newX = event.getFrom().getX() + (dx * ratio);
            double newZ = event.getFrom().getZ() + (dz * ratio);

            Location cappedLocation = new Location(
                    event.getTo().getWorld(),
                    newX,
                    event.getTo().getY(),
                    newZ,
                    event.getTo().getYaw(),
                    event.getTo().getPitch()
            );

            event.setTo(cappedLocation);
        }
    }
}