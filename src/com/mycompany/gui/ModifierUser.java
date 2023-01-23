/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

/**
 *
 * @author Moez
 */




import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Question;
import com.mycomany.entities.Utilisateur;
import com.mycompany.services.ServiceAbonnement;
import com.mycompany.services.ServiceQuestion;
import com.mycompany.services.ServiceUtilisateur;




public class ModifierUser extends BaseForm{
    
    Form current;
    public ModifierUser(Resources res , Utilisateur r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout User");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField email = new TextField(r.getEmail() , "Objet" , 20 , TextField.ANY);
        TextField password = new TextField(r.getMotdepasse(), "Objet" , 20 , TextField.ANY);
        
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
       
        
        
        
        
        
        
        
        email.setUIID("NewsTopLine");
        
        
        email.setSingleLineTextArea(true);
        password.setUIID("NewsTopLine");
        
        
        password.setSingleLineTextArea(true);
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           
           r.setEmail(email.getText());
           r.setMotdepasse(password.getText());
           
           
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceUtilisateur.getInstance().modifierUser(r)) { // if true
               InfiniteProgress ip = new InfiniteProgress();
          final Dialog ipDlg = ip.showInfiniteBlocking();
              ipDlg.dispose();
           new ListUser(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListUser(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(email),
                new FloatingHint(password),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
    
    
    
    
}

