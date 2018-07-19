package org.enginehub.util.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import org.enginehub.util.forge.dumper.LegacyDumper;

import java.io.File;

@Mod(modid = ForgeUtils.MODID, name = "Forge Utils", version = "%VERSION%", acceptableRemoteVersions = "*")
public class ForgeUtils {
    public static final String MODID = "forgeutils";
    public static ForgeUtils instance;

    public Logger modLogger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        this.modLogger = event.getModLog();
    }

    @EventHandler
    public void init(FMLPostInitializationEvent event) {
        modLogger.info("ForgeUtils loading.");

        boolean dumpAll = "true".equalsIgnoreCase(System.getProperty("enginehub.dumpall"));

        try {
            if (dumpAll || "true".equalsIgnoreCase(System.getProperty("enginehub.dumplegacy"))) {
                (new LegacyDumper(new File("legacy_phase1.json"))).run();
            }
        } catch (Exception e) {
            modLogger.error("Error running block registry dumper: " +  e);
            e.printStackTrace();
        }
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        if ("true".equalsIgnoreCase(System.getProperty("enginehub.beanshell"))) {
            modLogger.info("ForgeUtils registering BeanShellCommand!");
            event.registerServerCommand(new BeanShellCommand());
        }
    }
}
