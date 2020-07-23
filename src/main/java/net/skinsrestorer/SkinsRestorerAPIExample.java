package net.skinsrestorer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

//Skinsrestorer imports!
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;
import skinsrestorer.shared.exception.SkinRequestException;

/**
 * Created by McLive on 30.08.2019.
 */
public class SkinsRestorerAPIExample extends JavaPlugin {
    private CommandSender console;
    // Setting definition
    private SkinsRestorer skinsRestorer;
    private SkinsRestorerBukkitAPI skinsRestorerBukkitAPI;


    public void onEnable() {
        console = getServer().getConsoleSender();
        console.sendMessage("Loading SkinsRestorer API...");
        
        //Connecting to the main SkinsRestorer API
        skinsRestorer = JavaPlugin.getPlugin(SkinsRestorer.class);
        console.sendMessage(skinsRestorer.toString());

        // Connecting to Bukkit API for applying the skin
        skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
        console.sendMessage(skinsRestorerBukkitAPI.toString());

        this.getCommand("api").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can only run this command as a player.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("/api <skin name>");
            return true;
        }

        Player player = (Player) sender;
        String skin = args[0];

        player.sendMessage("Setting your skin to " + skin);

        
        try {
            // setskin for player skin 
            skinsRestorerBukkitAPI.setSkin(player.getName(), skin);
            // Force skinrefresh for player
            skinsRestorerBukkitAPI.applySkin(player);
        } catch (SkinRequestException e) {
            e.printStackTrace();
        }

        return true;
    }
}
