/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dietclasses;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jesse Tan
 */
public class DietClasses {

    /**
     * @param args the command line arguments
     */   
    public double caloOfFood; //calories of food 
    public String fName; //name of the food
    public double nWOfFood; //net weight of food
    public String nutriName; // nutrient name 
    public double nutriAmount; //nutrient amount 
    public String timeConsumed;
    public static void main(String[] args) throws IOException, ParseException {
        // TODO code application logic here
        calories a = new calories ();
        a.dietChoices();
    }
    
}
