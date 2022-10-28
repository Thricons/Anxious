package cn.MiLiBlue.anxious.utils;

import java.awt.*;

/**
 * Code by MiLiBlue, At 2022/10/22
 **/
public class ColorUtil {
    public static int getRed(int finalColor) {
        return new Color(finalColor).getRed() / 255;
    }

    public static int getGreen(int finalColor) {
        return new Color(finalColor).getGreen()/255;
    }

    public static int getBlue(int finalColor) {
        return new Color(finalColor).getBlue()/255;
    }

    public static int getAlpha(int finalColor) {
        return new Color(finalColor).getAlpha()/255;
    }
    public static int getRGB(int color){return new Color(color).getRGB() /255;}
}
