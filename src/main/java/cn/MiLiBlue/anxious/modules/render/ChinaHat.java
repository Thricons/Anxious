package cn.MiLiBlue.anxious.modules.render;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.utils.ReflectionUtil;
import cn.MiLiBlue.anxious.values.Numbers;
import cn.MiLiBlue.anxious.values.Option;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class ChinaHat extends Module {
    public Option renderInFirstPerson = new Option("ShowInFirstPerson", false);
    public Numbers side = new Numbers("Side", 45.0f, 30.0f, 50.0f, 1.0f);
    public Numbers stack = new Numbers("Stacks", 50.0f, 45.0f, 200.0f, 5.0f);

    public ChinaHat(){
        super("ChinaHat", 0, Category.Render);
        addValues(renderInFirstPerson, side, stack);
    }

    @SubscribeEvent
    public void onRenderW0rld(RenderHandEvent evt) {
        if(mc.gameSettings.thirdPersonView == 0 && !renderInFirstPerson.getValue()) {
            return;
        }

        this.drawChinaHat(mc.thePlayer, evt);
    }

    private void drawChinaHat(EntityLivingBase entity, RenderHandEvent evt) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)evt.partialTicks - (Double)ReflectionUtil.getFieldValue(mc.getRenderManager(), "renderPosX", "field_78725_b");
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)evt.partialTicks - (Double)ReflectionUtil.getFieldValue(mc.getRenderManager(), "renderPosY", "field_78726_c");
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)evt.partialTicks - (Double)ReflectionUtil.getFieldValue(mc.getRenderManager(), "renderPosZ", "field_78723_d");
        int side = (int) this.side.intValue();
        int stack = (int) this.stack.intValue();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + (mc.thePlayer.isSneaking() ? 2.0 : 2.2), z);

        GL11.glRotatef(-entity.width, 0.0f, 1.0f, 0.0f);

        Color col = new Color(-1);
        GL11.glColor4f(col.getRed() / 255f, col.getGreen() / 255f, col.getBlue() / 255f, 0.4f);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(1.0f);

        Cylinder c = new Cylinder();
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.0f, 0.8f, 0.4f, side, stack);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();
    }
}
