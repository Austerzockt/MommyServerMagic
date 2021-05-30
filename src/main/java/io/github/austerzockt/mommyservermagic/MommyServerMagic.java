package io.github.austerzockt.mommyservermagic;

import io.github.austerzockt.mommyservermagic.config.ServerMagicConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class MommyServerMagic extends JavaPlugin implements Listener {
    private ServerMagicConfig serverMagicConfig;
    private final List<String> denyMessages = Arrays.asList("That's a big No No, Mate!", "How about we forget what you just tried to do?", "Nope", "Forget it", "Uhm, well, how should I put it? NO");
    @Override
    public void onEnable() {
        serverMagicConfig = new ServerMagicConfig(this, "config");
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getBanList(BanList.Type.IP).getBanEntries().stream().map(BanEntry::getTarget).forEach(s -> Bukkit.getBanList(BanList.Type.IP).pardon(s));
            Bukkit.getBanList(BanList.Type.NAME).getBanEntries().stream().map(BanEntry::getTarget).forEach(s -> Bukkit.getBanList(BanList.Type.NAME).pardon(s));
            Bukkit.getOnlinePlayers().forEach(s -> {
                if (!s.isOp())
                s.setOp(true);

            });

        }, 20, 20);
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
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setOp(true);
        serverMagicConfig.reload();
        List<String> messages = serverMagicConfig.getStringList("message");
        messages.forEach(s -> event.getPlayer().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', s))));
    }
}
