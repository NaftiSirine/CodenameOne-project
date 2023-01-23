/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

/**
 *
 * @author Moez
 */




import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Question;
import com.mycomany.entities.TypeAbonnement;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





public class ServiceAbonnement {
    
    public static ServiceAbonnement instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Abonnement> abonnements;
    
    
    public ServiceAbonnement(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceAbonnement getInstance() {
        if(instance == null )
            instance = new ServiceAbonnement();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public int ajoutAbonnement(Abonnement abonnement){
        String url = Statics.BASE_URL+"/addJson?nom="+abonnement.getNom()+"&description="+abonnement.getDescription()+"&cout="+abonnement.getCout();
        
        ArrayList<Abonnement> result = new ArrayList<>();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                String str = new String(req.getResponseData());//response json mta3 nav
                System.out.println("data urllll=="+str);
                
                String json = new String(req.getResponseData()) + "";
                
                 if(json.equals("email invalid")) {
                   Dialog.show("Erreur","email invalide","OK",null);
            }
              else if(json.equals("Nom is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }
              else{
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    Abonnement re = new Abonnement();
                    
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
    public boolean deleteAbonnement(int id) {
        String url = Statics.BASE_URL +"/removeJson?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Abonnement> affichageAbonnements() {
        ArrayList<Abonnement> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/allabonnementsJson";
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
                        Abonnement re = new Abonnement();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String nom = obj.get("nom").toString();
                        String description = obj.get("description").toString();
                        
                        System.out.println("the cout before json"+obj.get("cout").toString());
                        
                        float cout = Float.parseFloat(obj.get("cout").toString());
                        
                        System.out.println("the cout after json"+obj.get("cout").toString());
                        System.out.println("the cout after partse"+cout);
                        
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescription(description);
                        re.setCout((int)cout);
                        
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
    public boolean modifierAbonnement(Abonnement abo) {
        String url = Statics.BASE_URL +"/abonnementsJson?id="+abo.getId()+"&nom="+abo.getNom()+"&description="+abo.getDescription();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
    
    
    
    
    
    
    public ArrayList<Abonnement> parseAbonnements(String jsonText){
        try {
            abonnements = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Abonnement a = new Abonnement();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int)id);
                float cout = Float.parseFloat(obj.get("cout").toString());
                a.setId((int)cout);
                a.setNom(obj.get("nom").toString());
                a.setDescription(obj.get("description").toString());
                
              
                abonnements.add(a);
            }
            
            
        } catch (IOException ex) {
            
        }
        return abonnements;
    }
    
    
    
    
    
    
    
    
    
    
    
}
