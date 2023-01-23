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
import com.mycomany.entities.Abonnement;
import com.mycomany.entities.Question;
import com.mycomany.entities.TypeAbonnement;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ServiceTypeAbo {
    public static ServiceTypeAbo instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<TypeAbonnement> Types;
    
    
    public ServiceTypeAbo(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceTypeAbo getInstance() {
        if(instance == null )
            instance = new ServiceTypeAbo();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutTypeAbo(TypeAbonnement t,int id_ab){
        
        String url = Statics.BASE_URL+"/addTypeJson?type="+t.getType()+"&id_ab="+id_ab;                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            System.out.println(url);
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }    
    
    
    
    //Delete 
    public boolean deleteTypeAbo(int id) {
        String url = Statics.BASE_URL +"/removetypeJson?id="+id;
        
        req.setUrl(url);
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<TypeAbonnement> affichageTypeAbo() {
        ArrayList<TypeAbonnement> t = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/allTypeabonnementsJson";
        req.setUrl(url);
        
        System.out.println("json url"+url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapType = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapType.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        TypeAbonnement re = new TypeAbonnement();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String contenu = obj.get("type").toString();
                        
                        re.setId((int)id);
                        re.setType(contenu);
                        
                        //insert data into ArrayList result
                        t.add(re);
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
      
      System.out.println("resultat service"+t);

        return t;
        
        
    }
    
    
    
    
    
    
    //Update 
    public boolean modifierTypeAbo(TypeAbonnement tabo) {
        String url = Statics.BASE_URL +"/typeabonnementsJson?id="+tabo.getId()+"&type="+tabo.getType();
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
    
    
    
    
    
    
    
    public ArrayList<TypeAbonnement> parseTypeAbo(String jsonText){
        try {
            Types = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                TypeAbonnement t = new TypeAbonnement();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setType(obj.get("type").toString());
                
              
                Types.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return Types;
    }
    
}
