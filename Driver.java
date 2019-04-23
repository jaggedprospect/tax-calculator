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
    
    public static void main(String[] args){
        /*
        JFrame frame=new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        frame.getContentPane().add(new TaxCalculatorPanel());
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
        
        TaxMain taxmain=new TaxMain();
    }
}
