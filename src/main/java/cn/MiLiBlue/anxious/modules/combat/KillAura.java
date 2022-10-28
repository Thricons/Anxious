package cn.MiLiBlue.anxious.modules.combat;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.utils.MathUtil;
import cn.MiLiBlue.anxious.utils.RotationUtil;
import cn.MiLiBlue.anxious.utils.TimerUtil;
import cn.MiLiBlue.anxious.values.Mode;
import cn.MiLiBlue.anxious.values.Numbers;
import cn.MiLiBlue.anxious.values.Option;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class KillAura extends Module {
    private static TimerUtil timer = new TimerUtil();
    public static EntityLivingBase target;
    private List targets = new ArrayList(0);
    private int index;
    private static Numbers<Double> aps = new Numbers<Double>("APS", 40.0, 1.0, 40.0, 0.5);
    private Numbers<Double> reach = new Numbers<Double>("Reach",  6.0, 1.0, 7.0, 0.1);
    private Option blocking = new Option("Autoblock",  true);
    private Option players = new Option("Players",  true);
    private Option animals = new Option("Animals",  true);
    private Option mobs = new Option("Mobs",  true);
    private Option invis = new Option("Invisibles", false);
    private Mode mode = new Mode("Mode", new String[]{"Single", "Switch"}, "Switch");
    private boolean isBlocking;
    private Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> RotationUtil.getRotations(e2)[0]);

    public KillAura(){
        super("KillAura", 0, Category.Combat);
        addValues(mode,aps, reach, blocking, players, animals, mobs, invis);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e){
        this.target = null;
        this.setSuffix(this.mode.getValue());
        this.targets = this.loadTargets();
        this.targets.sort(this.angleComparator);
        if (this.mc.thePlayer.ticksExisted % 50 == 0 && this.targets.size() > 1) {
            ++this.index;
        }
        if (!this.targets.isEmpty()) {
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
            this.target = (EntityLivingBase) this.targets.get(this.index);
//            event.setYaw(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0]);
//            event.setPitch(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1] + new Random().nextInt(2));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0], RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1], mc.thePlayer.onGround));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0], RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1], mc.thePlayer.onGround));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0], RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1], mc.thePlayer.onGround));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0], RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1], mc.thePlayer.onGround));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[0], RotationUtil.faceTarget(this.target, 1000.0f, 1000.0f, false)[1], mc.thePlayer.onGround));
        }
        if (this.blocking.getValue().booleanValue() && this.canBlock() && (
                mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                )) {
            this.stopAutoBlock();
        }

        if (this.target != null) {
            double angle = Math.toRadians(this.target.rotationYaw - 90.0f + 360.0f) % 360.0;
            if (this.shouldAttack()) {
                this.attack();
                this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                if (!this.mc.thePlayer.isBlocking() && this.canBlock() && this.blocking.getValue().booleanValue()) {
                    this.startAutoBlock();
                }
                this.timer.reset();
            }
            if (this.canBlock() && this.blocking.getValue().booleanValue() && !this.mc.thePlayer.isBlocking()) {
                this.startAutoBlock();
            }
        }
    }

    private void attack() {
        if (this.blocking.getValue().booleanValue() && this.canBlock() && this.mc.thePlayer.isBlocking() && this.qualifies(this.target)) {
            this.stopAutoBlock();
        }
        this.mc.thePlayer.swingItem();
        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
    }

    private boolean qualifies(Entity e) {
        if (e == this.mc.thePlayer) {
            return false;
        }
//        if (ab.isServerBot(e)) {
//            return false;
//        }
//        if(AntiBot.isBot(e)){
//            return false;
//        }
        if (!e.isEntityAlive()) {
            return false;
        }
//        if (FriendManager.isFriend(e.getName())) {
//            return false;
//        }
//        if (e instanceof EntityPlayer && this.players.getValue().booleanValue() && !Teams.isOnSameTeam(e)) {
//            return true;
//        }
        if (e instanceof EntityMob && this.mobs.getValue().booleanValue()) {
            return true;
        }
        if (e instanceof EntityAnimal && this.animals.getValue().booleanValue()) {
            return true;
        }
        if (e.isInvisible() && !this.invis.getValue().booleanValue()) {
            return true;
        }
        return false;
    }

    private List<Entity> loadTargets() {
        return this.mc.theWorld.loadedEntityList.stream().filter(e -> (double)this.mc.thePlayer.getDistanceToEntity((Entity)e) <= this.reach.getValue() && this.qualifies((Entity)e)).collect(Collectors.toList());
    }

    @Override
    public void disable() {
        this.targets.clear();
        if (this.blocking.getValue().booleanValue() && this.canBlock() && this.mc.thePlayer.isBlocking()) {
            this.stopAutoBlock();
        }
        this.target = null;
    }

    public static boolean shouldAttack() {
        return timer.hasReached(1000.0 / (aps.getValue() + MathUtil.randomDouble(0.0, 5.0)));
    }

    private boolean canBlock() {
        if (this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }

    private void startAutoBlock() {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
    }

    private void stopAutoBlock() {
              KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }
    @Override
    public void enable() {
        this.target = null;
        this.index = 0;
    }
}
