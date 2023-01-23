/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Utilisateur;
import com.mycomany.utils.Statics;
import com.mycompany.gui.AjoutReclamationForm;
import com.mycompany.gui.ListReclamationForm;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.gui.SessionManager;
import com.mycompany.gui.SignInForm;
import static com.mycompany.services.ServiceAbonnement.resultOk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class ServiceUtilisateur {
    
    
  //singleton 
    public static ServiceUtilisateur instance = null ;
       public ArrayList<Utilisateur> utilisateurs;
    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceUtilisateur getInstance() {
        if(instance == null )
            instance = new ServiceUtilisateur();
        return instance ;
    }
    
    
    
    public ServiceUtilisateur() {
        req = new ConnectionRequest();
        
    }
    
    
    //Signup
    public void signup(TextField username,TextField password,TextField email,TextField confirmPassword, ComboBox<String> roles , Resources res ) {
        
     
        
        String url = Statics.BASE_URL+"/user/signup?username="+username.getText().toString()+"&email="+email.getText().toString()+
                "&password="+password.getText().toString()+"&roles="+roles.getSelectedItem().toString();
        
        req.setUrl(url);
       
        //Control saisi
        if(username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ")) {
            
            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);
            
        }
        
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
            
        
    }
    
    
    //SignIn
    
    public void signin(TextField email,TextField password, Resources rs ) {
        
        
        String url = Statics.BASE_URL+"/api/login?email="+email.getText().toString()+"&password="+password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            try {
            
            if(json.equals("failed")) {
                Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK",null);
            }
            else {
                System.out.println("data =="+json);
                
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                float id = Float.parseFloat(user.get("id").toString());
                
                SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
                
                SessionManager.setPassowrd(user.get("password").toString());
           
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setRoles(user.get("roles").toString());
                SessionManager.setEtat(user.get("etat").toString());
                System.out.println(SessionManager.getRoles());
                   System.out.println(SessionManager.getEtat());
                //photo 
     
                if(user.get("etat").equals("disabled") ){
                     Dialog.show("Echec d'authentification","Vous avez été bloqué ","OK",null);
                 new SignInForm(rs).show();
                }
                  //  SessionManager.setPhoto(user.get("photo").toString());
                
                else {
                      if(user.size() >0 ) 
                              if( SessionManager.getRoles().equals("[ROLE_ADMIN, ROLE_USER]") ){
                new NewsfeedForm(rs).show();
            }
                              else{
                                  
                              }  
                }
           
                    //yemchi lel list reclamation
                  // new ProfileForm(rs).show();
                    
                    }
       
            
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
       
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    

  //heki 5dmtha taw nhabtha ala description
    public String getPasswordByEmail(String email, Resources rs ) {
        
        
        String url = Statics.BASE_URL+"/json/forgetpass?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
             json = new String(req.getResponseData()) + "";
            
            
            try {
            
          
                System.out.println("data =="+json);
                
                Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                
            
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    return json;
    }
public void register(TextField email ,TextField password ,TextField image , Resources res ) {
      String url = Statics.BASE_URL +"/api/register?email="+email.getText().toString()+""+"&password="
              +password.getText().toString()+"&image="+image.getText().toString() ;
              
      req.setUrl(url);
      // set post false naamlha bech nkoul li l methode li nabeeth biha mech post 
      req.setPost(false);
      if(email.getText().equals(" ") && password.getText().equals(" ")  && image.getText().equals(" ")  ){
          Dialog.show("errreur", " veuillez remplir le formulaire" , "ok" , null);
      }
         
 
      /// hedhi naamlah abech njib code status li f symfo 
      //req.getResponseCode()
       req.addResponseListener(e-> { 
          byte[]data =(byte[])e.getMetaData();
          String responseData = new String(data);
                      String json = new String(req.getResponseData()) + "";
               
       
            if(json.equals("email invalid")) {
                   Dialog.show("Erreur","email invalide","OK",null);
            }
              else if(json.equals("password is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }
       });
      
        NetworkManager.getInstance().addToQueueAndWait(req);
    } 
// editer 
 public static void EditUser( int id ,String email, String password)
    {
        int idd =SessionManager.getId();
      String url = Statics.BASE_URL+"/json/modifierprofil?id="+idd+"&email="+email+"&password="+password;
        MultipartRequest req = new MultipartRequest();
        
       
            req.setUrl(url);
            req.setPost(true);
            req.addArgument("id", String.valueOf(SessionManager.getId()));
            req.addArgument("email", email);
            req.addArgument("password", password);
           // req.addArgument("image", image);

            System.out.println(email);
            req.addResponseListener((response)-> {
               byte[] data = (byte[]) response.getMetaData();
               String s = new String(data);
               System.out.println(s);
               
               
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
               
              if(json.equals("modified")) {
                   Dialog.show("success","Profile modifié avec success","OK", null);
            }
            else if(json.equals("email invalid")) {
                   Dialog.show("Erreur","email invalide","OK",null);
            }
              else if(json.equals("password is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }
             
              /* if (s.equals("profile modified"))
               {
                   Dialog.show("success","Profile modifié avec success","OK", null);
               }
               else 
               {
                   Dialog.show("Erreur","Echec de modification","OK",null);
               }*/
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            
    }
    
    
        //ajout 
    public int ajoutUser(Utilisateur utilisateur){
        String url = Statics.BASE_URL+"/json/adduser?email="+utilisateur.getEmail()+"&password="+utilisateur.getMotdepasse();
        
        ArrayList<Utilisateur> result = new ArrayList<>();
       
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                            
            String json = new String(req.getResponseData()) + "";
               
         if(json.equals("email invalid")) {
                   Dialog.show("Erreur","email invalide","OK",null);
            }
              else if(json.equals("password is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }else{
                   Dialog.show("Success","account is saved","OK",null);
              
             
                String str = new String(req.getResponseData());//response json mta3 nav
                System.out.println("data urllll=="+str);
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    Utilisateur re = new Utilisateur();
                    
                    System.out.println("aftern map =="+mapReclamations);
                    float id = Float.parseFloat(mapReclamations.get("id").toString());
                    System.out.println("work blaizzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+id);
                    re.setId((int)id);
                    result.add(re);
                    
  
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }}
        });
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    
        return result.get(0).getId();
    
    }    
    
    
    
    //Delete 
    public boolean deleteUser(int id) {
        String url = Statics.BASE_URL +"/json/bloquer/"+id;
        
        req.setUrl(url);
        
     
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
           Dialog.show("success","User bloqued","OK", null);
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Utilisateur> affichageUsers() {
        ArrayList<Utilisateur> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/json/allusers";
        req.setUrl(url);
        
        System.out.println("json url"+url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapAbonnements = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapAbonnements.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Utilisateur re = new Utilisateur();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String email = obj.get("email").toString();
                        String password = obj.get("password").toString();
                        String etat = obj.get("etat").toString();
                        
                       // System.out.println("the cout before json"+obj.get("cout").toString());
                        
                       // float cout = Float.parseFloat(obj.get("cout").toString());
                        
                     //   System.out.println("the cout after json"+obj.get("cout").toString());
                     //   System.out.println("the cout after partse"+cout);
                        
                        re.setId((int)id);
                        re.setEmail(email);
                        re.setMotdepasse(password);
                        re.setEtat(etat);
                        
                        //insert data into ArrayList result
                        result.add(re);
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
      
      System.out.println("resultat service"+result);

        return result;
        
        
    }
    
    
    
    
    
    
    //Update 
    public boolean modifierUser(Utilisateur abo) {
        String url = Statics.BASE_URL +"/json/editeruser/"+abo.getId()+"?email="+abo.getEmail()+"&password="+abo.getMotdepasse();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
                        
            String json = new String(req.getResponseData()) + "";
               
              if(json.equals("modified")) {
                   Dialog.show("success","Profile modifié avec success","OK", null);
            }
            else if(json.equals("email invalid")) {
                   Dialog.show("Erreur","email invalide","OK",null);
            }
              else if(json.equals("password is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
    
    
    
    
    
    
    public ArrayList<Utilisateur> parseAbonnements(String jsonText){
        try {
            utilisateurs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Utilisateur a = new Utilisateur();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int)id);
           
                a.setEmail(obj.get("nom").toString());
                a.setEtat(obj.get("etat").toString());
                
              
                utilisateurs.add(a);
            }
            
            
        } catch (IOException ex) {
            
        }
        return utilisateurs;
    }
    
    
}
