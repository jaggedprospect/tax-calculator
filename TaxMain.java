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
import java.io.*;

public class TaxMain{
    
    private final String NAME_REGEX="(([a-z]*[A-Z]*)+\\s*){1,2}";
    private final String SSN_REGEX="\\d{3}-\\d{2}-\\d{4}";
    
    private ArrayList<Client> clients;
    private Stream stream;
   
    public TaxMain(){
        stream=new Stream();
        
        // load array if save file exists; otherwise, create new array
        if(stream.hasFile())
            stream.readFromSave();
        else
            clients=new ArrayList<>();
        
        printTitle();
        
        menu();
    }
    
    /**
     * Prompts user for Client information and creates new instance of Client -- 
     * Client object is added to ArrayList of Clients.
     */
    public void createClient(){
        Scanner in=new Scanner(System.in);
        String name,SSN;
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
            System.out.println("Enter SSN (XXX-XX-XXXX):");
            SSN=validSSN();
            
            while(isDuplicateSSN(SSN)){
                System.out.println("Client with that SSN already exists.");
                SSN=validSSN();
            }
            
            /*--- Income Block ---*/
            
            // WARNING
            // this block is not fail-proof -- entering a non-numerical
            // input will trigger an exception + error message
            
            System.out.print("Enter Taxable Income:\n>> $");
            income=in.nextDouble();
            
            while(income<0 || income>999999999){
                System.out.print("INVALID INCOME.\n>> $");
                income=in.nextDouble();
            }
            
            clients.add(new Client(SSN,name,income));
            System.out.println("Client Added.");
            
            stream.writeToSave(); // save and...
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
        Calculator calc=new Calculator();
        
        menu();
    }
    
    /**
     * Prompts user for SSN of Client to edit -- if Client exists, user is
     * prompted to enter new information and the ArrayList is saved.
     */
    public void editClient(){
        Scanner in=new Scanner(System.in);
        Client found;
        String search; // SSN to be searched
        
        System.out.println("Enter the SSN of the Client to Edit:");
        search=validSSN();
                
        // NEED TO SPECIFY WHAT INFO CAN BE MODIFIED
        // (probably just income and filing status)
        
        stream.writeToSave(); // save and...
        menu(); // return to menu
    }
    
    /**
     * Prompts user for SSN of Client to delete -- if Client exists, the 
     * Client is removed from the ArrayList and the  ArrayList is saved.
     */
    public void deleteClient(){
        Scanner in=new Scanner(System.in);
        String search; // SSN to be searched
        int foundIndex;
        
        System.out.println("\nEnter the SSN of the Client to Delete:");
        search=validSSN();
        
        foundIndex=findClient(search);
        
        if(foundIndex!=-1){
            clients.remove(foundIndex);
            System.out.println("Client Deleted.");
        }else
            System.out.println("Client Not Found.");
        
        stream.writeToSave(); // save and...
        menu(); // return to menu
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
                + "\n3) edit client \n4) delete client\n5) quit\n6) print array");
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
                    break;
                    
                case "2":
                case "two":
                    calculateTaxes();
                    break;
                    
                case "3":
                case "three":
                    editClient();
                    break;

                case "4":
                case "four":
                    deleteClient();
                    break;

                case "5":
                case "five":
                    exitSequence();
                    break;
                    
                // tempory case
                case "6":
                    System.out.println(clients);
                    break;
                      
                default:
                    System.out.println("INVALID SELECTION.");
            }
        }
    }
    
    /*--- HELPER METHODS ---*/
    
    /**
     * Searches the ArrayList of Clients for the Client SSN equal to the
     * passed parameter -- if found, the index of the Client is returned.
     * @param SSN
     * @return
     */
    public int findClient(String SSN){
        for(Client c : clients){
            if(c.getSSN().equals(SSN))
                return clients.indexOf(c);
        }
        
        return -1;
    }
    
    /**
     * Get input from user via Scanner and match against the defined SSN_REGEX
     * -- returns a valid SSN.
     * @return 
     */
    public String validSSN(){
        Scanner in=new Scanner(System.in);
        
        System.out.print(">> ");
        String SSN=in.nextLine();

            while(!SSN.matches(SSN_REGEX)){
                System.out.print("INVALID SSN.\n>> ");
                SSN=in.nextLine();
            }
            
        return SSN;
    }
    
    /**
     * Searches the ArrayList of Clients for the Client SSN equal to the 
     * passed parameter -- if found, the method returns true (for preventing
     * duplicate SSN entries).
     * @param SSN
     * @return 
     */
    public boolean isDuplicateSSN(String SSN){
        for(Client c : clients){
            if(c.getSSN().equals(SSN))
                return true;
        }
            
        return false;
    }
    
    /*--- END HELPER METHODS ---*/

    /**
     * Calculator Class
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
     * Stream Class
     * Reads Serialized ArrayList from external .txt file -- Writes ArrayList
     * to external .txt file.
     */
    private class Stream{
        
        private final String SAVE_FILE="taxCalc_save.txt";
        
        public Stream(){
            // constructor
        }
        
        public void readFromSave(){            
            try{
                FileInputStream fis=new FileInputStream(SAVE_FILE);
                ObjectInputStream ois=new ObjectInputStream(fis);
                clients=(ArrayList<Client>)ois.readObject(); // must cast to ArrayList
                ois.close();
                
            }catch(FileNotFoundException e){
                System.out.println(e); // couldn't find specified file
            }catch(IOException | ClassNotFoundException e){
                System.out.println(e); // error while writing/reading
            }
        }
        
        public void writeToSave(){
            try{
                FileOutputStream fos=new FileOutputStream(SAVE_FILE);
                ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(clients);
                oos.close();
                
            }catch(FileNotFoundException e){
                System.out.println(e); // couldn't find specified file
            }catch(IOException e){
                System.out.println(e); // error while writing/reading
            }
        }
        
        /**
         * Determines if a save file (matching the defined constant SAVE_FILE) 
         * already exists in the project folder.
         * @return 
         */
        public boolean hasFile(){
            return new File(SAVE_FILE).isFile();
        }
    }
}
