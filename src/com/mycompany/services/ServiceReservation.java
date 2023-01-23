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
import com.mycomany.entities.Reservation;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



public class ServiceReservation {
    
    
    public static ServiceQuestion instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Reservation> questions;
    
    
    public ServiceReservation(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceQuestion getInstance() {
        if(instance == null )
            instance = new ServiceQuestion();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public void ajoutResrvation(Reservation reservation,int eve_id){
        
       String url = Statics.BASE_URL+"/postulercandidatureapi?message="+reservation.getNbplace();                
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//response json mta3 nav
            System.out.println("data =="+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    
    }   
    
    
    
    
    
    
    
    //Delete 
    public boolean deleteQuestion(int id) {
        String url = Statics.BASE_URL +"/json/front/testmaker/question/deletequestion?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Reservation> affichageReservations() {
        ArrayList<Reservation> result = new ArrayList<>();
        
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
                        Reservation re = new Reservation();
                        
                        
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        float nb_place = Float.parseFloat(obj.get("nbplace").toString());
                        
                        
                        String contenu = obj.get("contenu").toString();
                        
                        re.setId((int)id);
                        re.setNbplace((int)nb_place);
                        
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
    public boolean modifierReservation(Reservation reclamation) {
        String url = Statics.BASE_URL +"/json/front/testmaker/modifier?id="+reclamation.getId()+"&contenu="+reclamation.getNbplace();
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
    
    
    
    
    
    
    
    public ArrayList<Reservation> parseReservations(String jsonText){
        try {
            questions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reservation t = new Reservation();
                float id = Float.parseFloat(obj.get("id").toString());
                float nb_place = Float.parseFloat(obj.get("nbplace").toString());
                t.setId((int)id);
                t.setNbplace((int)nb_place);
                
              
                questions.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return questions;
    }
    
    
   
    
    
    
    
    
    
    
}
