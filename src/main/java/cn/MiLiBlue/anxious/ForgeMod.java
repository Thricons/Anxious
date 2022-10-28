package cn.MiLiBlue.anxious;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="fuck", name="Reborn", version="1.1.0", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {

    @Mod.EventHandler
    public void Mod(FMLPreInitializationEvent event) {
        new Client();
    }
}
