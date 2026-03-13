package com.August.slowdown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SlowDownPlugin extends JavaPlugin implements Listener {

    public static volatile boolean ELYTRA_JUMP;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSettings();

        getServer().getPluginManager().registerEvents(new MoveListeners(), this);
        getCommand("slowdown").setExecutor(this);
    }

    private void loadSettings() {
        ELYTRA_JUMP = getConfig().getBoolean("jump.elytra-jump", true);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("ELYTRA_JUMP", "reload");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("ELYTRA_JUMP")) {
            return List.of("true", "false");
        }
        return java.util.Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("slowdown.admin")) return true;

        if (args.length == 0) return false;

        String type = args[0].toUpperCase();

        if (type.equalsIgnoreCase("reload")) {
            reloadConfig();
            loadSettings();
            sender.sendMessage(ChatColor.GREEN + "[SlowDown] Config reloaded!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /slowdown <TYPE> <VALUE>");
            return true;
        }

        try {
            String val = args[1];
            if (type.equals("ELYTRA_JUMP")) {
                ELYTRA_JUMP = Boolean.parseBoolean(val);
                getConfig().set("jump.elytra-jump", ELYTRA_JUMP);
            } else {
                sender.sendMessage(ChatColor.RED + "Unknown type.");
                return true;
            }
            saveConfig();
            sender.sendMessage(ChatColor.GREEN + "[SlowDown] " + type + " updated to " + val);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Invalid value.");
        }
        return true;
    }
}