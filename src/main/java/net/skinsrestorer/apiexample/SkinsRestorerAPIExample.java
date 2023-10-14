package net.skinsrestorer.apiexample;

import net.skinsrestorer.api.PropertyUtils;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.VersionProvider;
import net.skinsrestorer.api.connections.MineSkinAPI;
import net.skinsrestorer.api.connections.model.MineSkinResponse;
import net.skinsrestorer.api.exception.DataRequestException;
import net.skinsrestorer.api.exception.MineSkinException;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.property.SkinProperty;
import net.skinsrestorer.api.property.SkinVariant;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class SkinsRestorerAPIExample extends JavaPlugin {
    // Skin values for the custom skin example
    public static String VALUE = "ewogICJ0aW1lc3RhbXAiIDogMTY0MTIwNjc4OTIwNywKICAicHJvZmlsZUlkIiA6ICJjZjgwY2E3NDFjNWQ0N2E3YWFjNGNmYjI2MjI0NDJmYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzb21lb25lX28iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc4NGJiOTEwMDQ4MGVlMTMyNWIyY2Q4NWJkYTkxMjI1NDcwYWMwOTRlZTExNzRiMzg4MDdmNzAwZDcyZDJkYyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    public static String SIGNATURE = "P2+tca61qcDIdKmIUgENZ0bhGzq3Y7mlGrBNpqVTMXGem8A8dBv7JaUqJqdwdFDhQOn9VExiUbPWQLbTc/OQezXxonFw2Wwq7wK1lRGPUwZIpLQxPh9JgkVPBib/vG/wgGm7qMscvkRp06vhQB1OdtFEKnPwt5T6GLfCnP5ifLPaWo9FCdr5bgO7RaozXS4hgGLjt1y87JAWZMABWuFQGPeNgnDQAlSVQTKNYosxjyl51wwDZxhHnjmW1UUqZZehQ2NlQ2G/bdp2sasf/8aWfWkLNifY01c7pNGDAtVPes5C0xAjHnCjNpiId/ylKYeb0HCM3w18N5kWPo2LULHb4R7TVgXuHBoIYHr70zx1DSutNLchh5NmTp/FhRZgkP6sucBVu6Cq1g4RP11B7vkQRZJbjAl6r0ur7pRha+ZFI6hR+k8NNqSWozree5oR7xZ7gaSKARcD9i78YNRXbDRprastLWV3iwH2SEeEV2JmgDXN+CjM6HJ0liXfz7VtRKajG8zF/9ZH3RxegbRxiqzs+CUkJnHtxKuDYjfScW6uFflvh8/Wf//xEulzxEgdAZdXzBgwPv3U8uXgfN1qHP0SAVaivZPL5g7e0hDTdrXFbUA6+n6PTssuwf52gLGdMaHJ0AOdrlgXxDSFb7LXEg+bWv8lFs34SlVFyCmZFEOLvZU=";

    // Store the SkinsRestorer API instance for later
    private SkinsRestorer skinsRestorerAPI;
    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info(ChatColor.AQUA + "Hooking into SkinsRestorer API");

        if (!VersionProvider.isCompatibleWith("15")) {
            logger.info("This plugin was made for SkinsRestorer v15, but " + VersionProvider.getVersionInfo() + " is installed. There may be errors!");
        }

        // Retrieve the SkinsRestorer API for applying the skin
        skinsRestorerAPI = SkinsRestorerProvider.get();

        logger.info(ChatColor.AQUA + "Registering command");
        PluginCommand apiCommand = Objects.requireNonNull(getCommand("api"));
        apiCommand.setExecutor(this);

        logger.info(ChatColor.AQUA + "Done! :D");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You can only run this command as a player!");
            return true;
        }

        // Help on /api without arguments
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/api skin <skin name> - set your skin from name");
            sender.sendMessage(ChatColor.RED + "/api custom - apply our custom skin from values :)");
            sender.sendMessage(ChatColor.RED + "/api genskin <url> [steve/slim] - generate skin from url using mineskin");
            sender.sendMessage(ChatColor.RED + "/api getskinurl get current skin url");
            return false;
        }

        String skin = args[0];

        sender.sendMessage("args.length" + args.length);

        // /api genskin <url>
        if (skin.equalsIgnoreCase("genskin") && args.length >= 2) {
            SkinVariant skinVariant = null;
            if (args.length >= 3 && args[2].equalsIgnoreCase("steve") || args[2].equalsIgnoreCase("slim")) {
                skinVariant = SkinVariant.valueOf(args[2].toUpperCase(Locale.ROOT));
            }

            try {
                sender.sendMessage("Generating skin for: " + args[1]);

                MineSkinAPI mineSkinAPI = skinsRestorerAPI.getMineSkinAPI();
                MineSkinResponse response = mineSkinAPI.genSkin(args[1], skinVariant);
                SkinProperty skinProperty = response.getProperty();

                sender.sendMessage("-- skin info for: " + args[1] + " --");
                sender.sendMessage("Value" + skinProperty.getValue());
                sender.sendMessage("Signature" + skinProperty.getSignature());

                sender.sendMessage("----------------");

                // this is the same what we do over at skinsRestorerAPI.getSkinTextureUrl
                String textureUrl = PropertyUtils.getSkinTextureUrl(skinProperty);

                sender.sendMessage("skintextureUrl: " + textureUrl);
            } catch (MineSkinException | DataRequestException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        if (skin.equalsIgnoreCase("getSkinUrl")) {
            // get textures.minecraft.net url for player current skin
            PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();
            try {
                Optional<SkinProperty> property = playerStorage.getSkinForPlayer(player.getUniqueId(), player.getName());

                if (property.isPresent()) {
                    String textureUrl = PropertyUtils.getSkinTextureUrl(property.get());
                    sender.sendMessage("skintextureUrl: " + textureUrl);
                } else {
                    sender.sendMessage("no skin found for player");
                }
            } catch (DataRequestException e) {
                e.printStackTrace();
            }

            return true;
        }

        player.sendMessage(ChatColor.AQUA + "Setting your skin to " + skin);

        try {
            SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();

            // /api custom
            if (skin.equalsIgnoreCase("custom")) {
                skinStorage.setCustomSkinData("custom", SkinProperty.of(VALUE, SIGNATURE));
            }

            // Find or generate skin data for skin
            // This either generates it from the URL, finds a custom skin,
            // finds the skin of a player (with that name) or returns an empty optional
            // SkinsRestorer never requests the URL directly
            // and instead tells MineSkin to generate the skin data with the URL
            Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin);

            if (result.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Skin not found!");
                return true;
            }

            PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

            // Associate the skin with the player
            playerStorage.setSkinIdOfPlayer(player.getUniqueId(), result.get().getIdentifier());

            // Instantly apply skin to the player without requiring the player to rejoin
            skinsRestorerAPI.getSkinApplier(Player.class).applySkin(player);
        } catch (DataRequestException | MineSkinException e) {
            e.printStackTrace();
        }

        return true;
    }
}
