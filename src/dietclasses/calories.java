/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dietclasses;

/**
 *
 * @author Jesse Tan
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class calories {
    public double breakTotalCal; //calories of breakfast
    public double lunchTotalCal; //calories of lunch
    public double dinnerTotalCal; //calories of dinner
    public ArrayList <Double> totalCalForAll = new ArrayList<>();
    JSONArray diet = new JSONArray();
    
    
    public void dietChoices() throws IOException, ParseException{
        String[] dietMenuValues = {"Breakfast",
                                       "Lunch",
                                       "Dinner",
                                       "Calories intake",
                                       "Nutrient Recommendation",
                                       "Return to Main Menu"
        };
        ImageIcon iconDiet = new ImageIcon(this.getClass().getResource("iconDIET.gif"));
        
        String inDietMenu = (String)JOptionPane.showInputDialog(
                null,
                "Diet Menu",
                "Diet",
                JOptionPane.PLAIN_MESSAGE,
                iconDiet,
                dietMenuValues,
                dietMenuValues[0]);
        calories b = new calories();
        if ("Breakfast".equals(inDietMenu)){
            b.breakfastCal();
        }
        else if ("Lunch".equals(inDietMenu)){
            b.lunchCal();
        }
        else if ("Dinner".equals(inDietMenu)){
            b.dinnerCal();
        }
        else if ("Nutrient Recommendation".equals(inDietMenu)){
            b.nutrientIntake();
        }
        else if ("Calories intake".equals(inDietMenu)){
            String[] caloriesMenu = {"Check your total calories intake for today", 
                                       "Analyze your calories for today", 
            };
            ImageIcon iconCal = new ImageIcon(DietClasses.class.getResource("2058065.png"));
            String  inCalMenu = (String)JOptionPane.showInputDialog(
                null,
                "Calories Menu",
                "Smart Fitness Management",
                JOptionPane.PLAIN_MESSAGE,
                iconCal,
                caloriesMenu,
                caloriesMenu[0]);
         
            if ("Check your total calories intake for today".equals(inCalMenu)){
                b.totalCalIn();
        }

        else if (inDietMenu == "Return to Main Menu"){
            
        }
        
        
    }
    }
    public void breakfastCal() throws IOException, ParseException{
        breakTotalCal = 0.0;
        int i = 1;
        ArrayList <String> breakFdName = new ArrayList<>();
        ArrayList <Double> breakCal = new ArrayList<>();
        int validCount = 0;
        JSONArray breakFood = new JSONArray();
        JSONArray breakCalo = new JSONArray();
        
        while (i >= 1){
            JTextField foodName = new JTextField(10);
            JTextField calFood = new JTextField(10);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Enter the name of Food" + i + ": "));
            myPanel.add(foodName);
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.add(new JLabel("Enter the calories(kcal) of Food" + i + ": "));
            myPanel.add(calFood);
            Object [] options = {"More food? ", "No more!"};
            int nameF1 = JOptionPane.showOptionDialog(null,
                                                         myPanel,
                                                         "Breakfast Intake",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE,
                                                        null,
                                                         options,
                                                         options[0]
                                                         );
   
            if (!foodName.getText().matches("[a-zA-Z_]+")){
                JOptionPane.showMessageDialog(null, "Please enter alphabets only for the food name!!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            } 
            else if (foodName.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter a value for \"Food Name\" !!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            }
            else if (calFood.getText().matches("[a-zA-Z_]+") || calFood.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter numbers only for Calories !!","Error",JOptionPane.ERROR_MESSAGE);
                continue;
            } 
            else if (calFood.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter for a value for \" Calories\" !!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            }
            else if (foodName.getText() == null && calFood.getText() == null ){
                JOptionPane.showMessageDialog(null, "Please enter for a value for \" Calories\" !!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            }
            
            double calFd = Double.parseDouble(calFood.getText());
            breakTotalCal += calFd;
            breakFdName.add(foodName.getText());
            breakFood.add(foodName.getText());
            breakCal.add(calFd);
            breakCalo.add(calFd);
            Object[] choices = {"Yes", "No"};
            Object defaultChoice = choices[0];
            if (nameF1 == JOptionPane.YES_OPTION){
                i++;
                validCount ++;
            } else {
                break;
            }  
        }         
        
        JSONObject breakfast = new JSONObject();
        breakfast.put("Food Name",breakFood);
        breakfast.put("Calories of the Food",breakCalo);
        breakfast.put("Total Calories",breakTotalCal);
        JSONObject breakFile = new JSONObject();
        breakFile.put("Breakfast",breakfast );
        diet.add(breakFile);
        try (FileWriter file = new FileWriter("diet.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.append(diet.toJSONString()); 
            file.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel outPane = new JPanel();
        
        String[] columnNames = {"No.","Food Name","Calories(kcal) of the food"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Serif", Font.PLAIN, 24));
        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(200);
        
        Object data[] = new Object[3];
        for (i = 0; i <= validCount; i++){
            data[0] = i+1 + ".";
            data[1] = breakFdName.get(i);
            data[2] = breakCal.get(i);
            model.addRow(data);
        }
        
        JFrame f = new JFrame();
        JScrollPane sp=new JScrollPane(table);    
        outPane.add(sp);
        f.add(outPane);                        
        f.setSize(600,800);    
        f.setLocationRelativeTo(null);
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel totalCal = new JLabel("Your total calories for your lunch is: " + breakTotalCal + "kcal");
        totalCal.setFont(new Font("Serif", Font.PLAIN, 24));
        outPane.add(totalCal);
        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "BreakFast",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
        }
        totalCalForAll.add(breakTotalCal); 
       
    }
    
     public void lunchCal() throws IOException, ParseException{
        lunchTotalCal = 0.0;
        int i = 1;
        ArrayList <String> lunchFdName = new ArrayList<>();
        ArrayList <Double> lunchCal = new ArrayList<>();
        int validCount = 0;
        JSONParser jsonparser = new JSONParser ();
        
        FileReader reader = new FileReader("diet.json");
        Object obj = jsonparser.parse(reader);
        JSONArray previousObj = (JSONArray)obj;
        //JSONArray breakfastList = (JSONArray) previousObj.get ("Breakfast");
        
   
        JSONArray lunchFood = new JSONArray();
        JSONArray lunchCalo = new JSONArray();
        
        while (i >= 1){
            JTextField foodName = new JTextField(10);
            JTextField calFood = new JTextField(10);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Enter the name of Food" + i + ": "));
            myPanel.add(foodName);
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.add(new JLabel("Enter the calories(kcal) of Food" + i + ": "));
            myPanel.add(calFood);
            Object [] options = {"More food? ", "No more!"};
            int nameF1 = JOptionPane.showOptionDialog(null,
                                                         myPanel,
                                                         "Lunch Intake",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.QUESTION_MESSAGE,
                                                        null,
                                                         options,
                                                         options[0]
                                                         );
   
            if (!foodName.getText().matches("[a-zA-Z_]+")){
                JOptionPane.showMessageDialog(null, "Please enter alphabets only for the food name!!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            } else if (foodName.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter a value for \"Food Name\" !!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            }
            if (calFood.getText().matches("[a-zA-Z_]+") || calFood.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter numbers only for Calories !!","Error",JOptionPane.ERROR_MESSAGE);
                continue;
            } else if (foodName.getText() == null){
                JOptionPane.showMessageDialog(null, "Please enter for a value for \" Calories\" !!","Error",JOptionPane.ERROR_MESSAGE);
                i -= 1;
            }
            double calFd = Double.parseDouble(calFood.getText());
            lunchTotalCal += calFd;
            lunchFdName.add(foodName.getText());
            lunchFood.add(foodName.getText());
            lunchCal.add(calFd);
            lunchCalo.add(calFd);
            Object[] choices = {"Yes", "No"};
            Object defaultChoice = choices[0];
            if (nameF1 == JOptionPane.YES_OPTION){
                i++;
                validCount ++;
            } else {
                break;
            }  
        }         
        JSONObject lunch = new JSONObject();
        lunch.put("Food Name",lunchFood);
        lunch.put("Calories of the Food",lunchCalo);
        lunch.put("Total Calories",lunchTotalCal);
        JSONObject lunchFile = new JSONObject();
        lunchFile.put("Breakfast", previousObj);
        lunchFile.put("Lunch", lunch);
        diet.add(lunchFile);
        try (FileWriter file = new FileWriter("diet.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.append(diet.toJSONString()); 
            file.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel outPane = new JPanel();
        
        String[] columnNames = {"No.","Food Name","Calories(kcal) of the food"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Serif", Font.PLAIN, 24));
        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(200);
        
        Object data[] = new Object[3];
        for (i = 0; i <= validCount; i++){
            data[0] = i+1 + ".";
            data[1] = lunchFdName.get(i);
            data[2] = lunchCal.get(i);
            model.addRow(data);
        }
        
        JFrame f = new JFrame();
        JScrollPane sp=new JScrollPane(table);    
        outPane.add(sp);
        f.add(outPane);                        
        f.setSize(600,800);    
        f.setLocationRelativeTo(null);
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel totalCal = new JLabel("Your total calories for your lunch is: " + lunchTotalCal + "kcal");
        totalCal.setFont(new Font("Serif", Font.PLAIN, 24));
        outPane.add(totalCal);
        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "BreakFast",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
        }
        totalCalForAll.add(lunchTotalCal); 
       
    }
    public double dinnerCal(){
        double dinnerTotalCal = 0.0;
        int i = 1;
        while (i >= 1){
            String nameF1 = (String)JOptionPane.showInputDialog(null,
                                                               "Enter the name of Food" + i + ": ",
                                                               "Dinner Intake",
                                                               2,
                                                               null,
                                                               null,
                                                               null);
            if (nameF1 == null){
                break;
            };
            String calF1 = (String)JOptionPane.showInputDialog(null,
                                                               "Enter the calories of Food" + i + ": ",
                                                               "Dinner Intake",
                                                               2,
                                                               null,
                                                               null,
                                                               null);  

            double calF1D = Double.parseDouble(calF1);
            dinnerTotalCal += calF1D;
            i++;
        } 
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,"Your total calories for your dinner is: " + dinnerTotalCal + "kcal","Dinner Intake", JOptionPane.INFORMATION_MESSAGE);
       return dinnerTotalCal; 
    }
    public void totalCalIn () throws FileNotFoundException, IOException{

        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,"Your total calories for today is: "+ totalCalForAll + "kcal","Total Calories Intake for today",JOptionPane.INFORMATION_MESSAGE);
        
    

        JSONParser jsonparser = new JSONParser();
        try (FileReader reader = new FileReader ("diet.json"))
        {
        Object obj = jsonparser.parse(reader);
        JSONObject totalCal  = (JSONObject)obj;
        
        double bFastCal = (double)totalCal.get("Total Calories");
        for(int i = 0; i < )
        
        
        
    };
    }
    public void nutrientIntake() throws IOException, ParseException{
        
        String[] signs ={"Headaches",
                        "Fatique",
                        "Dry Mouth",
                        "Nausea",
                        "Dizziness",
                        "Constipation",
                        "Loss of Muscle Mass and Stunted Growth",
                        "Dry Skin",
                        "Hormonal Problems and Often Hungry",
                        "Night Blindness and Dry Eyes",
                        "Slow Healing Wounds",
                        "Bone Pain"
                        };
        String symptoms = (String)JOptionPane.showInputDialog(null,
                                                            "What is your symptoms??",
                                                            "Nutrient",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            signs,
                                                            signs[0]
                                                                );
        if (symptoms == "Headaches"){
            String[] headaches = {
                "Dry Mouth and Fatique",
                "Nauseas and Muscle Cramps",
                "Fatique and Cold Hands&Feets"
            };
            String secondSym = (String)JOptionPane.showInputDialog(null,
                                                                "What is your symptoms??",
                                                                "Nutrient",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                headaches,
                                                                headaches[0]
                                                                    );
            if (secondSym == "Dry Mouth and Fatique"){
            
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of WATER!<br/>Here are some foods for you to replenish WATER.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                
                ImageIcon water = new ImageIcon(getClass().getResource("Water/Water.jpg"));
                JLabel waterR = new JLabel ("Water",water,SwingConstants.CENTER);
                waterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(waterR);
                
                ImageIcon avocado = new ImageIcon(getClass().getResource("Water/Avocado.jpg"));
                JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
                avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(avocadoR);
                
                ImageIcon banana = new ImageIcon(getClass().getResource("Water/Banana.jpg"));
                JLabel  bananaR = new JLabel ("Banana",banana,SwingConstants.CENTER);
                bananaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(bananaR);
                
                ImageIcon coconutwater = new ImageIcon(getClass().getResource("Water/Coconut Water.jpg"));
                JLabel coconutwaterR = new JLabel ("Coconut",coconutwater,SwingConstants.CENTER);
                coconutwaterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(coconutwaterR);
                
                ImageIcon dairymilk = new ImageIcon(getClass().getResource("Water/Dairy Milk.jpg"));
                JLabel dairymilkR = new JLabel ("Dairy Milk",dairymilk,SwingConstants.CENTER);
                dairymilkR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(dairymilkR);
                
                ImageIcon watermelon = new ImageIcon(getClass().getResource("Water/Watermelon.jpg"));
                JLabel watermelonR = new JLabel ("Watermelon",watermelon,SwingConstants.CENTER);
                watermelonR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(watermelonR);
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }
            } else if (secondSym == "Nauseas and Muscle Cramps"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of SODIUM!<br/>Here are some foods for you to replenish SODIUM.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                    
                ImageIcon bread = new ImageIcon(getClass().getResource("Sodium/Bread.jpg"));
                JLabel breadR = new JLabel ("Bread",bread,SwingConstants.CENTER);
                breadR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(breadR);
                
                ImageIcon cheese = new ImageIcon(getClass().getResource("Sodium/Cheese.jpg"));
                JLabel cheeseR = new JLabel ("Cheese",cheese,SwingConstants.CENTER);
                cheeseR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(cheeseR);
                
                ImageIcon coldcuts = new ImageIcon(getClass().getResource("Sodium/Cold Cuts.jpeg"));
                JLabel coldcutsR = new JLabel ("Cold Cuts",coldcuts,SwingConstants.CENTER);
                coldcutsR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(coldcutsR);
                
                ImageIcon omelette = new ImageIcon(getClass().getResource("Sodium/Omelette.jpg"));
                JLabel omeletteR = new JLabel ("Omelette",omelette,SwingConstants.CENTER);
                omeletteR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(omeletteR);
                
                ImageIcon pizza = new ImageIcon(getClass().getResource("Sodium/Pizza.gif"));
                JLabel pizzaR = new JLabel ("Pizza",pizza,SwingConstants.CENTER);
                pizzaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(pizzaR);
                
                ImageIcon savourysnack = new ImageIcon(getClass().getResource("Sodium/Savory Snacks.jpg"));
                JLabel savourysnackR = new JLabel ("Savoury Snacks",savourysnack,SwingConstants.CENTER);
                savourysnackR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(savourysnackR);
                
                ImageIcon chicken = new ImageIcon(getClass().getResource("Sodium/Chicken.jpg"));
                JLabel chickenR = new JLabel ("Chicken",chicken,SwingConstants.CENTER);
                chickenR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(chickenR);
                
                ImageIcon soups = new ImageIcon(getClass().getResource("Sodium/Soups.jpg"));
                JLabel soupsR = new JLabel ("Soups",soups,SwingConstants.CENTER);
                soupsR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(soupsR);
        
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }
            }else if (secondSym == "Fatique and Cold Hands&Feets" ){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of IRON!<br/>Here are some foods for you to replenish IRON.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                    
                ImageIcon beans = new ImageIcon(getClass().getResource("Iron/Beans.jpg"));
                JLabel beansR = new JLabel ("Beans",beans,SwingConstants.CENTER);
                beansR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(beansR);
                
                ImageIcon breadPasta = new ImageIcon(getClass().getResource("Iron/Bread&Pasta.jpg"));
                JLabel breadPastaR = new JLabel ("Bread and Pasta",breadPasta,SwingConstants.CENTER);
                breadPastaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(breadPastaR);
                
                ImageIcon driedFruit = new ImageIcon(getClass().getResource("Iron/Dried Fruit.jpg"));
                JLabel driedFruitR = new JLabel ("Dried Fruit",driedFruit,SwingConstants.CENTER);
                driedFruitR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(driedFruitR);
                
                ImageIcon redMeat = new ImageIcon(getClass().getResource("Iron/Red Meat.jpg"));
                JLabel redMeatR = new JLabel ("Red Meat",redMeat,SwingConstants.CENTER);
                redMeatR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(redMeatR);
                
                ImageIcon seafood = new ImageIcon(getClass().getResource("Iron/Seafood.jpg"));
                JLabel seafoodR = new JLabel ("Seafood",seafood,SwingConstants.CENTER);
                seafoodR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(seafoodR);
                
                ImageIcon spinach = new ImageIcon(getClass().getResource("Iron/Spinach.jpg"));
                JLabel spinachR = new JLabel ("Spinach",spinach,SwingConstants.CENTER);
                spinachR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(spinachR);
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }

            }
    } else if (symptoms == "Fatique"){

            String[] fatique = {
                "Headaches and Dry Mouth",
                "Dizzines, Constipation and Nausea",
                "Headaches and Cold Hands&Feets"
            };
            String secondSym = (String)JOptionPane.showInputDialog(
                                                                null,
                                                                "What is your symptoms??",
                                                                "Nutrient",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                fatique,
                                                                fatique[0]
                                                                    );
            if (secondSym == "Headaches and Dry Mouth"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of WATER!<br/>Here are some foods for you to replenish WATER.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                
                ImageIcon water = new ImageIcon(getClass().getResource("Water/Water.jpg"));
                JLabel waterR = new JLabel ("Water",water,SwingConstants.CENTER);
                waterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(waterR);
                
                ImageIcon avocado = new ImageIcon(getClass().getResource("Water/Avocado.jpg"));
                JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
                avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(avocadoR);
                
                ImageIcon banana = new ImageIcon(getClass().getResource("Water/Banana.jpg"));
                JLabel  bananaR = new JLabel ("Banana",banana,SwingConstants.CENTER);
                bananaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(bananaR);
                
                ImageIcon coconutwater = new ImageIcon(getClass().getResource("Water/Coconut Water.jpg"));
                JLabel coconutwaterR = new JLabel ("Coconut",coconutwater,SwingConstants.CENTER);
                coconutwaterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(coconutwaterR);
                
                ImageIcon dairymilk = new ImageIcon(getClass().getResource("Water/Dairy Milk.jpg"));
                JLabel dairymilkR = new JLabel ("Dairy Milk",dairymilk,SwingConstants.CENTER);
                dairymilkR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(dairymilkR);
                
                ImageIcon watermelon = new ImageIcon(getClass().getResource("Water/Watermelon.jpg"));
                JLabel watermelonR = new JLabel ("Watermelon",watermelon,SwingConstants.CENTER);
                watermelonR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(watermelonR);
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                    }
                    
                }else if (secondSym == "Dizzines, Constipation and Nausea" ){
                    JPanel outPane = new JPanel();
                    outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                    JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of CARBOHYDRATES!<br/>Here are some foods for you to replenish CARBOHYDRATES.</div></html>", SwingConstants.CENTER);
                    message.setFont(new Font("Impact", Font.PLAIN, 24));
                    outPane.add(message);
                    
                    ImageIcon apple = new ImageIcon(getClass().getResource("Carbohydrates/Apple.jpg"));
                    JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
                    appleR.setFont(new Font("Impact", Font.PLAIN, 20));
                    outPane.add(appleR);
                    
                    ImageIcon beetroot = new ImageIcon(getClass().getResource("Carbohydrates/Beetroot.jpg"));
                    JLabel beetrootR = new JLabel ("Beetroot",beetroot,SwingConstants.CENTER);
                    beetrootR.setFont(new Font("Impact", Font.PLAIN, 20));
                    outPane.add(beetrootR);
                    
                    ImageIcon brownrice = new ImageIcon(getClass().getResource("Carbohydrates/Brown Rice.jpg"));
                    JLabel brownriceR = new JLabel ("Brown Rice",brownrice,SwingConstants.CENTER);
                    brownriceR.setFont(new Font("Impact", Font.PLAIN, 20));
                    outPane.add(brownriceR);
                    
                    ImageIcon corn = new ImageIcon(getClass().getResource("Carbohydrates/Corn.jpg"));
                    JLabel cornR = new JLabel ("Corn",corn,SwingConstants.CENTER);
                    cornR.setFont(new Font("Impact", Font.PLAIN, 20));
                    outPane.add(cornR);
                    
                    ImageIcon sweetpotatoes = new ImageIcon(getClass().getResource("Carbohydrates/Sweet Potatoes.jpg"));
                    JLabel sweetpotatoesR = new JLabel ("Sweet Potatoes",sweetpotatoes,SwingConstants.CENTER);
                    sweetpotatoesR.setFont(new Font("Impact", Font.PLAIN, 20));
                    outPane.add(sweetpotatoesR);
                    
                    Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                    int lastOpt = JOptionPane.showOptionDialog(null,
                                                             outPane,
                                                             "Food Recommendation",
                                                             JOptionPane.YES_NO_OPTION,
                                                             JOptionPane.PLAIN_MESSAGE,
                                                             null,
                                                             directionOpt,
                                                             directionOpt[0]
                                                             );   
                    if (lastOpt == JOptionPane.YES_OPTION){
                        dietChoices();
                        }
            }else if (secondSym == "Headaches and Cold Hands&Feets"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of IRON!<br/>Here are some foods for you to replenish IRON.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                    
                ImageIcon beans = new ImageIcon(getClass().getResource("Iron/Beans.jpg"));
                JLabel beansR = new JLabel ("Beans",beans,SwingConstants.CENTER);
                beansR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(beansR);
                
                ImageIcon breadPasta = new ImageIcon(getClass().getResource("Iron/Bread&Pasta.jpg"));
                JLabel breadPastaR = new JLabel ("Bread and Pasta",breadPasta,SwingConstants.CENTER);
                breadPastaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(breadPastaR);
                
                ImageIcon driedFruit = new ImageIcon(getClass().getResource("Iron/Dried Fruit.jpg"));
                JLabel driedFruitR = new JLabel ("Dried Fruit",driedFruit,SwingConstants.CENTER);
                driedFruitR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(driedFruitR);
                
                ImageIcon redMeat = new ImageIcon(getClass().getResource("Iron/Red Meat.jpg"));
                JLabel redMeatR = new JLabel ("Red Meat",redMeat,SwingConstants.CENTER);
                redMeatR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(redMeatR);
                
                ImageIcon seafood = new ImageIcon(getClass().getResource("Iron/Seafood.jpg"));
                JLabel seafoodR = new JLabel ("Seafood",seafood,SwingConstants.CENTER);
                seafoodR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(seafoodR);
                
                ImageIcon spinach = new ImageIcon(getClass().getResource("Iron/Spinach.jpg"));
                JLabel spinachR = new JLabel ("Spinach",spinach,SwingConstants.CENTER);
                spinachR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(spinachR);
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }
            }
        }else if (symptoms == "Dry Mouth"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of WATER!<br/>Here are some foods for you to replenish WATER.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                
                ImageIcon water = new ImageIcon(getClass().getResource("Water/Water.jpg"));
                JLabel waterR = new JLabel ("Water",water,SwingConstants.CENTER);
                waterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(waterR);
                
                ImageIcon avocado = new ImageIcon(getClass().getResource("Water/Avocado.jpg"));
                JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
                avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(avocadoR);
                
                ImageIcon banana = new ImageIcon(getClass().getResource("Water/Banana.jpg"));
                JLabel  bananaR = new JLabel ("Banana",banana,SwingConstants.CENTER);
                bananaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(bananaR);
                
                ImageIcon coconutwater = new ImageIcon(getClass().getResource("Water/Coconut Water.jpg"));
                JLabel coconutwaterR = new JLabel ("Coconut",coconutwater,SwingConstants.CENTER);
                coconutwaterR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(coconutwaterR);
                
                ImageIcon dairymilk = new ImageIcon(getClass().getResource("Water/Dairy Milk.jpg"));
                JLabel dairymilkR = new JLabel ("Dairy Milk",dairymilk,SwingConstants.CENTER);
                dairymilkR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(dairymilkR);
                
                ImageIcon watermelon = new ImageIcon(getClass().getResource("Water/Watermelon.jpg"));
                JLabel watermelonR = new JLabel ("Watermelon",watermelon,SwingConstants.CENTER);
                watermelonR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(watermelonR);
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                    }
        }else if (symptoms == "Nausea"){
            String[] nausea = {
                "Fatique,Dizziness and Constipation",
                "Headaches and Muscle Cramps",
                "Weigh Gain and Constipation"
            };
            String secondSym = (String)JOptionPane.showInputDialog(
                                                                null,
                                                                "What is your symptoms??",
                                                                "Nutrient",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                nausea,
                                                                nausea[0]
                                                                    );
            if (secondSym == "Fatique,Dizziness and Constipation"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of CARBOHYDRATES!<br/>Here are some foods for you to replenish CRABOHYDRATES.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);

                ImageIcon apple = new ImageIcon(getClass().getResource("Carbohydrates/Apple.jpg"));
                JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
                appleR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(appleR);

                ImageIcon beetroot = new ImageIcon(getClass().getResource("Carbohydrates/Beetroot.jpg"));
                JLabel beetrootR = new JLabel ("Beetroot",beetroot,SwingConstants.CENTER);
                beetrootR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(beetrootR);

                ImageIcon brownrice = new ImageIcon(getClass().getResource("Carbohydrates/Brown Rice.jpg"));
                JLabel brownriceR = new JLabel ("Brown Rice",brownrice,SwingConstants.CENTER);
                brownriceR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(brownriceR);

                ImageIcon corn = new ImageIcon(getClass().getResource("Carbohydrates/Corn.jpg"));
                JLabel cornR = new JLabel ("Corn",corn,SwingConstants.CENTER);
                cornR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(cornR);

                ImageIcon sweetpotatoes = new ImageIcon(getClass().getResource("Carbohydrates/Sweet Potatoes.jpg"));
                JLabel sweetpotatoesR = new JLabel ("Sweet Potatoes",sweetpotatoes,SwingConstants.CENTER);
                sweetpotatoesR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(sweetpotatoesR);

                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                    }
            
            }else if (secondSym == "Headaches and Muscle Cramps"){                
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of SODIUM!<br/>Here are some foods for you to replenish SODIUM.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);
                    
                ImageIcon bread = new ImageIcon(getClass().getResource("Sodium/Bread.jpg"));
                JLabel breadR = new JLabel ("Bread",bread,SwingConstants.CENTER);
                breadR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(breadR);
                
                ImageIcon cheese = new ImageIcon(getClass().getResource("Sodium/Cheese.jpg"));
                JLabel cheeseR = new JLabel ("Cheese",cheese,SwingConstants.CENTER);
                cheeseR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(cheeseR);
                
                ImageIcon coldcuts = new ImageIcon(getClass().getResource("Sodium/Cold Cuts.jpeg"));
                JLabel coldcutsR = new JLabel ("Cold Cuts",coldcuts,SwingConstants.CENTER);
                coldcutsR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(coldcutsR);
                
                ImageIcon omelette = new ImageIcon(getClass().getResource("Sodium/Omelette.jpg"));
                JLabel omeletteR = new JLabel ("Omelette",omelette,SwingConstants.CENTER);
                omeletteR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(omeletteR);
                
                ImageIcon pizza = new ImageIcon(getClass().getResource("Sodium/Pizza.gif"));
                JLabel pizzaR = new JLabel ("Pizza",pizza,SwingConstants.CENTER);
                pizzaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(pizzaR);
                
                ImageIcon savourysnack = new ImageIcon(getClass().getResource("Sodium/Savory Snacks.jpg"));
                JLabel savourysnackR = new JLabel ("Savoury Snacks",savourysnack,SwingConstants.CENTER);
                savourysnackR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(savourysnackR);
                
                ImageIcon chicken = new ImageIcon(getClass().getResource("Sodium/Chicken.jpg"));
                JLabel chickenR = new JLabel ("Chicken",chicken,SwingConstants.CENTER);
                chickenR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(chickenR);
                
                ImageIcon soups = new ImageIcon(getClass().getResource("Sodium/Soups.jpg"));
                JLabel soupsR = new JLabel ("Soups",soups,SwingConstants.CENTER);
                soupsR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(soupsR);
        
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }
                
                
            }else if (secondSym == "Weigh Gain and Constipation"){
                JPanel outPane = new JPanel();
                outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
                JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of FIBRE!<br/>Here are some foods for you to replenish FIBRE.</div></html>", SwingConstants.CENTER);
                message.setFont(new Font("Impact", Font.PLAIN, 24));
                outPane.add(message);  
                
                ImageIcon almond = new ImageIcon(getClass().getResource("Fibre/Almond.jpg"));
                JLabel almondR = new JLabel ("Almond",almond,SwingConstants.CENTER);
                almondR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(almondR);
                
                ImageIcon apple = new ImageIcon(getClass().getResource("Fibre/Apple.jpg"));
                JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
                appleR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(appleR);
                
                ImageIcon avocado = new ImageIcon(getClass().getResource("Fibre/Avocado.jpg"));
                JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
                avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(avocadoR);
                
                ImageIcon banana = new ImageIcon(getClass().getResource("Fibre/Banana.jpg"));
                JLabel bananaR = new JLabel ("Banana",banana,SwingConstants.CENTER);
                bananaR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(bananaR);
                
                ImageIcon broccoli = new ImageIcon(getClass().getResource("Fibre/Broccoli.jpg"));
                JLabel broccoliR = new JLabel ("Broccoli",broccoli,SwingConstants.CENTER);
                broccoliR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(broccoliR);
                
                ImageIcon chiaSeed = new ImageIcon(getClass().getResource("Fibre/Chia Seed.jpg"));
                JLabel chiaSeedR = new JLabel ("Chia Seed",chiaSeed,SwingConstants.CENTER);
                chiaSeedR.setFont(new Font("Impact", Font.PLAIN, 20));
                outPane.add(chiaSeedR);
                
                
                Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
                int lastOpt = JOptionPane.showOptionDialog(null,
                                                         outPane,
                                                         "Food Recommendation",
                                                         JOptionPane.YES_NO_OPTION,
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null,
                                                         directionOpt,
                                                         directionOpt[0]
                                                         );   
                if (lastOpt == JOptionPane.YES_OPTION){
                    dietChoices();
                }
                
            }
    }else if (symptoms == "Dizziness"){
        JPanel outPane = new JPanel();
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of CARBOHYDRATES!<br/>Here are some foods for you to replenish CRABOHYDRATES.</div></html>", SwingConstants.CENTER);
        message.setFont(new Font("Impact", Font.PLAIN, 24));
        outPane.add(message);

        ImageIcon apple = new ImageIcon(getClass().getResource("Carbohydrates/Apple.jpg"));
        JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
        appleR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(appleR);

        ImageIcon beetroot = new ImageIcon(getClass().getResource("Carbohydrates/Beetroot.jpg"));
        JLabel beetrootR = new JLabel ("Beetroot",beetroot,SwingConstants.CENTER);
        beetrootR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(beetrootR);

        ImageIcon brownrice = new ImageIcon(getClass().getResource("Carbohydrates/Brown Rice.jpg"));
        JLabel brownriceR = new JLabel ("Brown Rice",brownrice,SwingConstants.CENTER);
        brownriceR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(brownriceR);

        ImageIcon corn = new ImageIcon(getClass().getResource("Carbohydrates/Corn.jpg"));
        JLabel cornR = new JLabel ("Corn",corn,SwingConstants.CENTER);
        cornR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(cornR);

        ImageIcon sweetpotatoes = new ImageIcon(getClass().getResource("Carbohydrates/Sweet Potatoes.jpg"));
        JLabel sweetpotatoesR = new JLabel ("Sweet Potatoes",sweetpotatoes,SwingConstants.CENTER);
        sweetpotatoesR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(sweetpotatoesR);

        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "Food Recommendation",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
            }
    }else if (symptoms == "Constipation"){
        String[] constipation = {
            "Fatique,Dizziness and Nausea",
            "Nausea, Tiredness and Weight Gain"
        };
        String secondSym = (String)JOptionPane.showInputDialog(
                                                            null,
                                                            "What is your symptoms??",
                                                            "Nutrient",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            constipation,
                                                            constipation[0]
                                                                );
        if (secondSym == "Fatique,Dizziness and Nausea"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of CARBOHYDRATES!<br/>Here are some foods for you to replenish CRABOHYDRATES.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);

            ImageIcon apple = new ImageIcon(getClass().getResource("Carbohydrates/Apple.jpg"));
            JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
            appleR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(appleR);

            ImageIcon beetroot = new ImageIcon(getClass().getResource("Carbohydrates/Beetroot.jpg"));
            JLabel beetrootR = new JLabel ("Beetroot",beetroot,SwingConstants.CENTER);
            beetrootR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(beetrootR);

            ImageIcon brownrice = new ImageIcon(getClass().getResource("Carbohydrates/Brown Rice.jpg"));
            JLabel brownriceR = new JLabel ("Brown Rice",brownrice,SwingConstants.CENTER);
            brownriceR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(brownriceR);

            ImageIcon corn = new ImageIcon(getClass().getResource("Carbohydrates/Corn.jpg"));
            JLabel cornR = new JLabel ("Corn",corn,SwingConstants.CENTER);
            cornR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(cornR);

            ImageIcon sweetpotatoes = new ImageIcon(getClass().getResource("Carbohydrates/Sweet Potatoes.jpg"));
            JLabel sweetpotatoesR = new JLabel ("Sweet Potatoes",sweetpotatoes,SwingConstants.CENTER);
            sweetpotatoesR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(sweetpotatoesR);

            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }
        } else if (secondSym == "Nausea, Tiredness and Weight Gain"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of FIBRE!<br/>Here are some foods for you to replenish FIBRE.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);  

            ImageIcon almond = new ImageIcon(getClass().getResource("Fibre/Almond.jpg"));
            JLabel almondR = new JLabel ("Almond",almond,SwingConstants.CENTER);
            almondR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(almondR);

            ImageIcon apple = new ImageIcon(getClass().getResource("Fibre/Apple.jpg"));
            JLabel appleR = new JLabel ("Apple",apple,SwingConstants.CENTER);
            appleR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(appleR);

            ImageIcon avocado = new ImageIcon(getClass().getResource("Fibre/Avocado.jpg"));
            JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
            avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(avocadoR);

            ImageIcon banana = new ImageIcon(getClass().getResource("Fibre/Banana.jpg"));
            JLabel bananaR = new JLabel ("Banana",banana,SwingConstants.CENTER);
            bananaR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(bananaR);

            ImageIcon broccoli = new ImageIcon(getClass().getResource("Fibre/Broccoli.jpg"));
            JLabel broccoliR = new JLabel ("Broccoli",broccoli,SwingConstants.CENTER);
            broccoliR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(broccoliR);

            ImageIcon chiaSeed = new ImageIcon(getClass().getResource("Fibre/Chia Seed.jpg"));
            JLabel chiaSeedR = new JLabel ("Chia Seed",chiaSeed,SwingConstants.CENTER);
            chiaSeedR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(chiaSeedR);


            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
            }
        }
    }else if (symptoms == "Loss of Muscle Mass and Stunted Growth"){
        JPanel outPane = new JPanel();
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of PROTEIN!<br/>Here are some foods for you to replenish PROTEIN.</div></html>", SwingConstants.CENTER);
        message.setFont(new Font("Impact", Font.PLAIN, 24));
        outPane.add(message); 
        
        ImageIcon almond = new ImageIcon(getClass().getResource("Protein/Almond.jpg"));
        JLabel almondR = new JLabel ("Almond",almond,SwingConstants.CENTER);
        almondR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(almondR);
        
        ImageIcon dairyMilk = new ImageIcon(getClass().getResource("Protein/Dairy Milk.jpg"));
        JLabel dairyMilkR = new JLabel ("Dairy Milk",dairyMilk,SwingConstants.CENTER);
        dairyMilkR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(dairyMilkR);
        
        ImageIcon egg = new ImageIcon(getClass().getResource("Protein/Egg.jpg"));
        JLabel eggR = new JLabel ("Egg",egg,SwingConstants.CENTER);
        eggR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(eggR);
        
        ImageIcon peanut = new ImageIcon(getClass().getResource("Protein/Peanut.jpg"));
        JLabel peanutR = new JLabel ("Peanut",peanut,SwingConstants.CENTER);
        peanutR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(peanutR);
        
        ImageIcon tuna = new ImageIcon(getClass().getResource("Protein/Tuna.jpg"));
        JLabel tunaR = new JLabel ("Tuna",tuna,SwingConstants.CENTER);
        tunaR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(tunaR);
        
        ImageIcon turkeyBreast = new ImageIcon(getClass().getResource("Protein/Turkey Breast.jpg"));
        JLabel turkeyBreastR = new JLabel ("Turkey Breast",turkeyBreast,SwingConstants.CENTER);
        turkeyBreastR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(turkeyBreastR);
        
        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "Food Recommendation",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
            }
    }else if (symptoms == "Dry Skin"){
        String[] drySkin = {
            "Fatique, Stunted Growth and Loss of Muscle Mass",
            "Often Hungry and Hormonal Problems"
        };
        String secondSym = (String)JOptionPane.showInputDialog(
                                                            null,
                                                            "What is your symptoms??",
                                                            "Nutrient",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            drySkin,
                                                            drySkin[0]
                                                                );
        if (secondSym == "Fatique, Stunted Growth and Loss of Muscle Mass"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of PROTEIN!<br/>Here are some foods for you to replenish PROTEIN.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message); 

            ImageIcon almond = new ImageIcon(getClass().getResource("Protein/Almond.jpg"));
            JLabel almondR = new JLabel ("Almond",almond,SwingConstants.CENTER);
            almondR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(almondR);

            ImageIcon dairyMilk = new ImageIcon(getClass().getResource("Protein/Dairy Milk.jpg"));
            JLabel dairyMilkR = new JLabel ("Dairy Milk",dairyMilk,SwingConstants.CENTER);
            dairyMilkR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(dairyMilkR);

            ImageIcon egg = new ImageIcon(getClass().getResource("Protein/Egg.jpg"));
            JLabel eggR = new JLabel ("Egg",egg,SwingConstants.CENTER);
            eggR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(eggR);

            ImageIcon peanut = new ImageIcon(getClass().getResource("Protein/Peanut.jpg"));
            JLabel peanutR = new JLabel ("Peanut",peanut,SwingConstants.CENTER);
            peanutR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(peanutR);

            ImageIcon tuna = new ImageIcon(getClass().getResource("Protein/Tuna.jpg"));
            JLabel tunaR = new JLabel ("Tuna",tuna,SwingConstants.CENTER);
            tunaR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(tunaR);

            ImageIcon turkeyBreast = new ImageIcon(getClass().getResource("Protein/Turkey Breast.jpg"));
            JLabel turkeyBreastR = new JLabel ("Turkey Breast",turkeyBreast,SwingConstants.CENTER);
            turkeyBreastR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(turkeyBreastR);

            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }            
        }else if (secondSym == "Often Hungry and Hormonal Problems" ){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of FAT!<br/>Here are some foods for you to replenish FAT.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);       
            
            ImageIcon avocado = new ImageIcon(getClass().getResource("Fat/Avocado.jpg"));
            JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
            avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(avocadoR);
            
            ImageIcon chiaSeed = new ImageIcon(getClass().getResource("Fat/Chia Seed.jpg"));
            JLabel chiaSeedR = new JLabel ("Chia Seed",chiaSeed,SwingConstants.CENTER);
            chiaSeedR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(chiaSeedR);
            
            ImageIcon darkChocolate = new ImageIcon(getClass().getResource("Fat/Dark Chocolate.jpg"));
            JLabel darkChocolateR = new JLabel ("Dark Chocolate",darkChocolate,SwingConstants.CENTER);
            darkChocolateR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(darkChocolateR);
                    
            ImageIcon egg = new ImageIcon(getClass().getResource("Fat/Egg.jpg"));
            JLabel eggR = new JLabel ("Egg",egg,SwingConstants.CENTER);
            eggR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(eggR);
            
            ImageIcon olive = new ImageIcon(getClass().getResource("Fat/Olive.jpg"));
            JLabel oliveR = new JLabel ("Olive",olive,SwingConstants.CENTER);
            oliveR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(avocadoR);
            
            ImageIcon salmon = new ImageIcon(getClass().getResource("Fat/Salmon.jpg"));
            JLabel salmonR = new JLabel ("Salmon",salmon,SwingConstants.CENTER);
            salmonR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(salmonR);
            
            ImageIcon tofu = new ImageIcon(getClass().getResource("Fat/Tofu.jpg"));
            JLabel tofuR = new JLabel ("Avocado",tofu,SwingConstants.CENTER);
            avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(tofuR);
            
            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }
        }
    
    }else if (symptoms == "Hormonal Problems and Often Hungry"){
        JPanel outPane = new JPanel();
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of FAT!<br/>Here are some foods for you to replenish FAT.</div></html>", SwingConstants.CENTER);
        message.setFont(new Font("Impact", Font.PLAIN, 24));
        outPane.add(message);       

        ImageIcon avocado = new ImageIcon(getClass().getResource("Fat/Avocado.jpg"));
        JLabel avocadoR = new JLabel ("Avocado",avocado,SwingConstants.CENTER);
        avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(avocadoR);

        ImageIcon chiaSeed = new ImageIcon(getClass().getResource("Fat/Chia Seed.jpg"));
        JLabel chiaSeedR = new JLabel ("Chia Seed",chiaSeed,SwingConstants.CENTER);
        chiaSeedR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(chiaSeedR);

        ImageIcon darkChocolate = new ImageIcon(getClass().getResource("Fat/Dark Chocolate.jpg"));
        JLabel darkChocolateR = new JLabel ("Dark Chocolate",darkChocolate,SwingConstants.CENTER);
        darkChocolateR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(darkChocolateR);

        ImageIcon egg = new ImageIcon(getClass().getResource("Fat/Egg.jpg"));
        JLabel eggR = new JLabel ("Egg",egg,SwingConstants.CENTER);
        eggR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(eggR);

        ImageIcon olive = new ImageIcon(getClass().getResource("Fat/Olive.jpg"));
        JLabel oliveR = new JLabel ("Olive",olive,SwingConstants.CENTER);
        oliveR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(avocadoR);

        ImageIcon salmon = new ImageIcon(getClass().getResource("Fat/Salmon.jpg"));
        JLabel salmonR = new JLabel ("Salmon",salmon,SwingConstants.CENTER);
        salmonR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(salmonR);

        ImageIcon tofu = new ImageIcon(getClass().getResource("Fat/Tofu.jpg"));
        JLabel tofuR = new JLabel ("Avocado",tofu,SwingConstants.CENTER);
        avocadoR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(tofuR);

        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "Food Recommendation",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
            }
        
    }else if (symptoms == "Night Blindness and Dry Eyes"){
        JPanel outPane = new JPanel();
        outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
        JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of  VITAMIN A!<br/>Here are some foods for you to replenish VITAMIN A.</div></html>", SwingConstants.CENTER);
        message.setFont(new Font("Impact", Font.PLAIN, 24));
        outPane.add(message);  
        
        ImageIcon beefLiver = new ImageIcon(getClass().getResource("Vitamin A/Beef Liver.jpg"));
        JLabel beefLiverR = new JLabel ("Beef Liver",beefLiver,SwingConstants.CENTER);
        beefLiverR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(beefLiverR);
        
        ImageIcon cantaloupes = new ImageIcon(getClass().getResource("Vitamin A/Cantaloupes.jpg"));
        JLabel cantaloupesR = new JLabel ("Beef Liver",cantaloupes,SwingConstants.CENTER);
        cantaloupesR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(cantaloupesR);
        
        ImageIcon salmon = new ImageIcon(getClass().getResource("Vitamin A/Salmon.jpg"));
        JLabel salmonR = new JLabel ("Beef Liver",salmon,SwingConstants.CENTER);
        salmonR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(salmonR);
        
        ImageIcon spinanch = new ImageIcon(getClass().getResource("Vitamin A/Cantaloupes.jpg"));
        JLabel spinanchR = new JLabel ("Spinanch",spinanch,SwingConstants.CENTER);
        spinanchR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(spinanchR);
        
        ImageIcon tuna = new ImageIcon(getClass().getResource("Vitamin A/Tuna.jpg"));
        JLabel tunaR = new JLabel ("Tuna",tuna,SwingConstants.CENTER);
        tunaR.setFont(new Font("Impact", Font.PLAIN, 20));
        outPane.add(tunaR);
        
        Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
        int lastOpt = JOptionPane.showOptionDialog(null,
                                                 outPane,
                                                 "Food Recommendation",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 directionOpt,
                                                 directionOpt[0]
                                                 );   
        if (lastOpt == JOptionPane.YES_OPTION){
            dietChoices();
            }
    }else if (symptoms == "Slow Healing Wounds"){
        String[] slow = {
            "Easy Bruising",
            "Loss of Appetite and Diarrhea"
        };
        String secondSym = (String)JOptionPane.showInputDialog(
                                                            null,
                                                            "What is your symptoms??",
                                                            "Nutrient",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            slow,
                                                            slow[0]
                                                                );
        if (secondSym == "Easy Bruising"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of VITAMIN C!<br/>Here are some foods for you to replenish VITAMIN C.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);
            
            ImageIcon blackcurrants = new ImageIcon(getClass().getResource("Vitamin C/Blackcurrants.jpg"));
            JLabel blackcurrantsR = new JLabel ("Blackcurrants",blackcurrants,SwingConstants.CENTER);
            blackcurrantsR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(blackcurrantsR);
            
            ImageIcon broccoli = new ImageIcon(getClass().getResource("Vitamin C/Broccoli.jpg"));
            JLabel broccoliR = new JLabel ("Broccoli",broccoli,SwingConstants.CENTER);
            broccoliR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(broccoliR);
            
            ImageIcon brusselSprouts = new ImageIcon(getClass().getResource("Vitamin C/Brussels Sprouts.jpg"));
            JLabel brusselSproutsR = new JLabel ("Brussels Sprouts",brusselSprouts,SwingConstants.CENTER);
            brusselSproutsR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(brusselSproutsR);
            
            ImageIcon potato = new ImageIcon(getClass().getResource("Vitamin C/Potato.jpg"));
            JLabel potatoR = new JLabel ("Potato",potato,SwingConstants.CENTER);
            potatoR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(potatoR);
            
            ImageIcon spinach = new ImageIcon(getClass().getResource("Vitamin C/Spinach.jpg"));
            JLabel spinachR = new JLabel ("Spinach",spinach,SwingConstants.CENTER);
            spinachR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(spinachR);
            
            ImageIcon strawberry = new ImageIcon(getClass().getResource("Vitamin C/Strawberry.jpg"));
            JLabel strawberryR = new JLabel ("Strawberry",strawberry,SwingConstants.CENTER);
            strawberryR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(strawberryR);
            
            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }
            
        }else if (secondSym == "Loss of Appetite and Diarrhea"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of ZINC!<br/>Here are some foods for you to replenish ZINC.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);
           
            ImageIcon darkChoco = new ImageIcon(getClass().getResource("Zinc/Dark Chocolate.jpg"));
            JLabel darkChocoR = new JLabel ("Dark Chocolate",darkChoco,SwingConstants.CENTER);
            darkChocoR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(darkChocoR);
            
            ImageIcon egg = new ImageIcon(getClass().getResource("Zinc/Egg.jpg"));
            JLabel eggR = new JLabel ("Egg",egg,SwingConstants.CENTER);
            eggR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(eggR);
            
            ImageIcon meat = new ImageIcon(getClass().getResource("Zinc/Meat.jpg"));
            JLabel meatR = new JLabel ("Meat",meat,SwingConstants.CENTER);
            meatR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(meatR);
            
            ImageIcon nut = new ImageIcon(getClass().getResource("Zinc/Nut.jpg"));
            JLabel nutR = new JLabel ("Nut",nut,SwingConstants.CENTER);
            nutR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(darkChocoR);
            
            ImageIcon shellish = new ImageIcon(getClass().getResource("Zinc/Shellfish.jpg"));
            JLabel shellishR = new JLabel ("Shellfish",shellish,SwingConstants.CENTER);
            shellishR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(shellishR);
            
            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }
            
        }
    }else if (symptoms == "Bone Pain"){
            JPanel outPane = new JPanel();
            outPane.setLayout(new BoxLayout(outPane, BoxLayout.Y_AXIS));
            JLabel message = new JLabel ("<html><div style = 'text-align: center;'>You are lack of VITAMIN D!<br/>Here are some foods for you to replenish VITAMIN D.</div></html>", SwingConstants.CENTER);
            message.setFont(new Font("Impact", Font.PLAIN, 24));
            outPane.add(message);
            
            ImageIcon eggYolk = new ImageIcon(getClass().getResource("Vitamin D/Egg Yolk.jpg"));
            JLabel eggYolkR = new JLabel ("Egg Yolk",eggYolk,SwingConstants.CENTER);
            eggYolkR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(eggYolkR);
            
            ImageIcon foods = new ImageIcon(getClass().getResource("Vitamin D/Fortified Foods.jpg"));
            JLabel foodsR = new JLabel ("Fortified Foods",foods,SwingConstants.CENTER);
            foodsR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(foodsR);
            
            ImageIcon liver = new ImageIcon(getClass().getResource("Vitamin D/Liver.jpg"));
            JLabel liverR = new JLabel ("Liver",liver,SwingConstants.CENTER);
            liverR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(liverR);
            
            ImageIcon redMeat = new ImageIcon(getClass().getResource("Vitamin D/Red Meat.jpg"));
            JLabel redMeatR = new JLabel ("Red Meat",redMeat,SwingConstants.CENTER);
            redMeatR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(redMeatR);
            
            ImageIcon salmon = new ImageIcon(getClass().getResource("Vitamin D/Salmon.jpg"));
            JLabel salmonR = new JLabel ("Egg Yolk",salmon,SwingConstants.CENTER);
            salmonR.setFont(new Font("Impact", Font.PLAIN, 20));
            outPane.add(salmonR);
            
            Object [] directionOpt = {"Return to Diet Menu", "Return to Main Menu"};
            int lastOpt = JOptionPane.showOptionDialog(null,
                                                     outPane,
                                                     "Food Recommendation",
                                                     JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.PLAIN_MESSAGE,
                                                     null,
                                                     directionOpt,
                                                     directionOpt[0]
                                                     );   
            if (lastOpt == JOptionPane.YES_OPTION){
                dietChoices();
                }
    }
        
    
}

    
}




