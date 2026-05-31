package dev.xiaoyu.mite_test;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MITETest implements ModInitializer {
    public static final String MOD_ID = "mite_test";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final List<String> PLAYER_IDS = List.of("Xiaoyu_2009");

    public static boolean isAllowedPlayer(String name) {
        return PLAYER_IDS.contains(name);
    }

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
    }
}