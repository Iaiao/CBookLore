package mc.iaiao.cbooklore;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends JavaPlugin implements Listener {
    private String format;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        format = getConfig().getString("format");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBookSigning(PlayerEditBookEvent event) {
        if (event.isSigning()) {
            BookMeta meta = event.getNewBookMeta();
            meta.setLore(getLore(format, event.getPlayer()));
            event.setNewBookMeta(meta);
        }
    }

    public List<String> getLore(String format, OfflinePlayer player) {
        return Arrays.stream(format.split("\n"))
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .map(line -> PlaceholderAPI.setPlaceholders(player, line))
                .collect(Collectors.toList());
    }
}
