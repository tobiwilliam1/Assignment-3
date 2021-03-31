/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis.workings;

import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;


public class RedisWorkings {

    static HashMap<Double, String> redisData = new HashMap<>();
   
    public static void main(String[] args) {
       
    Jedis jedis = new Jedis("localhost");
        Info labour_Force = new Info();
       
        
         if (jedis.zcard("statistics") == null || jedis.zcard("statistics") == 0) {
            jedis.zadd("statistics", (Map) Info.map);
        }
        
         jedis.zrangeByScoreWithScores("statistics", 0, 100).stream().map((t) -> {
             System.out.println(t.getScore());
            return t;
        }).forEach((t) -> {
            redisData.put(t.getScore(),t.getElement());
        });
         
        ArrayList<String> states = new ArrayList<>();
        redisData.entrySet().stream().forEach((m) -> {
            states.add((String)m.getValue());
        });
         
         String[] statesArray = new String[states.size()];
         states.toArray(statesArray);

        

        JComboBox<String> stateList = new JComboBox<>(statesArray);
        stateList.addItemListener(new Handler());
       // stateList.addItemListener(null);

// add to the parent container (e.g. a JFrame):
        JFrame jframe = new JFrame();
        JLabel item1 = new JLabel("Labour Force Per State Q2 ");
        item1.setToolTipText("By Bodunde");
        jframe.add(item1);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.setSize(275,180);
        jframe.setVisible(true);
        
        jframe.add(stateList);
        
        

// get the selected item:
       // String selectedBook = (String) stateList.getSelectedItem();
       

        // check whether the server is running or not
        System.out.println("Server is running: " + jedis.ping());
        //getting the percentage for each state
       
//        System.out.println((jedis.zrangeByScoreWithScores("names", 0, 1000).forEach(Tuple r:)}));
        // storing the data into redis database
       
        System.out.println(jedis.zrange("Labour Force Per State", 0, 100));
        
        Info.map.entrySet().stream().forEach((m) -> {
            System.out.println(m.getKey() + " " + m.getValue());

            
        });
        
        
    }
    
    private static class Handler implements ItemListener{


        @Override
        public void itemStateChanged(ItemEvent e) {
             for (Map.Entry m : redisData.entrySet()) {
                 if(e.getItem().toString() == m.getValue()&& e.getStateChange() == 1){
                     
                     JOptionPane.showMessageDialog(null, m.getKey() + "%", "Percentage", 1);
                     System.out.println(m.getKey());
                     break;
                     
                 }
         

          
        }
      
        }
}
}
  
