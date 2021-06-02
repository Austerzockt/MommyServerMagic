package io.github.austerzockt.mommyservermagic;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class BroadcastHandler {
    private MommyServerMagic mommyServerMagic;
    public BroadcastHandler(MommyServerMagic mommyServerMagic) {
        this.mommyServerMagic = mommyServerMagic;
        var sec = mommyServerMagic.getServerMagicConfig().getConfigurationSection("messages");
        Bukkit.getLogger().severe(sec.getKeys(false).stream().reduce("", String::concat));
        for (String s : sec.getKeys(false)) {
        var message = sec.getConfigurationSection(s);
            Bukkit.getScheduler().runTaskTimer(mommyServerMagic, () -> Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message.getString("message"))), message.getInt("time")* 20L, message.getInt("time")*20L);
        }
    }
}
