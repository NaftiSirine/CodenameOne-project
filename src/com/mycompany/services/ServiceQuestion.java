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
public class ServiceQuestion {
    
    public static ServiceQuestion instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Question> questions;
    
    
    public ServiceQuestion(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceQuestion getInstance() {
        if(instance == null )
            instance = new ServiceQuestion();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public int ajoutQuestion(Question question,int id_t){
        
        String url = Statics.BASE_URL+"/json/front/testmaker/addquestions?contenu="+question.getContenu()+"&id_t="+id_t;                
        
        ArrayList<Question> result = new ArrayList<>();
       
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                String str = new String(req.getResponseData());//response json mta3 nav
                System.out.println("data urllll=="+str);
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    Question re = new Question();
                    
                    System.out.println("aftern map =="+mapReclamations);
                    float id = Float.parseFloat(mapReclamations.get("id").toString());
                    System.out.println("work blaizzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+id);
                    re.setId((int)id);
                    result.add(re);
                    
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    
        return result.get(0).getId();
        
    }    
    
    
    
    
    
    
    
    //Delete 
    public boolean deleteQuestion(int id) {
        String url = Statics.BASE_URL +"/json/front/testmaker/question/deletequestion?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Question> affichageQuestions() {
        ArrayList<Question> result = new ArrayList<>();
        
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
                        Question re = new Question();
                        
                        
                        
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
    public boolean modifierQuestion(Question reclamation) {
        String url = Statics.BASE_URL +"/json/front/testmaker/modifier?id="+reclamation.getId()+"&contenu="+reclamation.getContenu();
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
    
    
    
    
    
    
    
    public ArrayList<Question> parseQuestions(String jsonText){
        try {
            questions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Question t = new Question();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setContenu(obj.get("contenu").toString());
                
              
                questions.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return questions;
    }
    
    
    public ArrayList<Question> getQuestions(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                questions = parseQuestions(new String(req.getResponseData()));
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return questions;
    }
    
    
    
    public ArrayList<Question> getAllQuestions(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                questions = parseQuestions(new String(req.getResponseData()));
                
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return questions;
    }
    
    
    
    
    
    
}
