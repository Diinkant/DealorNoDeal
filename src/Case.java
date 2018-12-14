/**************************************
 * DEAL OR NO DEAL
 * 
 * DIINKANT RAVICHANDRAN 1385134
 * 
 **************************************/


public class Case {
	
//Declaring variables
    private double quantity;
    private boolean removed = false;
    private String open;

//Constructor for the variables

 
    public Case(double quantity) {	
	this.quantity = quantity;
    }

//Getters and Setter for the variables

    public double getQuantity() {
	return quantity;
    }

 
    public boolean isRemoved() {
	return removed;
    }


    public String getOpen() {
	return open;
    }


    public void setOpen(String open) {
	this.open = open;
    }


    public void setRemoved(boolean removed) {
	this.removed = removed;
    }

//Default values


    public void remove() {
	removed = true;
	open = "/";
    }

//Override to String method

  
    @Override
    public String toString() {
	return open;
    }
}