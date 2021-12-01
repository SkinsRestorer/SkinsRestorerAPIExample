package net.skinsrestorer;

import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import net.skinsrestorer.api.property.IProperty;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SkinsRestorerAPIExample extends JavaPlugin {
    private String name;
    private String value;
    private String signature;
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
        // /api custom
        if (skin == "custom") {
            String value = "ewogICJ0aW1lc3RhbXAiIDogMTYzODM1OTAzNzI2MSwKICAicHJvZmlsZUlkIiA6ICI3ZGEwMzQ4NDU4MTY0OGNjYjEwNTRjZjdmZDZiMjQxYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJSYWNoeGVsIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFkODNiNzY2MjNmMjVhMzljYzE5NjExMWIzZjZlOTAyZmE4NGVlODUxYWVhYWNlMTU1MzQzYjBhNjc0MTI0YjgiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ=";
            String signature = "GH2ECR/QU9vt7x6GLEQrgYsy/kVkJtVsApA04g/d3pcrlJNx9Q7oat4GNYM++cTXPQTQ7Cc2B/hHZnn4ZZfI5p2YYJ+3R1P6ci6zArVYfgRnsrwSR2JSfP3e6iXvctpkvGzIYuqJXbRR914Ie0xCxzjpwUwjINGZGjnQKzZdUD11OaheGRbp6wgf6vCIoStesf/MrXoqKbDYzsrO5jvbb09HszA3DoFXoh4KqvlwDJMur8awOi1+lqIvCGDvszsjkNgfHwQCEfUGduO66LtLK+WRluXzSBXd9hNsXwytrapcNnVZxj3DQOyZjV8LU5hP5RJg9ybO+/+UOAvat7QyhMf/wkMGipENvjVa/bW7KNRBnVnINxkTfskOz+MS8bhe9PjJmmdcmTSAqpcB/cKNlT3lgIqu3GiLOBmGxeT/h4tpncNPsH1q/OEhJE2kFo53GGR7y30Y/C8KGjGx2EdRXMI7IYXBTvxfC5daEPFoO8vlNxkP9ChmBRwfeCeZyUs9HqE6+T7JYgMt/ZAicMrEtz65ds0z4zw5xxgU6vDSGGBZ9JJqfpK384PKxKAAb8fPYTKT7MR7mBxE/e3/tcnW4yNdu7r8UQnJ6JtQQPyBVLUIHCv+liep5yfK0IrRlD0VqJ9aDfs0sltN9lhcW+Lyn+Zwaoa6YS3stERO3mobOsY=";
            skinsRestorerAPI.setSkinData("custom", new GProperty("textures", value, signature), null);
        }

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
