package net.skinsrestorer;

import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class SkinsRestorerAPIExample extends JavaPlugin {
    // Setting definition
    private SkinsRestorerAPI skinsRestorerAPI;

    @Override
    public void onEnable() {
        Logger log = getLogger();

        log.info(ChatColor.AQUA + "Hooking into SkinsRestorer API");
        // Connecting to SkinsRestorer API for applying the skin
        skinsRestorerAPI = SkinsRestorerAPI.getApi();

        log.info(ChatColor.AQUA + "Registering command");
        this.getCommand("api").setExecutor(this);

        log.info(ChatColor.AQUA + "Done! :D");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can only run this command as a player!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/api <skin name>");
            return false;
        }

        Player player = (Player) sender;
        String skin = args[0];

        player.sendMessage(ChatColor.AQUA + "Setting your skin to " + skin);

        try {
            // #setSkin() for player skin
            skinsRestorerAPI.setSkin(player.getName(), skin);

            // Force skin refresh for player
            skinsRestorerAPI.applySkin(new PlayerWrapper(player));
        } catch (SkinRequestException e) {
            e.printStackTrace();
        }

        return true;
    }
}
