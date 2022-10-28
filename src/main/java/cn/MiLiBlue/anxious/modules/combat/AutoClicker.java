package cn.MiLiBlue.anxious.modules.combat;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.utils.TimerUtil;
import cn.MiLiBlue.anxious.values.Numbers;
import cn.MiLiBlue.anxious.values.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.security.SecureRandom;
import java.util.Random;

public class AutoClicker extends Module {
    private final Option block = new Option("Ignore Block", true);

    private final Numbers<Integer> minCPS = new Numbers<>("MinCPS", 10, 1, 20 , 1);
    private final Numbers<Integer> maxCPS = new Numbers<>("MaxCPS", 12, 1, 20 , 1);

    private final TimerUtil timer = new TimerUtil();
    private int delay = (int) Math.floor(random(1000.0F / maxCPS.getValue(), 1000.0F / minCPS.getValue()));

    private double random(double min, double max) {
        return min + (max - min) * new SecureRandom().nextDouble();
    }

    public AutoClicker(){
        super("AutoClicker", 0, Category.Combat);
        addValues(block, minCPS, maxCPS);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && block.getValue()) return;

        if ((Mouse.isButtonDown(0)) && mc.currentScreen == null) {
            if (maxCPS.getValue() < minCPS.getValue()) {
                maxCPS.setValue(minCPS.getValue());
            }

            if (timer.hasReached(delay)) {
                KeyBinding.setKeyBindState(-100, true);
                KeyBinding.onTick(-100);
                delay = (int) Math.floor(random(1000.0F / maxCPS.getValue(), 1000.0F / minCPS.getValue()));
                KeyBinding.setKeyBindState(-100, false);
                timer.reset();
            }
        }
    }
}
