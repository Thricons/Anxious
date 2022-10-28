package cn.MiLiBlue.anxious.ui.cgui;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.values.Value;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class Button
{
    public Module cheat;
    public Window parent;
    public int x;
    public int y;
    public int enable;
    public int arrow;
    public int index;
    public int remander;
    private int valueY;
    public double opacity;
    public ArrayList<ValueButton> buttons;
    public boolean expand;
    int staticColor;
    public Category category;
    
    public Button(final Category category, final Module cheat, final int x, final int y) {
        this.opacity = 0.0;
        this.buttons =  Lists.newArrayList();
        this.category = category;
        this.cheat = cheat;
        this.x = x;
        this.y = y;
        int y2 = y + 15;
        this.valueY = 0;
        for (final Value v : cheat.getValues()) {
            this.buttons.add(new ValueButton(category, v, x + 5, y2));
            y2 += 20;
        }
        this.buttons.add(new KeyBindButton(category, cheat, x + 5, y2));
    }
    
    public static void doGlScissor(final int x, final int y, final int width, final int height) {
        final Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
    }
    
    public void render(final int mouseX, final int mouseY, final Limitation limitation) {
        if (this.index != 0) {
            final Button b2 = this.parent.buttons.get(this.index - 1);
            this.y = b2.y + 15 + (b2.expand ? (15 * b2.buttons.size()) : 0);
        }
        for (int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).y = this.y + 14 + 15 * i;
            this.buttons.get(i).x = this.x + 5;
        }
            this.staticColor = new Color(38, 154, 255).getRGB();
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        doGlScissor(this.x - 5, this.y - 5, 90, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 5);
        limitation.cut();
        Gui.drawRect(this.x - 5, this.y - 5, this.x + 85, this.y + 5 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, new Color(39, 39, 39).getRGB());
        if (this.cheat.state) {
            limitation.cut();
            Gui.drawRect(this.x - 4, this.y - 5, this.x + 84, this.y + 10, this.staticColor);
        }
        if (this.cheat.state) {
            if (this.enable < 180) {
                this.enable += 10;
            }
            limitation.cut();
            Minecraft.getMinecraft().fontRendererObj.drawString(this.cheat.getName().toLowerCase(), this.x + 81 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.cheat.getName().toLowerCase()), this.y, new Color(225, 225, 225).getRGB());
        }
        else {
            if (this.enable > 0) {
                this.enable -= 10;
            }
            limitation.cut();
            Minecraft.getMinecraft().fontRendererObj.drawString(this.cheat.getName().toLowerCase(), this.x + 81 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.cheat.getName().toLowerCase()), this.y, new Color(225, 225, 225).getRGB());
        }
        if (this.cheat.getValues().size() > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(this.x + 78), (float)(this.y + 2), 0.0f);
            GlStateManager.rotate((float)this.arrow, 0.0f, 0.0f, 1.0f);
            if (this.expand && this.arrow < 180) {
                this.arrow += 10;
            }
            else if (!this.expand && this.arrow > 0) {
                this.arrow -= 10;
            }
            GlStateManager.translate((float)(-(this.x + 78)), (float)(-(this.y + 2)), 0.0f);
            GlStateManager.popMatrix();
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        if (this.expand) {
            this.buttons.forEach(component -> component.render(mouseX, mouseY, limitation));
        }
    }
    
    public void key(final char typedChar, final int keyCode) {
        this.buttons.forEach(b -> b.key(typedChar, keyCode));
    }
    
    private boolean isHovering(final int n, final int n2) {
        final boolean b = n >= this.x && n <= this.x - 7 && n2 >= this.y && n2 <= this.y + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        return b;
    }
    
    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) {
            if (button == 0) {
                this.cheat.toggle();
            }
            if (button == 1 && !this.buttons.isEmpty()) {
                this.expand = !this.expand;
                boolean bl = this.expand;
            }
            if (button == 2) {
              //  this.cheat.setRemoved(!this.cheat.wasRemoved());
                this.cheat.toggle();
            }
        }
        if (this.expand) {
            this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
        }
    }
    
    public void setParent(final Window parent) {
        this.parent = parent;
        for (int i = 0; i < this.parent.buttons.size(); ++i) {
            if (this.parent.buttons.get(i) == this) {
                this.index = i;
                this.remander = this.parent.buttons.size() - i;
                break;
            }
        }
    }
}
