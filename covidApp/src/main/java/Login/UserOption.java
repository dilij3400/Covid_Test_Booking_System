/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import Login.Customer;
import Application.BookingTestingOnlinePage;
import Application.SearchTestingSiteView;
import Application.VerifyBookingStatusUser;
import Application.OnlineOnSiteTestingBooking;
import Application.UserActiveBooking;
import MainClass.ModifyBooking;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooyewlim
 */
public class UserOption extends javax.swing.JFrame {
    
    Customer customer;

    /**
     * Creates new form userOption
     */
    public UserOption() {
        initComponents();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchButton = new javax.swing.JButton();
        bookHomeTestingOnline = new javax.swing.JButton();
        verifyBookingStatus = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        onSiteBookingButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchButton.setText("Search Testing Site ");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        bookHomeTestingOnline.setLabel("Book Home Testing");
        bookHomeTestingOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookHomeTestingOnlineActionPerformed(evt);
            }
        });

        verifyBookingStatus.setLabel("Verify Booking Status");
        verifyBookingStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyBookingStatusActionPerformed(evt);
            }
        });

        jLabel1.setText("Login As User");

        onSiteBookingButton.setText("Book Onsite Testing");
        onSiteBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSiteBookingButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Modify Testing");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(onSiteBookingButton)
                            .addComponent(searchButton))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bookHomeTestingOnline, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addComponent(verifyBookingStatus)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(bookHomeTestingOnline)
                    .addComponent(verifyBookingStatus))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onSiteBookingButton)
                    .addComponent(jButton1))
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        new SearchTestingSiteView().setVisible(true);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void bookHomeTestingOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookHomeTestingOnlineActionPerformed
        
        BookingTestingOnlinePage bookingOnlinePage = new BookingTestingOnlinePage();
        bookingOnlinePage.setCustomer(customer);
        bookingOnlinePage.setVisible(true);
    }//GEN-LAST:event_bookHomeTestingOnlineActionPerformed

    private void verifyBookingStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyBookingStatusActionPerformed
        UserActiveBooking verifyBookingStatusUser=new UserActiveBooking();
        try {
            verifyBookingStatusUser.setCustomer(customer);
        } catch (IOException ex) {
            Logger.getLogger(UserOption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserOption.class.getName()).log(Level.SEVERE, null, ex);
        }
        verifyBookingStatusUser.setVisible(true);
    }//GEN-LAST:event_verifyBookingStatusActionPerformed

    private void onSiteBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSiteBookingButtonActionPerformed
        new OnlineOnSiteTestingBooking().setVisible(true);
    }//GEN-LAST:event_onSiteBookingButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            ModifyBooking.main();
        } catch (Exception ex) {
            Logger.getLogger(UserOption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserOption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new UserOption().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookHomeTestingOnline;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton onSiteBookingButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton verifyBookingStatus;
    // End of variables declaration//GEN-END:variables
}
