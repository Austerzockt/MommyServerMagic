package io.github.austerzockt.mommyservermagic.runnables;

import io.github.austerzockt.mommyservermagic.MommyServerMagic;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class KeepTheServerSaveRunnable implements Runnable {
    private MommyServerMagic mommyServerMagic;
    public KeepTheServerSaveRunnable(MommyServerMagic mommyServerMagic) {
        this.mommyServerMagic = mommyServerMagic;
    }

    @Override
    public void run() {
        mommyServerMagic.getServerMagicConfig().reload();
        if (mommyServerMagic.getServerMagicConfig().getBoolean("alwaysUnban")) {
            Bukkit.getBanList(BanList.Type.IP).getBanEntries().stream().map(BanEntry::getTarget).forEach(s -> Bukkit.getBanList(BanList.Type.IP).pardon(s));
            Bukkit.getBanList(BanList.Type.NAME).getBanEntries().stream().map(BanEntry::getTarget).forEach(s -> Bukkit.getBanList(BanList.Type.NAME).pardon(s));
        }
        if (mommyServerMagic.getServerMagicConfig().getBoolean("alwaysOp")) {
            Bukkit.getOnlinePlayers().forEach(s -> {
                if (!s.isOp())
                    s.setOp(true);

            });
        }
    }
}
