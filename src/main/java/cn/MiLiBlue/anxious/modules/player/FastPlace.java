package cn.MiLiBlue.anxious.modules.player;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class FastPlace extends Module {
    public FastPlace(){
        super("FastPlace", 0, Category.Player);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e){
        Class<Minecraft> minecraft = net.minecraft.client.Minecraft.class;
        try {
            Field ClickDelay = minecraft.getDeclaredField("rightClickDelayTimer");
            ClickDelay.setAccessible(true);
            ClickDelay.set(mc, 0);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            try {
                Field ClickDelay = minecraft.getDeclaredField("field_71467_ac");
                ClickDelay.setAccessible(true);
                ClickDelay.set(mc, 0);
            } catch (NoSuchFieldException exc) {
                toggle();
            } catch (IllegalAccessException exception) {
                toggle();
            }
        }
    }
}
