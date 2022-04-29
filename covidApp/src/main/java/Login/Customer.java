/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import Login.AbstractUser;
import com.mycompany.covidapp.UserOption;

/**
 *
 * @author ASUS
 */
public class Customer extends AbstractUser{
    
    /**
     * Class for customer
     */

    
    public Customer() {
        
        super();
        
        
    }
    @Override
    public void display() {
        UserOption userOption=new UserOption();
        userOption.setCustomer(this);
        userOption.setVisible(true);
    }
    
}
