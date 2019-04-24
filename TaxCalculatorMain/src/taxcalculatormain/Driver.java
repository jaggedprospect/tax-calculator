package taxcalculatormain;

/**
 * Driver Class for Tax Calculator Program
 *
 * @author Zachary Combs
 * @author Oghenero Ekwevugbe
 * @author Nate Heppard
 * @author Michael Kellam
 * @author Omolola Solaru
 * @version 1.0
 */

import javax.swing.*;

public class Driver extends JFrame{
    
    public static void main(String[] args) throws InterruptedException{
        TaxMain taxmain=new TaxMain();
        taxmain.run();  // start the program
    }
}
