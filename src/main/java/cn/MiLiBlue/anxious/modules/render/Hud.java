package cn.MiLiBlue.anxious.modules.render;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.Client;
import cn.MiLiBlue.anxious.modules.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class Hud extends Module {
    public static int MAIN = new Color(0, 89, 255).getRGB();
    public static final int SECONDARY = new Color(23, 23, 23, 120).getRGB();

    public Hud() {
        super("HUD", 0, Category.Render);
        setState(true);
    }


    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
       // GlStateManager.pushMatrix();
        int rainbowTick = 0;
        int yStart = 6;

        ScaledResolution sr = new ScaledResolution(mc);

        List<Module> mods = Client.moduleManager.getEnabledModList();
        mods.sort((o1, o2) ->mc.fontRendererObj.getStringWidth(o2.getSuffix() == null ? o2.getName() : o2.getName() + "," + o2.getSuffix()) - mc.fontRendererObj.getStringWidth(o1.getSuffix() == null ? o1.getName() : o1.getName() + "," + o1.getSuffix()));

        for (Module module : mods) {
                MAIN = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f)).getRGB();
            mc.fontRendererObj.drawStringWithShadow("Anxious", 2, 2, MAIN);
            int startX = sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getSuffix() == null ? module.getName() : module.getName() + "," + module.getSuffix()) - 5;
            Gui.drawRect(startX, yStart - 1, sr.getScaledWidth(), yStart + 10, SECONDARY);
            //BlurUtil.blurArea(startX, 5, sr.getScaledWidth(), yStart + 4);
            Gui.drawRect(sr.getScaledWidth() - 1, yStart - 1, sr.getScaledWidth(), yStart + 10, MAIN);

            mc.fontRendererObj.drawStringWithShadow(module.getName(), startX + 3, yStart+2, MAIN);
            if (module.getSuffix() != null) {
                mc.fontRendererObj.drawStringWithShadow(module.getSuffix(), startX + 3 + mc.fontRendererObj.getStringWidth(module.getName() + ","), yStart+2, Color.WHITE.darker().getRGB());
            }
            if (++rainbowTick > 50) {
                rainbowTick = 0;
            }

            yStart += 11;
        }

   //     GlStateManager.popMatrix();

        GlStateManager.disableBlend();
        GlStateManager.color(1, 1, 1);

    }

}
