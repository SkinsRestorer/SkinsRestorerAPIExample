package net.skinsrestorer;

import net.skinsrestorer.api.PlayerWrapper;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.exception.SkinRequestException;
import net.skinsrestorer.api.property.GenericProperty;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SkinsRestorerAPIExample extends JavaPlugin {
    // Skin values for the custom skin example
    public static String VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTY0MTIwNjc4OTIwNywKICAicHJvZmlsZUlkIiA6ICJjZjgwY2E3NDFjNWQ0N2E3YWFjNGNmYjI2MjI0NDJmYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb21lb25lX28iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc4NGJiOTEwMDQ4MGVlMTMyNWIyY2Q4NWJkYTkxMjI1NDcwYWMwOTRlZTExNzRiMzg4MDdmNzAwZDcyZDJkYyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    public static String SIGNATURE = "P2+tca61qcDIdKmIUgENZ0bhGzq3Y7mlGrBNpqVTMXGem8A8dBv7JaUqJqdwdFDhQOn9VExiUbPWQLbTc/OQezXxonFw2Wwq7wK1lRGPUwZIpLQxPh9JgkVPBib/vG/wgGm7qMscvkRp06vhQB1OdtFEKnPwt5T6GLfCnP5ifLPaWo9FCdr5bgO7RaozXS4hgGLjt1y87JAWZMABWuFQGPeNgnDQAlSVQTKNYosxjyl51wwDZxhHnjmW1UUqZZehQ2NlQ2G/bdp2sasf/8aWfWkLNifY01c7pNGDAtVPes5C0xAjHnCjNpiId/ylKYeb0HCM3w18N5kWPo2LULHb4R7TVgXuHBoIYHr70zx1DSutNLchh5NmTp/FhRZgkP6sucBVu6Cq1g4RP11B7vkQRZJbjAl6r0ur7pRha+ZFI6hR+k8NNqSWozree5oR7xZ7gaSKARcD9i78YNRXbDRprastLWV3iwH2SEeEV2JmgDXN+CjM6HJ0liXfz7VtRKajG8zF/9ZH3RxegbRxiqzs+CUkJnHtxKuDYjfScW6uFflvh8/Wf//xEulzxEgdAZdXzBgwPv3U8uXgfN1qHP0SAVaivZPL5g7e0hDTdrXFbUA6+n6PTssuwf52gLGdMaHJ0AOdrlgXxDSFb7LXEg+bWv8lFs34SlVFyCmZFEOLvZU=";

    // Store the SkinsRestorer API instance for later
    private SkinsRestorerAPI skinsRestorerAPI;

    @Override
    public void onEnable() {
        Logger log = getLogger();

        log.info(ChatColor.AQUA + "Hooking into SkinsRestorer API");
        // Retrieve the SkinsRestorer API for applying the skin
        skinsRestorerAPI = SkinsRestorerAPI.getApi();

        log.info(ChatColor.AQUA + "Registering command");
        getCommand("api").setExecutor(this);

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
            if (skin.equals("custom")) {
                skinsRestorerAPI.setSkinData("custom", new GenericProperty("textures", VALUE, SIGNATURE), null);
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
