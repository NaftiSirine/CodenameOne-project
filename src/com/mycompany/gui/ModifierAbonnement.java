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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Question;
import com.mycompany.services.ServiceAbonnement;
import com.mycompany.services.ServiceQuestion;




public class ModifierAbonnement extends BaseForm{
    
    Form current;
    public ModifierAbonnement(Resources res , Abonnement r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Abonnement");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField nom = new TextField(r.getNom() , "Objet" , 20 , TextField.ANY);
        TextField description = new TextField(r.getDescription() , "Objet" , 20 , TextField.ANY);
        
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
       
        
        
        
        
        
        
        
        nom.setUIID("NewsTopLine");
        
        
        nom.setSingleLineTextArea(true);
        description.setUIID("NewsTopLine");
        
        
        description.setSingleLineTextArea(true);
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           
           r.setNom(nom.getText());
           r.setDescription(description.getText());
           
           
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceAbonnement.getInstance().modifierAbonnement(r)) { // if true
           new ListAbonnement(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListAbonnement(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(nom),
                new FloatingHint(description),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
    
    
    
    
}
