package taxcalculatormain;

/**
 * Main Class for Tax Calculator Program
 *
 * @author Zachary Combs
 * @author Oghenero Ekwevugbe
 * @author Nate Heppard
 * @author Michael Kellam
 * @author Omolola Solaru
 * @version 1.0
 */

import java.util.*;

public class TaxMain{
    
    private final String NAME_REGEX="(([a-z]*[A-Z]*)+\\s*){1,2}";
    private final String SSN_REGEX="\\d{3}-\\d{2}-\\d{4}";
    
    private ArrayList<Client> clients;
   
    public TaxMain(){
        clients=new ArrayList<>();
        
        printTitle();
        
        for(int i=0;i<3;i++)
            menu();
        
    }
    
    /**
     * Prompts user for Client information and creates new instance of Client -- 
     * Client object is added to ArrayList of Clients.
     */
    public void createClient(){
        Scanner in=new Scanner(System.in);
        String name,ssn;
        Double income;
        
        try{
            /*--- Name Block ---*/
            System.out.print("Enter Name:\n>> ");
            name=in.nextLine().replaceAll("\\s+"," ").trim();

            while(!name.matches(NAME_REGEX)){
                System.out.print("INVALID NAME.\n>> ");
                name=in.nextLine().replaceAll("\\s+"," ");
            }

            /*--- SSN Block ---*/
            System.out.print("Enter SSN (XXX-XX-XXXX):\n>> ");
            ssn=in.nextLine();

            while(!ssn.matches(SSN_REGEX)){
                System.out.print("INVALID SSN.\n>> ");
                ssn=in.nextLine();
            }
            
            /*--- Income Block ---*/
            System.out.print("Enter Taxable Income:\n>> $");
            income=in.nextDouble();
            
            while(income<0 || income>999999999){
                System.out.print("INVALID INCOME.\n>> $");
                income=in.nextDouble();
            }
            
            clients.add(new Client(name,ssn,income));
            System.out.println("CLIENT ADDED");
            
            menu(); // return to menu
            
        }catch(Exception e){
            System.out.println("Error Entering Client Information.\n"+e);
        }
    }
    
    /**
     * Instantiates the class Calculator -- uses Federal/State tax values
     * defined in Client to calculate taxes due.
     */
    public void calculateTaxes(){
        
    }
    
    /**
     * Prompts user for SSN of Client to edit -- if Client exists, user is
     * prompted to enter new information and the ArrayList is saved.
     */
    public void editClient(){
        
    }
    
    /**
     * Prompts user for SSN of Client to delete -- if Client exists, the 
     * Client is removed from the ArrayList and the  ArrayList is saved.
     */
    public void deleteClient(){
        
    }
    
    /**
     * Outputs a print statement to notify the closing of the program -- 
     * the thread is paused for 1000ms and System.exit is called within a 
     * try-catch clause.
     */
    public void exitSequence(){
        System.out.println("\nExiting...");
        
        try{
            Thread.sleep(1000);
            System.exit(0); // normal exit status
        }catch(InterruptedException e){
            System.out.println("Error While Closing Program.\n"+e);
        }
    }
    
    public void printTitle(){
        System.out.println("==================\n= Tax Calculator ="
                + "\n==================");
    }
    
    public void printMenu(){
        System.out.println("\n Menu\n======\n1) create client\n2) do taxes"
                + "\n3) edit client \n4) delete client\n5) quit");
    }
    
    public void menu(){
        Scanner in=new Scanner(System.in);
        String input;
        
        printMenu();
        
        for(;;){
            System.out.print("\n>> ");
            input=in.nextLine().toLowerCase().replaceAll("\\s+","");
            
            switch(input){
                case "1":
                case "one":
                    System.out.println("\n New Client\n============");
                    createClient();
                    
                case "2":
                case "two":
                    calculateTaxes();
                    
                case "3":
                case "three":
                    editClient();

                case "4":
                case "four":
                    deleteClient();

                case "5":
                case "five":
                    exitSequence();
                    
                default:
                    System.out.println("INVALID SELECTION.");
            }
        }
    }

    /**
     * Calculates Federal and State Taxes for Client.
     */
    private class Calculator{

        public Calculator(){
            // constructor
        }

        public void calculateFederalTax(){
            
        }
        
        public void calculateStateTax(){
            
        }
    }
    
    /**
     * Reads Serialized ArrayList from external .txt file -- Writes ArrayList
     * to external .txt file.
     */
    private class Stream{
        
        public Stream(){
            
        }
    }
}
