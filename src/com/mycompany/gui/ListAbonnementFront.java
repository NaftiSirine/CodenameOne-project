/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Question;
import com.mycompany.services.ServiceAbonnement;
import com.mycompany.services.ServiceReclamation;
import java.util.ArrayList;

/**
 *
 * @author Moez
 */
public class ListAbonnementFront extends BaseForm{
    
    
    TextField searchTF;

 ArrayList<Component> componentModels;

 
 Form current;
 
/*  public Component addGUIs() {
        Container compo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        compo.setScrollableY(true);

        ArrayList<Abonnement> listAbonnement = ServiceAbonnement.getInstance().affichageAbonnements();
        
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher un plat");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (int i = 0; i < componentModels.size(); i++) {
                    compo.removeComponent(componentModels.get(i));
                    componentModels.remove(i);
                }
            }
            for (int i = 0; i < listAbonnement.size(); i++) {
                if (listAbonnement.get(i).getNom().startsWith(searchTF.getText())) {
                    Component model = makePlatModel(listAbonnement.get(i));
                    compo.add(model);
                    componentModels.add(model);
                }
            }
            compo.revalidate();
        });
        compo.add(searchTF);

        if (listAbonnement.size() > 0) {
            for (Abonnement listPlat : listAbonnement) {
                Component model = makePlatModel(listPlat);
                compo.add(model);
                componentModels.add(model);
            }
        } else {
            compo.add(new Label("Aucune donnee"));
        }

        return compo;
    }
*/
    
    
    
    
    public ListAbonnementFront(Resources res){
        
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Abonnements", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Offres", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Tests", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("Evenements", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);
        
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
                
        
        all.addActionListener(e -> new ListAbonnement(res).show());
         featured.addActionListener(e -> new ListQuestion(res).show());
         popular.addActionListener(e -> new ListTest(res).show());
         myFavorite.addActionListener(e -> new ListEvenement(res).show());
        
        
        ArrayList<Abonnement> data = new ArrayList<>(); 
        Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
         
        // current = this; 
       
        data=ServiceAbonnement.getInstance().affichageAbonnements();
        Container y = new Container(new BoxLayout (BoxLayout.Y_AXIS)); 
        
        for(int i=0;i<data.size();i++) 
        {
        
        Abonnement a = new Abonnement(); 
        a.setNom(data.get(i).getNom());
        a.setDescription(data.get(i).getDescription()); 
        a.setCout(data.get(i).getCout());
        
        
        }
        
        
        
        
        
        
      
        //Appel affichage methode
       
        System.out.println("testtttttttttttttttttttttttt");
        
        ArrayList<Abonnement>list = ServiceAbonnement.getInstance().affichageAbonnements();
        
        System.out.println("List ttetetetst length "+list.size());
        
        int fontSize = Display.getInstance().convertToPixels(5);
        
        for(Abonnement rec : list ) {
             
            
            
             System.out.println("test  Question == "+rec.getNom());
             Font tt  = Font.createTrueTypeFont("native:MainBold", "native:MainBold").derive(fontSize, Font.STYLE_PLAIN);
                 
             String Abonnement_nom = rec.getNom();
             String Abonnement_description = rec.getDescription();
             int Abonnement_cout = rec.getCout();
             
             
             TextField firstName = new TextField(Abonnement_nom);
                
                Label left = new Label(rec.getNom());
                left.setAlignment(CENTER);
                left.setWidth(10000);
                left.getUnselectedStyle().setFont(tt);
                left.setTextPosition(Component.TOP);
                addStringValue("", left);
                
                
                
                
                TextField description = new TextField(Abonnement_description);
                
                Label left_1 = new Label(rec.getDescription());
                left_1.setAlignment(CENTER);
                left_1.setWidth(10000);
                left_1.getUnselectedStyle().setFont(tt);
                left_1.setTextPosition(Component.TOP);
                addStringValue("", left_1);
                
                
                
                
                TextField cout = new TextField(Abonnement_cout);
                
                Label left_2 = new Label("100");
                left_2.setAlignment(CENTER);
                left_2.setWidth(10000);
                left_2.getUnselectedStyle().setFont(tt);
                left_2.setTextPosition(Component.TOP);
                addStringValue("", left_2);
             
             
                
                Button btnModifier = new Button("Modifier cet abonnement");
                addStringValue("", btnModifier);
                
        
                Button btnNom = new Button(rec.getNom());
                addStringValue("", btnNom);
                
                Button btnDescription= new Button(rec.getDescription());
                addStringValue("", btnDescription);
                
               
                Button btnSupprimer = new Button("Supprimer cet abonnement");


                btnSupprimer.addActionListener((e) -> {
                    Dialog dig = new Dialog("Suppression");
                    
                    System.out.println("in supression buton");
            
                       ServiceAbonnement.getInstance().deleteAbonnement(rec.getId());
                       
                       new ListAbonnement(res).show();

                });
                
                btnModifier.addActionListener((e) -> {
                    Dialog dig = new Dialog("Modifier");
                    
                    System.out.println("in modifier buton");
            
                       new ModifierAbonnement(res,rec).show();

                });
                
                Container containerImg = new Container();
                
               
                
                
                
        }
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
       private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("back-logo.jpeg"), page1);
        
        
        
        
    }
    
    
    
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }
    
    
    
    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        
    }
    
    
    
    

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }

    private void addButton(Question rec , Resources res) {
        
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        
        
        
        
        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label objetTxt = new Label("Date : "+rec.getContenu(),"NewsTopLine2");
        
        createLineSeparator();
        
        
       
        
        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce reclamation ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(ServiceReclamation.getInstance().deleteReclamation(rec.getId())) {
                    new ListReclamationForm(res).show();
                }
           
        });
        
        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);
        
        
       
        
        
        
        
    
    
    
        
 
    }
    
    private void addButton(String title) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
      
       
       TextArea ta = new TextArea(title);
       
       
   }
    


}
