package io.github.austerzockt.mommyservermagic;

import io.github.austerzockt.mommyservermagic.config.ServerMagicConfig;
import io.github.austerzockt.mommyservermagic.events.listeners.JoinListener;
import io.github.austerzockt.mommyservermagic.runnables.KeepTheServerSaveRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class MommyServerMagic extends JavaPlugin implements Listener {
    private final List<String> denyMessages = Arrays.asList("That's a big No No, Mate!", "How about we forget what you just tried to do?", "Nope", "Forget it", "Uhm, well, how should I put it? NO");
    private ServerMagicConfig serverMagicConfig;
    private Path loginLogs;
    private BroadcastHandler broadcastHandler;

    @Override
    public void onEnable() {
        serverMagicConfig = new ServerMagicConfig(this, "config");
        loginLogs = Paths.get(serverMagicConfig.getString("logFile"));
        if (Files.notExists(loginLogs)) {
            try {
                Files.createFile(loginLogs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getScheduler().runTaskTimer(this, new KeepTheServerSaveRunnable(this), 20, 40);
        broadcastHandler = new BroadcastHandler(this);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        List<String> commands = serverMagicConfig.getStringList("commands");
        commands.forEach(s -> {
            if (event.getMessage().startsWith("/minecraft:" + s) || event.getMessage().startsWith("/" + s)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text(denyMessages.get(new Random().nextInt(denyMessages.size()))).color(TextColor.fromHexString("#ff0000"))); //That is red btw
            }


        });
    }

    public Path getLoginLogs() {
        return loginLogs;
    }

    public ServerMagicConfig getServerMagicConfig() {
        return serverMagicConfig;
    }
}
