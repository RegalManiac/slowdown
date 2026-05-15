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
            return List.of("reload", "true", "false");
        }
        return java.util.Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("slowdown.admin")) return true;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /slowdown <reload|true|false>");
            return true;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("reload")) {
            reloadConfig();
            loadSettings();
            sender.sendMessage(ChatColor.GREEN + "[SlowDown] Config reloaded!");
            return true;
        }

        if (arg.equals("true") || arg.equals("false")) {
            ELYTRA_JUMP = Boolean.parseBoolean(arg);
            getConfig().set("jump.elytra-jump", ELYTRA_JUMP);
            saveConfig();
            sender.sendMessage(ChatColor.GREEN + "[SlowDown] updated to " + ELYTRA_JUMP);
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown argument. " + ChatColor.YELLOW + "Usage: /slowdown <reload|true|false>");
        return true;
    }
}