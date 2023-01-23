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
import com.codename1.ui.events.ActionListener;
import com.mycomany.entities.Evenement;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;






/**
 *
 * @author Moez
 */
public class ServiceEvenement {
    
    public static ServiceEvenement instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<ServiceEvenement> evenements;
    
    
    public ServiceEvenement(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceEvenement getInstance() {
        if(instance == null )
            instance = new ServiceEvenement();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutEvenement(Evenement ev){
        
        String url = Statics.BASE_URL+"/json/front/testmaker/allquestions?titre="+ev.getTitre()+"&description="+ev.getDescription()+"&localisation="+ev.getLocalisation();                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }    
    
    
    
    //Delete 
    public boolean deleteEvenement(int id) {
        String url = Statics.BASE_URL +"/json/front/testmaker/question/deletequestion?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Evenement> affichageEvenement() {
        ArrayList<Evenement> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
        req.setUrl(url);
        
        System.out.println("json url"+url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Evenement ev = new Evenement();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String contenu = obj.get("contenu").toString();
                        String description = obj.get("description").toString();
                        String localisation = obj.get("localisation").toString();
                        float nb_participants = Float.parseFloat(obj.get("nb_participants").toString());
                        
                        ev.setId((int)id);
                        ev.setTitre(contenu);
                        ev.setDescription(contenu);
                        ev.setLocalisation(contenu);
                        
                        //insert data into ArrayList result
                        result.add(ev);
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
    public boolean modifierEvenement(Evenement ev) {
        String url = Statics.BASE_URL +"/json/front/testmaker/modifier?id="+ev.getId()+"&titre="+ev.getTitre()+"&description="+ev.getDescription()+"&localisation="+ev.getLocalisation();;
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
    
    
    
    
    
    
    
    
    
    
    
}
