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
import com.mycomany.entities.Choix;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class ServiceChoix {
    
    
    public static ServiceChoix instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Choix> choix;
    
    
    public ServiceChoix(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    
    
    public static ServiceChoix getInstance() {
        if(instance == null )
            instance = new ServiceChoix();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutChoix(Choix choix,int q,int etat_choix){
        
        String url = Statics.BASE_URL+"/json/front/testmaker/addchoice?contenu="+choix.getContenu()+"&etat_choix="+etat_choix+"&id_q="+q;                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }    
    
    
    
    
    
    class Box {
        String value;
    }
    
    
    
    
    
    
    //Delete 
    public boolean deleteChoix(int id) {
        String url = Statics.BASE_URL +"/json/front/testmaker/question/deletechoix?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    
    //affichage
    
    public ArrayList<Choix> affichageChoix() {
        ArrayList<Choix> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/json/front/testmaker/test/choix";
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
                        Choix re = new Choix();
                        
                        
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String contenu = obj.get("contenu").toString();
                        
                        re.setId((int)id);
                        re.setContenu(contenu);
                        
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
    public boolean modifierChoix(Choix reclamation) {
        String url = Statics.BASE_URL +"/json/front/testmaker/modifier/choix?id="+reclamation.getId()+"&contenu="+reclamation.getContenu();
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
    
    
    
    
    
    
    
    public ArrayList<Choix> parseChoix(String jsonText){
        try {
            choix = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Choix t = new Choix();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setContenu(obj.get("contenu").toString());
                
              
                choix.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return choix;
    }
    
    
    public ArrayList<Choix> getChoix(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                choix = parseChoix(new String(req.getResponseData()));
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return choix;
    }
    
    
    
    public ArrayList<Choix> getAllChoix(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                choix = parseChoix(new String(req.getResponseData()));
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return choix;
    }
    
    
    
    
}
