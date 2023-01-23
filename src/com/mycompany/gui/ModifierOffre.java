/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Form;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Offre;
import com.mycompany.services.ServiceOffre;
import com.mycompany.services.ServiceQuestion;
import com.mycompany.services.ServiceReclamation;

/**
 *
 * @author Moez
 */
public class ModifierOffre extends BaseForm {
    
    Form current;
    public ModifierOffre(Resources res , Offre r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField titre = new TextField(r.getTitre() , "Objet" , 20 , TextField.ANY);
        TextField secteur = new TextField(r.getSecteur() , "Objet" , 20 , TextField.ANY);
        TextField description = new TextField(r.getDescription() , "Objet" , 20 , TextField.ANY);
        TextField localisation = new TextField(r.getLocalisation() , "Objet" , 20 , TextField.ANY);
                
        
        
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
       
        
        
        
        
        
        
        
        titre.setUIID("NewsTopLine");
        secteur.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        localisation.setUIID("NewsTopLine");
        
        
        
        titre.setSingleLineTextArea(true);
        secteur.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        localisation.setSingleLineTextArea(true);
        
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setTitre(titre.getText());
           r.setSecteur(secteur.getText());
           r.setDescription(description.getText());
           r.setLocalisation(localisation.getText());
           
           
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceOffre.getInstance().modifierOffre(r)) { // if true
           new listOffres(res).show();
       }
        });
       
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListQuestion(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(titre),
                new FloatingHint(secteur),
                new FloatingHint(description),
                new FloatingHint(localisation),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
    
}
