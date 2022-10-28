package cn.MiLiBlue.anxious.modules.movement;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", 0, Category.Movement);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        try{
            if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
                mc.thePlayer.setSprinting(true);
            }
        }catch (Exception e){}
    }
}
