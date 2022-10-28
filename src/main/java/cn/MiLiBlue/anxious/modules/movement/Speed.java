package cn.MiLiBlue.anxious.modules.movement;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.Client;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.utils.MoveUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class Speed extends Module {
    private double speed;
    private int stage;

    private double lastDist;
    public Speed(){
        super("Speed", 0, Category.Movement);
    }

    @Override
    public void disable() {
        Client.getTimer().timerSpeed = 1.0f;
    }

    @Override
    public void enable() {
        lastDist = 0;
        speed = MoveUtil.getSpeed();
        stage = 2;
    }

    @SubscribeEvent
    public void onEvent(TickEvent.PlayerTickEvent e){
        setSuffix("Hypixel");
        if(e.player.onGround){
            mc.thePlayer.jump();
        }
        MoveUtil.strafe(getBaseSpeed());
    }

    private double getBaseSpeed() {
        final EntityPlayerSP player = mc.thePlayer;
        double base = 0.2895;
        final PotionEffect moveSpeed = player.getActivePotionEffect(Potion.moveSpeed);
        final PotionEffect moveSlowness = player.getActivePotionEffect(Potion.moveSlowdown);
        if (moveSpeed != null)
            base *= 1.0 + 0.19 * (moveSpeed.getAmplifier() + 1);

        if (moveSlowness != null)
            base *= 1.0 - 0.13 * (moveSlowness.getAmplifier() + 1);

        if (player.isInWater()) {
            base *= 0.5203619984250619;
            final int depthStriderLevel = EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);

            if (depthStriderLevel > 0) {
                double[] DEPTH_STRIDER_VALUES = new double[]{1.0, 1.4304347400741908, 1.7347825295420374, 1.9217391028296074};
                base *= DEPTH_STRIDER_VALUES[depthStriderLevel];
            }

        } else if (player.isInLava()) {
            base *= 0.5203619984250619;
        }
        return base;
    }

    public static boolean isOnIce() {
        Block blockUnder = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(Minecraft.getMinecraft().thePlayer.posX, StrictMath.floor(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY) - 1.0, Minecraft.getMinecraft().thePlayer.posZ)).getBlock();
        return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
    }
}
