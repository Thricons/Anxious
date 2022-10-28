package cn.MiLiBlue.anxious.modules.render;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.modules.Module;
import cn.MiLiBlue.anxious.ui.cgui.ClickUi;
import org.lwjgl.input.Keyboard;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class ClickGui extends Module {
    public ClickGui(){
        super("ClickGui", Keyboard.KEY_O, Category.Render);
    }

    public void enable(){
        mc.displayGuiScreen(new ClickUi());
        toggle();
    }
}
