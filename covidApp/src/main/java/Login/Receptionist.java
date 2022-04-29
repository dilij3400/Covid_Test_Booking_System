/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import Login.AbstractUser;
import com.mycompany.covidapp.AdminOption;

/**
 *
 * @author ASUS
 */
public class Receptionist extends AbstractUser {
    
    /**
     * Class for Receptionist
     */
    
    public Receptionist() {
        
        super();
        
       
    }
    @Override
    public void display() {
        AdminOption adminPage = new AdminOption();
        adminPage.setVisible(true);
    }
    
}
