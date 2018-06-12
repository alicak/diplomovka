/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.upjs.ics.diplomovka.ui.windows;

import sk.upjs.ics.diplomovka.data.flights.FlightInfo;

import java.util.Collections;
import java.util.List;

public class NewAssignmentDialog extends javax.swing.JDialog {

    private MainFrame parent;

    /**
     * Creates new form NewAssignmentDialog
     */
    public NewAssignmentDialog(MainFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        optimizeTimeCheckBox = new javax.swing.JCheckBox();
        optimizeReassignmentsCheckBox = new javax.swing.JCheckBox();
        optimizeWalkingCheckBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        considerPassengersCheckBox = new javax.swing.JCheckBox();
        considerPriorityCheckBox = new javax.swing.JCheckBox();
        calculateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        timeWeightTextField = new javax.swing.JFormattedTextField();
        reassignmentsWeightTextField = new javax.swing.JFormattedTextField();
        walkingWeightTextField = new javax.swing.JFormattedTextField();
        passengersWeightTextField = new javax.swing.JFormattedTextField();
        priorityWeightTextField = new javax.swing.JFormattedTextField();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New assignment");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel1.setText("Optimize for:");

        optimizeTimeCheckBox.setText("time");
        optimizeTimeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimizeTimeCheckBoxActionPerformed(evt);
            }
        });

        optimizeReassignmentsCheckBox.setText("reassignments");
        optimizeReassignmentsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimizeReassignmentsCheckBoxActionPerformed(evt);
            }
        });

        optimizeWalkingCheckBox.setText("walking distance");
        optimizeWalkingCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimizeWalkingCheckBoxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setText("Consider:");

        considerPassengersCheckBox.setText("no. of passengers");
        considerPassengersCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                considerPassengersCheckBoxActionPerformed(evt);
            }
        });

        considerPriorityCheckBox.setText("flight priority");
        considerPriorityCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                considerPriorityCheckBoxActionPerformed(evt);
            }
        });

        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("weight:");

        jLabel4.setText("weight:");

        jLabel5.setText("weight:");

        jLabel7.setText("weight:");

        jLabel8.setText("weight:");

        timeWeightTextField.setText("1.0");
        timeWeightTextField.setEnabled(false);

        reassignmentsWeightTextField.setText("1.0");
        reassignmentsWeightTextField.setEnabled(false);

        walkingWeightTextField.setText("1.0");
        walkingWeightTextField.setEnabled(false);

        passengersWeightTextField.setText("1.0");
        passengersWeightTextField.setEnabled(false);

        priorityWeightTextField.setText("1.0");
        priorityWeightTextField.setEnabled(false);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(optimizeTimeCheckBox)
                                                        .addComponent(considerPriorityCheckBox)
                                                        .addComponent(considerPassengersCheckBox)
                                                        .addComponent(optimizeWalkingCheckBox)
                                                        .addComponent(optimizeReassignmentsCheckBox)
                                                        .addComponent(calculateButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(34, 34, 34)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel8)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(priorityWeightTextField))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel7)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(passengersWeightTextField))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel5)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(walkingWeightTextField))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel4)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(reassignmentsWeightTextField))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(timeWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(30, 30, 30)
                                                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(optimizeTimeCheckBox)
                                        .addComponent(jLabel3)
                                        .addComponent(timeWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(optimizeReassignmentsCheckBox)
                                        .addComponent(jLabel4)
                                        .addComponent(reassignmentsWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(optimizeWalkingCheckBox)
                                        .addComponent(jLabel5)
                                        .addComponent(walkingWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(considerPassengersCheckBox)
                                        .addComponent(jLabel7)
                                        .addComponent(passengersWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(considerPriorityCheckBox)
                                        .addComponent(jLabel8)
                                        .addComponent(priorityWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(calculateButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void setTextBoxEnable(javax.swing.JCheckBox checkBox, javax.swing.JFormattedTextField textBox) {
        if (checkBox.isSelected()) {
            textBox.setEnabled(true);
        } else {
            textBox.setEnabled(false);
        }
    }

    private void optimizeTimeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimizeTimeCheckBoxActionPerformed
        setTextBoxEnable(optimizeTimeCheckBox, timeWeightTextField);
    }

    private void optimizeReassignmentsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimizeReassignmentsCheckBoxActionPerformed
        setTextBoxEnable(optimizeReassignmentsCheckBox, reassignmentsWeightTextField);
    }

    private void optimizeWalkingCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimizeWalkingCheckBoxActionPerformed
        setTextBoxEnable(optimizeWalkingCheckBox, walkingWeightTextField);
    }

    private void considerPassengersCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_considerPassengersCheckBoxActionPerformed
        setTextBoxEnable(considerPassengersCheckBox, passengersWeightTextField);
    }

    private void considerPriorityCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_considerPriorityCheckBoxActionPerformed
        setTextBoxEnable(considerPriorityCheckBox, priorityWeightTextField);
    }

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        // TODO: add calculation
        List<FlightInfo> result = Collections.emptyList();
        dispose();

        ReassignmentFinishedDialog reassignmentFinishedDialog = new ReassignmentFinishedDialog(parent, true, result);
        reassignmentFinishedDialog.setVisible(true);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewAssignmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewAssignmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewAssignmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewAssignmentDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewAssignmentDialog dialog = new NewAssignmentDialog(new MainFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox considerPassengersCheckBox;
    private javax.swing.JCheckBox considerPriorityCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JCheckBox optimizeReassignmentsCheckBox;
    private javax.swing.JCheckBox optimizeTimeCheckBox;
    private javax.swing.JCheckBox optimizeWalkingCheckBox;
    private javax.swing.JFormattedTextField passengersWeightTextField;
    private javax.swing.JFormattedTextField priorityWeightTextField;
    private javax.swing.JFormattedTextField reassignmentsWeightTextField;
    private javax.swing.JFormattedTextField timeWeightTextField;
    private javax.swing.JFormattedTextField walkingWeightTextField;
    // End of variables declaration//GEN-END:variables
}
