package dev.spring93.blueprintblockeraddon;

import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.feature.BlueprintFeature;
import com.golfing8.kore.featureobjs.blueprint.SchematicSpawner;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlueprintClickListener implements Listener {
    private BlueprintFeature blueprintFeature;
    private ConfigService config;

    public BlueprintClickListener() {
        this.blueprintFeature = FactionsKore.get().getFeature(BlueprintFeature.class);
        this.config = ConfigService.getInstance();
    }

    @EventHandler
    public void onBlueprintClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if(blueprintFeature.getPlacedSpawners().containsKey(clickedBlock.getLocation())) {
                if(!FactionsKore.getIntegration().canPlayerBuildThere(player, clickedBlock.getChunk())) {
                    SchematicSpawner schemSpawner = blueprintFeature.getPlacedSpawners().get(clickedBlock.getLocation());
                    int time = config.getTime();
                    int modifiedTime = schemSpawner.getCountdown();
                    int maxTime = schemSpawner.getMaxCountdown();
                    String blueprintName = ChatColor.translateAlternateColorCodes('&',
                            schemSpawner.getBlueprint().getDisplayName());

                    switch (config.getMode().toLowerCase()) {
                        case "increment":
                            modifiedTime = schemSpawner.getCountdown() + time;
                            break;
                        case "overwrite":
                            modifiedTime = time;
                            break;
                        default: break;
                    }

                    if(config.isPreventOverflow() && modifiedTime > maxTime)
                        modifiedTime = maxTime;

                    schemSpawner.setCountdown(modifiedTime);
                    
                    player.sendMessage(
                            config.getHitterMessage(
                                blueprintName,
                                modifiedTime));

                    schemSpawner.getPlayer().sendMessage(
                            config.getBlueprintOwnerMessage(
                                    player.getName(),
                                    blueprintName,
                                    modifiedTime));
                }
            }
        }
    }
}
