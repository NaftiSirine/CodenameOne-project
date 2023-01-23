/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
//import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.Log;
import static com.codename1.io.rest.Rest.patch;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;
import com.mycompany.gui.SessionManager;
import com.mycompany.gui.BaseForm;
import com.mycompany.gui.NewsfeedForm;
import java.util.Vector;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;



/**
 * The user profile form
 *
 * @author Mr-Freez
 */
public class ProfileForm extends BaseForm {
    
    public static String i;
    
    //image 
        protected String saveFileToDevice(String hi, String txt) throws IOException, URISyntaxException
        {
            URI uri;
            try
            {
                uri = new URI(hi);
                String path = uri.getPath();
                int index = hi.lastIndexOf("/");
                hi = hi.substring(index + 1);
                return hi;
            } catch (URISyntaxException ex)
            {
            }
            return "famech photo";
        }
    

    public ProfileForm(Resources res) {
         super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setTitle("Mon Profile");
        getContentPane().setScrollVisible(true);
        
        super.addSideMenu(res);
        
       /* Image img = res.getImage("back-logo.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL); */
        
        Button modifier = new Button("Modifier");
        Button image = new Button("Photo");
        Button btnAnnuler = new Button("return");
        btnAnnuler.addActionListener(back -> {
           new NewsfeedForm(res).show();
       });
        //Label pp = new Label(ServiceUtilisateur.UrlImage(SessionManager.getPhoto()),"PictureWhiteBackground");
        
        
      /*  add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3,
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
                    )
                )
        ));*/
        
        

        TextField email = new TextField(SessionManager.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);
        
        
        int id = SessionManager.getId();
       
        
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password", password);
        
        image.setUIID("update");
        modifier.setUIID("Edit");
        addStringValue("",image);
        addStringValue("",modifier);
        btnAnnuler.setUIID("return");
        addStringValue("",btnAnnuler);        
        
      /*  
        CheckBox multiSelect = new CheckBox("Multi-select");
        image.addActionListener((ActionEvent e)->{
            if (FileChooser.isAvailable())
            {
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png/plain", (ActionEvent e2)->{
                    if (e2 == null || e2.getSource() == null)
                    {
                        add("No file was selected");
                        revalidate();
                        return;
                    }
                    if (multiSelect.isSelected()){
                        String[] paths = (String[]) e2.getSource();
                        for(String path : paths){
                            System.out.println(path);
                            CN.execute(path);
                        }
                        return;
                    }
                    String file = (String) e2.getSource();
                    if (file == null){
                        add("no file was selected");
                        revalidate();
                    } else {
                        Image logo;
                        try {
                            logo = Image.createImage(file).scaledHeight(500);
                            add(logo);
                            String imageFile = FileSystemStorage.getInstance().getAppHomePath()+ "photo.png";
                            try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                                System.out.println(imageFile);
                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);
                            } catch (IOException err){  }
                        } catch (IOException ex){ }
                        String extension = null;
                        if (file.lastIndexOf(".") > 0){
                            extension = file.substring(file.lastIndexOf(".")+ 1);
                            StringBuilder hi = new StringBuilder(file);
                            if (file.startsWith("file://")){
                                hi.delete(0,7);
                            }
                            int lastIndexPeriod = hi.toString().lastIndexOf(".");
                            Log.p(hi.toString());
                            String ext = hi.toString().substring(lastIndexPeriod);
                            String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                            try {
                                String namepic = saveFileToDevice(file, ext);
                                System.out.println();
                            } catch (IOException ex){ } catch (URISyntaxException ex) {
                                
                            }
                            revalidate();
                        }
                    }
                });
            }
        }); */
        
        TextField path = new TextField("");
        
        image.addActionListener(e-> {
           i = Capture.capturePhoto(Display.getInstance().getDisplayHeight(), -1);
           if (i != null)
           {
               Image im;
               try{
                   im = Image.createImage(i);
                  // im = im.scaled(res.getImage("photo-profile.jpg").getWidth(),res.getImage("photo-profile.jpg").getHeight());
                   System.out.println(i);
                   path.setText(i);
               } catch (IOException ex)
               {
                   System.out.println("Could not load image");
               }
           }
        }); 
       
       modifier.addActionListener((edit)->{
          InfiniteProgress ip = new InfiniteProgress();
          final Dialog ipDlg = ip.showInfiniteBlocking();
          ServiceUtilisateur.EditUser( id,email.getText(), password.getText());
          SessionManager.setEmail(email.getText());
          SessionManager.setPassowrd(password.getText());
          SessionManager.setPhoto(path.getText()+".jpg");
        // Dialog.show("success","Profile modifié avec success","OK", null);
          ipDlg.dispose();
          refreshTheme();
       });
        

      
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
