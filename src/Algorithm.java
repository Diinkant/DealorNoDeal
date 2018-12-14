/**************************************
 * DEAL OR NO DEAL
 * 
 * DIINKANT RAVICHANDRAN 1385134
 * 
 **************************************/

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 
 * This is the Algorithm class that stores values of price.
 */
public class Algorithm {
    //private static final int NUMBER_OF_BOXES = 25;
    private final ArrayList<Integer> priceList = new ArrayList<>();//Declare Arraylist
    private static final int[] priceArray = new int[]{1, 2, 5, 10, 50,//Declare static final integer array.
            100, 150, 200, 250, 500, 750,
            1000, 2000, 3000, 4000, 5000,
            10000, 15000, 20000, 30000, 50000,
            75000, 100000, 200000, 300000, 450000};
    
    protected Case[] briefCase;//Declare Briefcase object array.
    private DecimalFormat f = new DecimalFormat("##.00");
    private double total = 0;//Declare instance total as double
    private int time = 0;//Declare instance time as integer
    private double amount = 0;//Declare instance amount as double
    
    public Algorithm(){
        init();//Calling method to set up the case.
    }
    
    public void init(){
        for(int i : priceArray){//Using for loop to add values into the arraylist
            priceList.add(i);
        }
        Collections.shuffle(priceList);//Calling collection to shuffle the arraylist value
        
        briefCase = new Case[priceList.size()];//Initalize the length of BriefCase object array.
        for(int i = 0; i < this.briefCase.length; i++){//Using for loop to pass value into the object array.
            int value = priceList.get(i);
            this.briefCase[i] = new Case(value);
        }
    }
    
    public double getPrice(int buttonselect){//This method is to get the price from the 
        remove(buttonselect);
        return this.briefCase[buttonselect - 1].getQuantity();
    }
    
    public void remove(int num){//The method will set false to true when player choose to remove the case.
        this.briefCase[num - 1].remove();
    }

    public void setOffer(double caseamount, int countturn){//The method with 2 parameter is to calculate the price offer.
        for(int i = 0; i < this.briefCase.length; i++){
            if(!this.briefCase[i].isRemoved()){
                this.total += this.briefCase[i].getQuantity();
                this.time++;
            }
        }
        this.amount = (this.total/this.time * countturn/10);
    }
    
    public double getOffer(){//The method is to get the price offer after being calculated.
        return this.amount;
    }
}
