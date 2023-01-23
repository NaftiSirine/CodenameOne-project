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
import com.mycomany.entities.Choix;
import com.mycomany.entities.Question;
import com.mycomany.entities.Test;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



public class ServiceTest {
    
    public static ServiceTest instance = null;
    
    private ConnectionRequest req;
    
    public ArrayList<Test> tests;
    
    
    public ServiceTest(){
        req = new ConnectionRequest();
        
        
    }
    
    public static boolean resultOk = true;

    
    public static ServiceTest getInstance() {
        if(instance == null )
            instance = new ServiceTest();
        return instance ;
    }
    
    
    
  
    
    
    //ajout 
    public int ajoutTest(Test test){
        
        String url = Statics.BASE_URL+"/front/testmaker/test/addtest/json?titre="+test.getTitre();                
        
        ArrayList<Test> result = new ArrayList<>();
       
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
              else if(json.equals("password is short")) {
                   Dialog.show("Erreur","password < 8 ?","OK",null);
            }
              else{
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    Test re = new Test();
                    
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
    public boolean deleteTest(int id) {
        String url = Statics.BASE_URL +"/front/testmaker/test/deletetest/json?id="+id;
        
        req.setUrl(url);
        
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
        
        return  resultOk;
    }
    
    
    
    
    
    //affichage
    
    public ArrayList<Test> affichageTests() {
        ArrayList<Test> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/front/testmaker/alltests/json";
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
                        Test re = new Test();
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        
                        String titre = obj.get("titre").toString();
                        
                        re.setId((int)id);
                        re.setTitre(titre);
                        
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
    
    
    
    
    
    //affichage quiz
    
    public ArrayList<Test> affichagequiz() {
        ArrayList<Test> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/front/testmaker/test/quiz/json";
        req.setUrl(url);
        
        System.out.println("json url"+url);
        
        System.out.println("azebi ?");
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                System.out.println("before try");
                
                try {
                    System.out.println("after try");
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Test re = new Test();
                        System.out.println("first map");
                        
                        
                        System.out.println("");
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        String titre = obj.get("titre").toString();
                        
                        
                        List<Map<String,Object>> questions = (List<Map<String,Object>>)obj.get("questions");

                        System.out.println("in the service shit"+questions);
                            List<Question> test_questions = new ArrayList<>();
                            
                            
                            for(Map<String, Object> obj_question : questions) {
                                Question q = new Question();
                                
                                String contenu_question = obj_question.get("contenu").toString();
                                q.setContenu(contenu_question);
                                
                                
                                
                                List<Map<String,Object>> choices = (List<Map<String,Object>>)obj_question.get("choix");
                                
                                List<Choix> question_choix = new ArrayList<Choix>();
                                for(Map<String, Object> obj_choix : choices) {
                                    Choix c = new Choix();
                                    
                                    
                                    String contenu_choix = obj_choix.get("contenu").toString();
                                    String etat_choix = obj_choix.get("etatchoix").toString();
                                    
                                    c.setContenu(contenu_choix);
                                    c.setStringetat(etat_choix);
                                    question_choix.add(c);
                                    
                                }
                                
                                q.setChoices(question_choix);
                                test_questions.add(q);
                            }
                            
                        re.setId((int)id);
                        re.setTitre(titre);
                        System.out.println("before the setting"+test_questions);
                        re.setQuestions(test_questions);
                        
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
    public boolean modifierTest(Test test) {
        String url = Statics.BASE_URL +"/front/testmaker/test/edittest/json?id="+test.getId()+"&titre="+test.getTitre();
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
    
    
    
    
    
    
    
    public ArrayList<Test> parseTests(String jsonText){
        try {
            tests = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Test t = new Test();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setTitre(obj.get("titre").toString());
                
              
                tests.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tests;
    }
    
    
    
    
    //public ArrayList<Test> getAllTests(){
        //String url = Statics.BASE_URL+"/tasks/";
        /*String url = Statics.BASE_URL+"/json/front/testmaker/test/questions";
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
    }*/
    
    
    
}
