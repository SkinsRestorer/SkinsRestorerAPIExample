package net.skinsrestorer;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;

/**
 * Created by McLive on 30.08.2019.
 */
public class SkinsRestorerAPIExample extends JavaPlugin {
    private CommandSender console;
    private SkinsRestorer skinsRestorer;

    public void onEnable() {
        console = getServer().getConsoleSender();

        console.sendMessage("Loading SkinsRestorer API...");
        skinsRestorer = JavaPlugin.getPlugin(SkinsRestorer.class);

        console.sendMessage(skinsRestorer.toString());
    }
}
