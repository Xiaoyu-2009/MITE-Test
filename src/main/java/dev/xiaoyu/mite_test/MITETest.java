package dev.xiaoyu.mite_test;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MITETest implements ModInitializer {
    public static final String MOD_ID = "mite_test";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final String PLAYER_ID = "Xiaoyu_2009";

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
    }
}