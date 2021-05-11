package GUI.Ultilities;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontCustom {
    public static final String BOLD = "Bold";
    public static final String BOLD_ITALIC = "BoldItalic";
    public static final String REGULAR = "Regular";
    public static final String ITALIC = "Italic";
    public static final String LIGHT = "Light";
    public static final String LIGHT_ITALIC = "LightItalic";
    public static final String SEMI_BOLD = "SemiBold";
    public static final String SEMI_BOLD_ITALIC = "SemiBoldItalic";
    public static final String EXTRA_BOLD = "ExtraBold";
    public static final String EXTRA_BOLD_ITALIC = "ExtraBoldItalic";

    public static Font getFont(String name,String type, float size){

        InputStream is = FontCustom.class.getResourceAsStream("../../RES/Font/" +name+"-"+type+".ttf");
        try {
            assert is != null;
            Font newFont =  Font.createFont(Font.TRUETYPE_FONT, is);
            newFont = newFont.deriveFont(size);
            return newFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String upcaseFirst(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
