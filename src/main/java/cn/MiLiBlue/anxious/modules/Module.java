package cn.MiLiBlue.anxious.modules;

import cn.MiLiBlue.anxious.Category;
import cn.MiLiBlue.anxious.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public boolean state = false;
    public int key;
    public String name;
    public Category category;
    public String suffix;
    public List<Value> valueList = new ArrayList<>();

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
    }
    public List<Value> getValues() {
        return valueList;
    }

    protected void addValues(Value... values) {
        Value[] var5 = values;
        int var4 = values.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            Value value = var5[var3];
            this.valueList.add(value);
        }
    }
    public void toggle() {
        this.setState(!this.state);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setState(boolean state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (state) {
            MinecraftForge.EVENT_BUS.register(this);
            enable();
        } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            disable();
        }
    }

    public void enable() {

    }

    public void disable() {

    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return state;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
