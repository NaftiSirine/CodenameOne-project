/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Question;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.services.ServiceQuestion;


import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Choix;
import com.mycomany.entities.Reclamation;
import com.mycompany.services.ServiceChoix;
import com.mycompany.services.ServiceReclamation;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Moez
 */
public class AjouterQuestion extends BaseForm {
    
    
    
    Form current;
    public AjouterQuestion(Resources res,int id_t){
        
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
        
        //
        
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
        RadioButton mesListes = RadioButton.createToggle("Return to menu", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("list", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
          new BackInterface(res).show();
            refreshTheme();
        });
        
        
        liste.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
          new ListQuestion(res).show();
            refreshTheme();
        });
        

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        
        //
        
        int fontSize = Display.getInstance().convertToPixels(3);
        
      Font tt  = Font.createTrueTypeFont("native:MainBold", "native:MainBold").derive(fontSize, Font.STYLE_PLAIN);
        
        Label p = new Label("Entrer la premiére question :");
                p.setAlignment(CENTER);
                p.setWidth(10000);
                p.getUnselectedStyle().setFont(tt);
                p.setTextPosition(Component.TOP);
                addStringValue("", p);
        
        TextField contenu_1 = new TextField("", "entrer contenu!!");
        contenu_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_1);
        
        TextField contenu_choix_1_1 = new TextField("", "entrer le choix correct");
        contenu_choix_1_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_1_1);
        
        TextField contenu_choix_1_2 = new TextField("", "entrer le choix incorrect");
        contenu_choix_1_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_1_2);
        
        TextField contenu_choix_1_3 = new TextField("", "entrer le choix incorrect");
        contenu_choix_1_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_1_3);
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        
        Label d = new Label("Entrer la deuxiéme question :");
                d.setAlignment(CENTER);
                d.setWidth(10000);
                d.getUnselectedStyle().setFont(tt);
                d.setTextPosition(Component.TOP);
                addStringValue("", d);
        
        TextField contenu_2 = new TextField("", "entrer contenu!!");
        contenu_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_2);
        
        TextField contenu_choix_2_1 = new TextField("", "entrer le choix correct");
        contenu_choix_2_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_2_1);
        
        TextField contenu_choix_2_2 = new TextField("", "entrer le choix incorrect");
        contenu_choix_2_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_2_2);
        
        TextField contenu_choix_2_3 = new TextField("", "entrer le choix incorrect");
        contenu_choix_2_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_2_3);
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        
        Label t = new Label("Entrer la troisiéme question :");
                t.setAlignment(CENTER);
                t.setWidth(10000);
                t.getUnselectedStyle().setFont(tt);
                t.setTextPosition(Component.TOP);
                addStringValue("", t);
        
        TextField contenu_3 = new TextField("", "entrer contenu!!");
        contenu_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_3);
        
        TextField contenu_choix_3_1 = new TextField("", "entrer le choix correct");
        contenu_choix_3_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_3_1);
        
        TextField contenu_choix_3_2 = new TextField("", "entrer le choix incorrect");
        contenu_choix_3_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_3_2);
        
        TextField contenu_choix_3_3 = new TextField("", "entrer le choix incorrect");
        contenu_choix_3_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_3_3);
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        
        Label q = new Label("Entrer la quatriéme question :");
                q.setAlignment(CENTER);
                q.setWidth(10000);
                q.getUnselectedStyle().setFont(tt);
                q.setTextPosition(Component.TOP);
                addStringValue("", q);
        
        TextField contenu_4 = new TextField("", "entrer contenu!!");
        contenu_4.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_4);
        
        TextField contenu_choix_4_1 = new TextField("", "entrer le choix correct");
        contenu_choix_4_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_4_1);
        
        TextField contenu_choix_4_2 = new TextField("", "entrer le choix incorrect");
        contenu_choix_4_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_4_2);
        
        TextField contenu_choix_4_3 = new TextField("", "entrer le choix incorrect");
        contenu_choix_4_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_4_3);
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        
        Label c = new Label("Entrer la cinquiéme question :");
                c.setAlignment(CENTER);
                c.setWidth(10000);
                c.getUnselectedStyle().setFont(tt);
                c.setTextPosition(Component.TOP);
                addStringValue("", c);
        
        TextField contenu_5 = new TextField("", "entrer contenu!!");
        contenu_5.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_5);
        
        TextField contenu_choix_5_1 = new TextField("", "entrer le choix correct");
        contenu_choix_5_1.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_5_1);
        
        TextField contenu_choix_5_2 = new TextField("", "entrer le choix incorrect");
        contenu_choix_5_2.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_5_2);
        
        TextField contenu_choix_5_3 = new TextField("", "entrer le choix incorrect");
        contenu_choix_5_3.setUIID("TextFieldBlack");
        addStringValue("Contenu",contenu_choix_5_3);
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        
        
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                if(contenu_1.getText().equals("") && contenu_2.getText().equals("") && contenu_3.getText().equals("") && contenu_4.getText().equals("") && contenu_5.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    
                    
                    //njibo iduser men session (current user)
                    Question q_1 = new Question(String.valueOf(contenu_1.getText()
                        ).toString());
                    //choix de cette question
                    Choix c1_1 = new Choix(String.valueOf(contenu_choix_1_1.getText()
                        ).toString(),
                        1);
                    
                    Choix c2_1 = new Choix(String.valueOf(contenu_choix_1_2.getText()
                        ).toString(),
                        1);
                    
                    Choix c3_1 = new Choix(String.valueOf(contenu_choix_1_3.getText()
                        ).toString(),
                        1);
                    
                    
                    
                    
                    Question q_2 = new Question(String.valueOf(contenu_2.getText()
                        ).toString());
                    //choix de cette question
                    Choix c1_2 = new Choix(String.valueOf(contenu_choix_2_1.getText()
                        ).toString(),
                        1);
                    
                    Choix c2_2 = new Choix(String.valueOf(contenu_choix_2_2.getText()
                        ).toString(),
                        1);
                    
                    Choix c3_2 = new Choix(String.valueOf(contenu_choix_2_3.getText()
                        ).toString(),
                        1);
                    
                    
                    
                    
                    Question q_3 = new Question(String.valueOf(contenu_3.getText()
                        ).toString());
                    //choix de cette question
                    Choix c1_3 = new Choix(String.valueOf(contenu_choix_3_1.getText()
                        ).toString(),
                        1);
                    
                    Choix c2_3 = new Choix(String.valueOf(contenu_choix_3_2.getText()
                        ).toString(),
                        1);
                    
                    Choix c3_3 = new Choix(String.valueOf(contenu_choix_3_3.getText()
                        ).toString(),
                        1);
                    
                    
                    
                    
                    Question q_4 = new Question(String.valueOf(contenu_4.getText()
                        ).toString());
                    //choix de cette question
                    Choix c1_4 = new Choix(String.valueOf(contenu_choix_4_1.getText()
                        ).toString(),
                        1);
                    
                    Choix c2_4 = new Choix(String.valueOf(contenu_choix_4_2.getText()
                        ).toString(),
                        1);
                    
                    Choix c3_4 = new Choix(String.valueOf(contenu_choix_4_3.getText()
                        ).toString(),
                        1);
                    
                    
                    
                    
                    Question q_5 = new Question(String.valueOf(contenu_5.getText()
                        ).toString());
                    //choix de cette question
                    Choix c1_5 = new Choix(String.valueOf(contenu_choix_5_1.getText()
                        ).toString(),
                        1);
                    
                    Choix c2_5 = new Choix(String.valueOf(contenu_choix_5_2.getText()
                        ).toString(),
                        1);
                    
                    Choix c3_5 = new Choix(String.valueOf(contenu_choix_5_3.getText()
                        ).toString(),
                        1);
                    
                    
                    
                    
                    
                    
                    
                    
                     System.out.println("data  Question == "+q_1);
                    
                    
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    int id_q_1 = ServiceQuestion.getInstance().ajoutQuestion(q_1,id_t);
                    //choix de cette question
                    ServiceChoix.getInstance().ajoutChoix(c1_1,id_q_1,1);
                    ServiceChoix.getInstance().ajoutChoix(c2_1,id_q_1,0);
                    ServiceChoix.getInstance().ajoutChoix(c3_1,id_q_1,0);
                    
                    
                    int id_q_2 = ServiceQuestion.getInstance().ajoutQuestion(q_2,id_t);
                    //choix de cette question
                    ServiceChoix.getInstance().ajoutChoix(c1_2,id_q_2,1);
                    ServiceChoix.getInstance().ajoutChoix(c2_2,id_q_2,0);
                    ServiceChoix.getInstance().ajoutChoix(c3_2,id_q_2,0);
                    
                    
                    int id_q_3 = ServiceQuestion.getInstance().ajoutQuestion(q_3,id_t);
                    //choix de cette question
                    ServiceChoix.getInstance().ajoutChoix(c1_3,id_q_3,1);
                    ServiceChoix.getInstance().ajoutChoix(c2_3,id_q_3,0);
                    ServiceChoix.getInstance().ajoutChoix(c3_3,id_q_3,0);
                    
                    int id_q_4 = ServiceQuestion.getInstance().ajoutQuestion(q_4,id_t);
                    //choix de cette question
                    ServiceChoix.getInstance().ajoutChoix(c1_4,id_q_4,1);
                    ServiceChoix.getInstance().ajoutChoix(c2_4,id_q_4,0);
                    ServiceChoix.getInstance().ajoutChoix(c3_4,id_q_4,0);
                    
                    int id_q_5 = ServiceQuestion.getInstance().ajoutQuestion(q_5,id_t);
                    //choix de cette question
                    ServiceChoix.getInstance().ajoutChoix(c1_5,id_q_5,1);
                    ServiceChoix.getInstance().ajoutChoix(c2_5,id_q_5,0);
                    ServiceChoix.getInstance().ajoutChoix(c3_5,id_q_5,0);
                    
                    
                    
                    //choix de cette question
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListREclamationForm
                    new ListTest(res).show();
                    
                    
                    
                    refreshTheme();//Actualisation
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
    
    
    
    
    
    
    ///////////////////////////////////////////
    
    ///////////////////////////////////////////
    
    ///////////////////////////////////////////
    
    ///////////////////////////////////////////
    
    
    
    ///////////////////////////////////////////
    
    
    
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

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
    
    
    
    
    
    
    
    
}
