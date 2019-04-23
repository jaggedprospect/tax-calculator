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
    private int filingStatus; // integer 0-3
    
    ///////////////////////////////////////////////////
    // to determine filing status:                   //              
    //                                               //
    // hasDependents?                                //
    // true => getNumberOfDependents                 //
    // false => isSingle?                            //
    //      true => getSingleRates                   //
    //      false => isHead?                         //
    //          true => getHeadRates                 //
    //          false => isJoint?                    //
    //              true => getMarriedJointRates     //
    //              false => getMarriedSeparateRates //
    ///////////////////////////////////////////////////

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
    
    public void setFilingStatus(int filingStatus){
        this.filingStatus=filingStatus;
    }
    
    public boolean equals(Client other){
        return this.SSN.equals(other.getSSN());
    }
    
    @Override
    public String toString(){
        return name+",SSN:"+SSN;
    }
}

