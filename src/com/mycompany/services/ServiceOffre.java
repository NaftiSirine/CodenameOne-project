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
import com.mycomany.entities.Offre;
import com.mycomany.entities.Question;
import com.mycomany.entities.TypeAbonnement;
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
public class ServiceOffre {
    
    public static ServiceOffre instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Offre> questions;
    
    
    public ServiceOffre(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceOffre getInstance() {
        if(instance == null )
            instance = new ServiceOffre();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutOffre(Offre question){
        
        String url = Statics.BASE_URL+"/newoffreapi?titre="+question.getTitre()+"&secteur="+question.getSecteur()+"&description="+question.getDescription()+"&localisation="+question.getLocalisation();                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }   
        
        
        
          
    
    
    
    
    
    
    
    //Delete 
    public boolean deleteOffre(int id) {
        String url = Statics.BASE_URL +"/deleteapi?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Offre> affichageOffres() {
        ArrayList<Offre> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/AfficherOffre";
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
                        Offre re = new Offre();
                        
                        
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String titre = obj.get("titre").toString();
                        String secteur = obj.get("secteur").toString();
                        String description = obj.get("description").toString();
                        String localisation = obj.get("localisation").toString();
                        
                        
                        re.setId((int)id);
                        re.setTitre(titre);
                        re.setSecteur(secteur);
                        re.setDescription(description);
                        re.setLocalisation(localisation);
                        
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
    public boolean modifierOffre(Offre reclamation) {
        String url = Statics.BASE_URL +"/modifierjson?id="+reclamation.getId()+"&titre="+reclamation.getTitre()+"&secteur="+reclamation.getSecteur()+"&description="+reclamation.getDescription()+"&localisation="+reclamation.getLocalisation();
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
    
    
    
    
    
    
    
    public ArrayList<Offre> parseQuestions(String jsonText){
        try {
            questions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Offre t = new Offre();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setTitre(obj.get("contenu").toString());
                
              
                questions.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return questions;
    }
    
    
    
    
    
    
    
    
    
}
