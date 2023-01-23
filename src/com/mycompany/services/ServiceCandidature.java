/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycomany.entities.Candidature;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class ServiceCandidature {
    
    public static ServiceCandidature instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Candidature> questions;
    
    
    public ServiceCandidature(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceCandidature getInstance() {
        if(instance == null )
            instance = new ServiceCandidature();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutCandidature(Candidature question){
        
        String url = Statics.BASE_URL+"/postulercandidatureapi?message="+question.getMessage();                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }   
        
        
        
          
    
    
    
    
    
    
    
    //Delete 
    public boolean deleteCandidature(int id) {
        String url = Statics.BASE_URL +"/deletecandapi?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Candidature> affichageCandidatures() {
        ArrayList<Candidature> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/Affichercandimobile";
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
                        Candidature re = new Candidature();
                        
                        
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String titre = obj.get("message").toString();
                        
                        
                        re.setId((int)id);
                        re.setMessage(titre);
                        
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
    public boolean modifierCandidature(Candidature reclamation) {
        String url = Statics.BASE_URL +"/modifierjson/cand?id="+reclamation.getId()+"&message="+reclamation.getMessage();
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
    
    
    
    
    
    
    
    public ArrayList<Candidature> parseCandidature(String jsonText){
        try {
            questions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Candidature t = new Candidature();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setMessage(obj.get("contenu").toString());
                
              
                questions.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return questions;
    }
    
}
