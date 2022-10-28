package cn.MiLiBlue.anxious.modules;

import cn.MiLiBlue.anxious.modules.combat.AutoClicker;
import cn.MiLiBlue.anxious.modules.combat.KillAura;
import cn.MiLiBlue.anxious.modules.movement.Eagle;
import cn.MiLiBlue.anxious.modules.movement.Speed;
import cn.MiLiBlue.anxious.modules.movement.Sprint;
import cn.MiLiBlue.anxious.modules.player.FastPlace;
import cn.MiLiBlue.anxious.modules.render.ChinaHat;
import cn.MiLiBlue.anxious.modules.render.ClickGui;
import cn.MiLiBlue.anxious.modules.render.Hud;
import cn.MiLiBlue.anxious.modules.render.Projectiles;

import java.util.ArrayList;

public class ModuleManager {

    static ArrayList<Module> list = new ArrayList<Module>();

    public ArrayList<Module> getModules() {
        return list;
    }

    public ModuleManager() {

    }

    public Module getModule(String name) {
        for (Module m : list) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    public ArrayList<Module> getEnabledModList() {
        ArrayList<Module> enabledModList = new ArrayList();
        for (Module m : list) {
            if (m.state) {
                enabledModList.add(m);
            }
        }
        return enabledModList;
    }

    static {
        list.add(new AutoClicker());
        list.add(new KillAura());
        list.add(new Sprint());
        list.add(new Hud());
        list.add(new Speed());
        list.add(new FastPlace());
        list.add(new Eagle());
        list.add(new Projectiles());
        list.add(new ClickGui());
        list.add(new ChinaHat());
    }
}
