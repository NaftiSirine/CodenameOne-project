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
import com.mycomany.entities.Sujet;
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
public class ServiceSujet {
    
    public static ServiceSujet instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Sujet> sujets;
    
    
    public ServiceSujet(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceSujet getInstance() {
        if(instance == null )
            instance = new ServiceSujet();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutSujet(Sujet sujet,int t){
        
        String url = Statics.BASE_URL+"/front/testmaker/test/addsujet/json?sujet="+sujet.getSujet()+"&id_t="+t;                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    }    
    
    
    
    //Delete 
    public boolean deleteSujet(int id) {
        String url = Statics.BASE_URL +"/front/testmaker/test/deletesujet/json?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Sujet> affichageSujets() {
        ArrayList<Sujet> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/front/testmaker/allsujets/json";
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
                       Sujet re = new Sujet();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String sujet = obj.get("Sujet").toString();
                        
                        re.setId((int)id);
                        re.setSujet(sujet);
                        
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
    public boolean modifierSujet(Sujet reclamation) {
        String url = Statics.BASE_URL +"/front/testmaker/test/editsujet/json?id="+reclamation.getId()+"&sujet="+reclamation.getSujet();
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
    
    
    
    
    
    
    
    public ArrayList<Sujet> parseSujets(String jsonText){
        try {
            sujets = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
               Sujet t = new Sujet();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setSujet(obj.get("sujet").toString());
                
              
                sujets.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return sujets;
    }
    
    
    
    
    
}

   
    


