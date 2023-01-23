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



import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
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
import com.mycompany.services.ServiceQuestion;
import com.mycompany.services.ServiceReclamation;
import java.util.ArrayList;


import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;






public class ListAbonnement extends BaseForm{
    
    
    TextField searchTF;

 ArrayList<Component> componentModels;

 
 Form current;
 
 public Component addGUIs() {
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
 
 Label libelleLabel, nomPLabel, nombreLabel, prixLabel, reductionLabel, dateLabel, marqueLabel, categorieLabel;
    ImageViewer imageIV;
    Container btnsContainer;
 
 private Component makePlatModel(Abonnement produit) {
        Container produitModel = makeModelWithoutButtons(produit);

        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(produit));

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);


        produitModel.add(btnsContainer);
        return produitModel;
    }
 
 
 private Container makeModelWithoutButtons(Abonnement produit) {
        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");

        nomPLabel = new Label(produit.getNom());
        nomPLabel.setUIID("labelCenter");

        
        imageIV.setFocusable(false);

        libelleLabel = new Label("Libelle : " + produit.getNom());
        libelleLabel.setUIID("labelDefault");

        nombreLabel = new Label("Nombre : " + produit.getDescription());
        nombreLabel.setUIID("labelDefault");

        

        produitModel.addAll(
                nomPLabel,
                imageIV, libelleLabel,
                nombreLabel, prixLabel, reductionLabel,
                dateLabel, marqueLabel, categorieLabel
        );

        return produitModel;
    }

    
    
    
    
    public ListAbonnement(Resources res){
        
        super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        tb.addSearchCommand(e ->  {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("back-logo.jpeg"),"","",res);
        
        // Welcome current user
        
        
        
        
        
        
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
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
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
        
        int fontSize = Display.getInstance().convertToPixels(3);
        Font tt  = Font.createTrueTypeFont("native:MainBold", "native:MainBold").derive(fontSize, Font.STYLE_PLAIN);
        
        
        for(Abonnement rec : list ) {
             
            
            
             System.out.println("test  Question == "+rec.getNom());
             
                 
                
                
                
        
                Label left = new Label("nom abonnement :"+rec.getNom());
                left.setAlignment(CENTER);
                left.setWidth(10000);
                left.getUnselectedStyle().setFont(tt);
                left.setTextPosition(Component.TOP);
                addStringValue("", left);
                
                Label dec = new Label("decription abonnnement :"+rec.getDescription());
                dec.setAlignment(CENTER);
                dec.setWidth(10000);
                dec.getUnselectedStyle().setFont(tt);
                dec.setTextPosition(Component.TOP);
                addStringValue("", dec);
                        
                
                Button btnModifier = new Button("Modifier cet abonnement");
                addStringValue("", btnModifier);
        
                
               
                Button btnSupprimer = new Button("Supprimer cet abonnement");
                addStringValue("", btnSupprimer);
                
                






                
                
                
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
    
    
    
    private void share(Abonnement produit) {
        Form form = new Form();
        form.add(new Label("Produit " + produit.getNom()));
        form.add(makeModelWithoutButtons(produit));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                com.codename1.ui.Display.getInstance().getDisplayWidth(),
                com.codename1.ui.Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager le produit", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(produit.toString());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        
        screenShotForm.show();
        // FIN API PARTAGE
    }
    
    
    
    
    
}
