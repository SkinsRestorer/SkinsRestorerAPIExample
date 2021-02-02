package net.skinsrestorer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// Skinsrestorer imports!
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.shared.exception.SkinRequestException;

public class SkinsRestorerAPIExample extends JavaPlugin {
    // Setting definition
    private SkinsRestorerAPI skinsRestorerAPI;

    @Override
    public void onEnable() {
        CommandSender console = getServer().getConsoleSender();
        console.sendMessage("Loading SkinsRestorer API...");

        // Connecting to Bukkit API for applying the skin
        skinsRestorerAPI = SkinsRestorerAPI.getApi();
        console.sendMessage(skinsRestorerAPI.toString());

        this.getCommand("api").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can only run this command as a player.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("/api <skin name>");
            return false;
        }

        Player player = (Player) sender;
        String skin = args[0];

        player.sendMessage("Setting your skin to " + skin);

        try {
            // setskin for player skin 
            skinsRestorerAPI.setSkin(player.getName(), skin);
            // Force skinrefresh for player
            skinsRestorerAPI.applySkin(new PlayerWrapper(player));
        } catch (SkinRequestException e) {
            e.printStackTrace();
        }

        return true;
    }
}
