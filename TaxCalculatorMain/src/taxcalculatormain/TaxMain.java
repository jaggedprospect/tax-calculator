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
    private Scanner in;
   
    public TaxMain(){
        stream=new Stream();
        
        // load array if save file exists; otherwise, create new array
        if(stream.hasFile()){
            stream.readFromSave();
            System.out.println(">: Client List Loaded From Save File.");
        }else{
            clients=new ArrayList<>();
            System.out.println(">: New Client List Created.");
        }
    }
    
    public void run() throws InterruptedException{
        //printDisclaimer();
        
        Thread.sleep(1000);
        printTitle();
        
        menu();
    }
    
    /*--- MAIN METHODS ---*/
    
    /**
     * Prompts user for Client information and creates new instance of Client -- 
     * Client object is added to ArrayList of Clients.
     */
    public void createClient(){
        in=new Scanner(System.in);
        Client client;
        String name,SSN,state;
        Double income;
        int dependents;
        
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
            
            client=new Client(SSN,name,income);

            /*--- State Block ---*/
            in=new Scanner(System.in);
            System.out.print("Enter State of Residence (XX):\n>> ");
            state=in.nextLine().toUpperCase().trim();

            while(!(state.equals("AK")||state.equals("AL")||state.equals("AK")||state.equals("AL")||
                    state.equals("AR")||state.equals("AZ")||state.equals("CA")||state.equals("CO")||
                    state.equals("CT")||state.equals("DE")||state.equals("FL")||state.equals("GA")||
                    state.equals("HI")||state.equals("IA")||state.equals("ID")||state.equals("IL")||
                    state.equals("IN")||state.equals("KS")||state.equals("KY")||state.equals("LA")||
                    state.equals("MA")||state.equals("MD")||state.equals("ME")||state.equals("MI")||
                    state.equals("MN")||state.equals("MO")||state.equals("MS")||state.equals("MT")||
                    state.equals("NC")||state.equals("ND")||state.equals("NE")||state.equals("NH")||
                    state.equals("NJ")||state.equals("NM")||state.equals("NV")||state.equals("NY")||
                    state.equals("OH")||state.equals("OK")||state.equals("OR")||state.equals("PA")||
                    state.equals("RI")||state.equals("SC")||state.equals("SD")||state.equals("TN")||
                    state.equals("TX")||state.equals("UT")||state.equals("VA")||state.equals("VT")||
                    state.equals("WA")||state.equals("WI")||state.equals("WV")||state.equals("WY"))){
                System.out.print("INVALID STATE.\n>> ");
                state=in.nextLine().toUpperCase().trim();
            }
            
            client.setState(state);
            
            /*--- Dependents Block ---*/
            System.out.print("Enter Number of Dependents:\n>> ");
            dependents=in.nextInt();
            client.setNumDependents(dependents);
            
            /*--- Filing Status Call ---*/
            getFilingStatus(client);
            
            clients.add(client);
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
    public void calculateTaxes() throws InterruptedException{
        Calculator calculator;
        Client found;
        String search;
        int foundIndex;
        
        if(clients.isEmpty())
            System.out.println("No Clients On Record.");
        else{
            System.out.println("\nEnter the SSN of the Client to Caculate Taxes:");
            search=validSSN();
            
            foundIndex=findClient(search);
            
            if(foundIndex!=-1){
                found=clients.get(foundIndex);
                calculator=new Calculator(found);
                
                calculator.calculateStateTax();
                calculator.calculateFederalTax();
                
                calculator.outputCalculations();
            }else
                System.out.println("Client Not Found.");

            stream.writeToSave(); // save and...
        }
                
        // calc.calculateFederalTaxes();
        // calc.calculateStateTaxes();
        // calc.outputCalculations();
        
        // save?
        menu();
    }
    
    /**
     * Prompts user for SSN of Client to edit -- if Client exists, user is
     * prompted to enter new information and the ArrayList is saved.
     */
    public void editClient() throws InterruptedException{
        Client found;
        String search,state;
        int foundIndex,dependents;
        double income;
        
        if(clients.isEmpty())
            System.out.println("No Clients On Record.");
        else{
            System.out.println("\nEnter the SSN of the Client to Edit:");
            search=validSSN();
            
            foundIndex=findClient(search);
            
            if(foundIndex!=-1){
                found=clients.get(foundIndex);
                
                System.out.print("Enter Taxable Income:\n>> $");
                income=in.nextDouble();
            
                while(income<0 || income>999999999){
                    System.out.print("INVALID INCOME.\n>> $");
                    income=in.nextDouble();
                }
            
                found.setIncome(income);
                
                in=new Scanner(System.in); // reset Scanner
                System.out.print("Enter State of Residence (XX):\n>> ");
                state=in.nextLine().toUpperCase().trim();

                while(!(state.equals("AK")||state.equals("AL")||state.equals("AK")||state.equals("AL")
                        ||state.equals("AR")||state.equals("AZ")||state.equals("CA")||state.equals("CO")
                        ||state.equals("CT")||state.equals("DE")||state.equals("FL")||state.equals("GA")
                        ||state.equals("HI")||state.equals("IA")||state.equals("ID")||state.equals("IL")
                        ||state.equals("IN")||state.equals("KS")||state.equals("KY")||state.equals("LA")
                        ||state.equals("MA")||state.equals("MD")||state.equals("ME")||state.equals("MI")
                        ||state.equals("MN")||state.equals("MO")||state.equals("MS")||state.equals("MT")
                        ||state.equals("NC")||state.equals("ND")||state.equals("NE")||state.equals("NH")
                        ||state.equals("NJ")||state.equals("NM")||state.equals("NV")||state.equals("NY")
                        ||state.equals("OH")||state.equals("OK")||state.equals("OR")||state.equals("PA")
                        ||state.equals("RI")||state.equals("SC")||state.equals("SD")||state.equals("TN")
                        ||state.equals("TX")||state.equals("UT")||state.equals("VA")||state.equals("VT")
                        ||state.equals("WA")||state.equals("WI")||state.equals("WV")||state.equals("WY"))){
                    System.out.print("INVALID STATE.\n>> ");
                    state=in.nextLine().toUpperCase().trim();
                }

                found.setState(state);

                System.out.print("Enter Number of Dependents:\n>> ");
                dependents=in.nextInt();
                found.setNumDependents(dependents);

                getFilingStatus(found);
                
                System.out.println("Client Edited.");

            }else
                System.out.println("Client Not Found.");

            stream.writeToSave(); // save and...
        }

        menu(); // return to menu
    }

    /**
     * Prompts user for SSN of Client to delete -- if Client exists, the Client
     * is removed from the ArrayList and the ArrayList is saved.
     */
    public void deleteClient() throws InterruptedException{
        String search; // SSN to be searched
        int foundIndex;

        if(clients.isEmpty()){
            System.out.println("No Clients On Record.");
        }else{
            System.out.println("\nEnter the SSN of the Client to Delete:");
            search=validSSN();

            foundIndex=findClient(search);

            if(foundIndex!=-1){
                clients.remove(foundIndex);
                System.out.println("Client Deleted.");
            }else{
                System.out.println("Client Not Found.");
            }

            stream.writeToSave(); // save and...
        }

        menu(); // return to menu
    }

    public void printClient() throws InterruptedException{
        Client found;
        String search;
        int foundIndex;

        if(clients.isEmpty()){
            System.out.println("No Clients On Record.");
        }else{
            System.out.println("\nEnter the SSN of the Client to Caculate Taxes:");
            search=validSSN();

            foundIndex=findClient(search);

            if(foundIndex!=-1){
                found=clients.get(foundIndex);
                found.printClientInfo();
            }else{
                System.out.println("Client Not Found.");
            }
        }

        menu(); // return to menu
    }

    /**
     * Outputs a print statement to notify the closing of the program -- the
     * thread is paused for 1000ms and System.exit is called within a try-catch
     * clause.
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

    public void menu() throws InterruptedException{
        in=new Scanner(System.in);
        String input;

        Thread.sleep(1000);
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
                    printClient();
                    break;
                    
                case "6":
                case "six":
                    exitSequence();
                    break;

                default:
                    System.out.println("INVALID SELECTION.");
            }
        }
    }

    /*--- HELPER METHODS ---*/
    
    public void getFilingStatus(Client client){
        in=new Scanner(System.in);
        String input;
        
        printFilingStatuses();
        
        System.out.print("\n>> ");
        input=in.nextLine().replaceAll("\\s+","");
        
        while(!input.matches("[1-4]")){
            System.out.print("INVALID SELECTION.\n>> ");
            input=in.nextLine().replaceAll("\\s+","");
        }
        
        switch(input){
            case "1":
                try{
                    client.setFederalTaxFile("singleRates(2018).txt");
                    client.setFilingStatus("Single");
                }catch(Exception e){
                    System.out.println("Error: File not found");
                }
                break;

            case "2":
                try{
                    client.setFederalTaxFile("headRates(2018).txt");
                    client.setFilingStatus("Head of House");
                }catch(Exception e){
                    System.out.println("Error: File not found");
                }
                break;

            case "3":
                try{
                    client.setFederalTaxFile("marriedSeparateRates(2018).txt");
                    client.setFilingStatus("Married, Separate");
                }catch(Exception e){
                    System.out.println("Error: File not found");
                }
                break;

            case "4":
                try{
                    client.setFederalTaxFile("marriedJointRates(2018).txt");
                    client.setFilingStatus("Married, Joint");
                }catch(Exception e){
                    System.out.println("Error: File not found");
                }
                break;
        }
    }

    /**
     * Searches the ArrayList of Clients for the Client SSN equal to the passed
     * parameter -- if found, the index of the Client is returned.
     *
     * @param SSN
     * @return
     */
    public int findClient(String SSN){
        for(Client c:clients){
            if(c.getSSN().equals(SSN)){
                return clients.indexOf(c);
            }
        }

        return -1;
    }

    /**
     * Get input from user via Scanner and match against the defined SSN_REGEX
     * -- returns a valid SSN.
     *
     * @return
     */
    public String validSSN(){
        in=new Scanner(System.in);

        System.out.print(">> ");
        String SSN=in.nextLine();

        while(!SSN.matches(SSN_REGEX)){
            System.out.print("INVALID SSN.\n>> ");
            SSN=in.nextLine();
        }

        return SSN;
    }

    /**
     * Searches the ArrayList of Clients for the Client SSN equal to the passed
     * parameter -- if found, the method returns true (for preventing duplicate
     * SSN entries).
     *
     * @param SSN
     * @return
     */
    public boolean isDuplicateSSN(String SSN){
        for(Client c:clients){
            if(c.getSSN().equals(SSN)){
                return true;
            }
        }

        return false;
    }

    public void printTitle(){
        System.out.println("==================\n= Tax Calculator ="
                +"\n==================");
    }

    public void printMenu(){
        System.out.println("\n Menu\n======\n1) create client\n2) do taxes"
                +"\n3) edit client \n4) delete client\n5) show client\n6) quit");
    }

    public void printDisclaimer(){
        System.out.println("DISCLAIMER: This tax calculator is for EDUCATIONAL\n"
                +"PURPOSES ONLY. DO NOT USE for calculating actual taxes.");
    }

    public void printFilingStatuses(){
        System.out.println("\n Filing Status\n===============\n1) single"
                +"\n2) head of household\n3) married (separate)"
                +"\n4) married (joint)");
    }

    /*--- END HELPER METHODS ---*/
    /**
     * Calculator Class Calculates Federal and State Taxes for Client.
     */
    private class Calculator{

        private final String STATETAX_FILE="stateTax.txt";

        private Client client;
        private String federalTaxFile;
        private double federalTax;
        private double stateTax;
        private double deductions;
        private double federalRate;
        private double stateRate;

        public Calculator(Client client){
            this.client=client;

            federalTaxFile=this.client.getFederalTaxFile();
        }

        public void calculateFederalTax(){
            String line;
            double percentage=0;

            try{
                Scanner read=new Scanner(new File(federalTaxFile));
                line=read.nextLine();
                
                while(line!=null){
                    percentage=parseStringFederal(line);
                    
                    if(percentage!=-1)
                        break;
                    
                    line=read.nextLine();
                }                
                
                deductions=client.getNumDependents()*300;
                federalTax=(client.getIncome()*(federalRate=percentage))-deductions;
                
            }catch(IOException e){
                System.out.println(e);
            }
        }
        
        public void calculateStateTax(){
            String line;
            String state=client.getState();
            
            try{
                Scanner read=new Scanner(new File(STATETAX_FILE));
                line=read.nextLine();
                String cont=line.substring(0,2);
                
                while(!(cont.equals(state.toUpperCase()))){
                    line=read.nextLine();
                    cont=line.substring(0,2);
                }
                
                stateTax=client.getIncome()*(stateRate=parseStringState(line));
                
            }catch(IOException e){
                System.out.println(e);
            }
        }
        
        public void outputCalculations(){
            System.out.println("\n===============================");
            System.out.println(" Taxable Income: $"+client.getIncome());
            System.out.println("\tDeductions: $"+deductions);
            System.out.println("\tFederal Rate: "+federalRate);
            System.out.println("\tState Rate: "+stateRate+"\n");
            System.out.println(" Federal Taxes Owed: $"+federalTax);
            System.out.println(" State Taxes Owed: $"+stateTax);
            System.out.println("===============================");
        }

        
        /**
         * Takes a line from text file and splits it at each hyphen into an
         * array of Strings -- the third substring (index==2) is parsed and
         * saved to the Integer variable percentage for use in State tax
         * calculations.
         *
         * @param str
         * @return
         */
        public double parseStringState(String str){
            String target;
            double percentage;

            String[] split=str.split("-"); // split at hyphen
            target=split[2]; // percentage always located at index==2

            return percentage=Double.parseDouble(target)/100;
        }
        
        /**
         * Takes a line from text file and splits it at each hyphen into an
         * array of Strings -- the first and second substrings are parsed to 
         * represent the income range, and the third substring of the matching
         * range is parsed and saved to the Integer variable percentage for use
         * in Federal tax calculations.
         * @param str
         * @return 
         */
        public double parseStringFederal(String str){
            String min,max,target;
            int minIncome,maxIncome;
            double income,percentage;
            
            String[] split=str.split("-");
            target=split[2];
            
            minIncome=Integer.parseInt(split[0]);
            maxIncome=Integer.parseInt(split[1]);
            
            income=client.getIncome();
            
            if(income>=minIncome && income<maxIncome)
                return percentage=Double.parseDouble(target)/100;
            
            return -1;
        }
    }
    
    /**
     * Stream Class
     * Reads Serialized ArrayList from external .txt file -- Writes ArrayList
     * to external .txt file.
     */
    private class Stream{
        
        private final String SAVE_FILE="taxCalc_save.txt";
        
        public Stream(){}
        
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
