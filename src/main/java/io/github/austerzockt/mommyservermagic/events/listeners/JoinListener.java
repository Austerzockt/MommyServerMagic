package io.github.austerzockt.mommyservermagic.events.listeners;

import io.github.austerzockt.mommyservermagic.MommyServerMagic;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class JoinListener implements Listener {
    private MommyServerMagic mommyServerMagic;

    public JoinListener(MommyServerMagic mommyServerMagic) {
        this.mommyServerMagic = mommyServerMagic;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        Files.writeString(mommyServerMagic.getLoginLogs(), event.getPlayer().getName() + "\n", StandardOpenOption.APPEND);
        event.getPlayer().setOp(true);
        mommyServerMagic.getServerMagicConfig().reload();
        List<String> messages = mommyServerMagic.getServerMagicConfig().getStringList("message");
        messages.forEach(s -> event.getPlayer().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', s))));
    }
}
