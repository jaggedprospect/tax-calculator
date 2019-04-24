package taxcalculatormain;

/**
 * Client Class for Tax Calculator Program
 *
 * @author Zachary Combs
 * @author Oghenero Ekwevugbe
 * @author Nate Heppard
 * @author Michael Kellam
 * @author Omolola Solaru
 * @version 1.0
 */

import java.io.Serializable;

public class Client implements Serializable{

    private String SSN;
    private String name;
    private double income;
    private int numDependents;
    private String federalTaxFile;
    private String filingStatus;
    private String state;
    
    public Client(String SSN,String name,double income){
        this.SSN=SSN;
        this.name=name;
        this.income=income;
    }

    public String getSSN(){
        return SSN;
    }

    public void setSSN(String SSN){
        this.SSN=SSN;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public double getIncome(){
        return income;
    }

    public void setIncome(double income){
        this.income=income;
    }
    
    public String getFederalTaxFile(){
        return federalTaxFile;
    }
    
    public void setFederalTaxFile(String federalTaxFile){
        this.federalTaxFile=federalTaxFile;
    }
    
    public int getNumDependents(){
        return numDependents;
    }
    
    public void setNumDependents(int numDependents){
        this.numDependents=numDependents;
    }
    
    public void setFilingStatus(String filingStatus){
        this.filingStatus=filingStatus;
    }
    
    public String getState(){
        return state;
    }
    
    public void setState(String state){
        this.state=state;
    }
    
    public void printClientInfo(){
        System.out.println("\n===========================");
        System.out.println(" Name: "+this.name);
        System.out.println(" SSN: "+this.SSN);
        System.out.println(" Taxable Income: $"+this.income);
        System.out.println(" # of Dependents: "+this.numDependents);
        System.out.println(" Filing Status: "+this.filingStatus);
        System.out.println(" State: "+this.state);
        System.out.println("===========================");
    }
    
    public boolean equals(Client other){
        return this.SSN.equals(other.getSSN());
    }
    
    @Override
    public String toString(){
        return name+",SSN:"+SSN;
    }
}

