/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author Moez
 */
public class Affichnote extends BaseForm{

    public Affichnote(int note) {
        
        String string_note = String.valueOf(note);
        
        int fontSize = Display.getInstance().convertToPixels(5);
        Font tt  = Font.createTrueTypeFont("native:MainBold", "native:MainBold").derive(fontSize, Font.STYLE_PLAIN);
        
        
        Label test = new Label(string_note);
                test.setAlignment(CENTER);
                test.setWidth(10000);
                test.getUnselectedStyle().setFont(tt);
                test.setTextPosition(Component.TOP);
                addStringValue("", test);
        
    }
    
    
    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        
    }
    
    
    
    
}
