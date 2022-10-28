package cn.MiLiBlue.anxious;

import cn.MiLiBlue.anxious.modules.ModuleManager;
import cn.MiLiBlue.anxious.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Client {
    public static Client instance;
    public static boolean state = false;

    public static ModuleManager moduleManager = new ModuleManager();

    public static Timer getTimer() {
        final Minecraft mc = Minecraft.getMinecraft();
        try {
            final Class<Minecraft> c = Minecraft.class;
            final Field f = c.getDeclaredField(new String(new char[] { 't', 'i', 'm', 'e', 'r' }));
            f.setAccessible(true);
            return (Timer)f.get(mc);
        }
        catch (Exception er) {
            try {
                final Class<Minecraft> c2 = Minecraft.class;
                final Field f2 = c2.getDeclaredField(new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T' }));
                f2.setAccessible(true);
                return (Timer)f2.get(mc);
            }
            catch (Exception er2) {
                return null;
            }
        }
    }

    public Client() {
        if (state) return;
        state = true;
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        instance = this;
    }
    @SubscribeEvent
    public void keyInput(TickEvent.PlayerTickEvent event) {
        for (Module m : moduleManager.getModules()) {
            if(Keyboard.isKeyDown(m.key)){
                m.toggle();
            }
        }
    }
}
