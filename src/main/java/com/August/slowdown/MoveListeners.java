package com.August.slowdown;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListeners implements Listener {

    public MoveListeners() {
    }

    @EventHandler
    public void onElytraLimit(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getEnvironment() != World.Environment.NETHER || player.getLocation().getY() < 127.5 || !SlowDownPlugin.ELYTRA_JUMP) return;

        boolean nearGround = !player.getLocation().clone().subtract(0, 0.1, 0).getBlock().isPassable();
        if (!nearGround) return;

        double dx = event.getTo().getX() - event.getFrom().getX();
        double dz = event.getTo().getZ() - event.getFrom().getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        double maxDistance = 0.65;

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