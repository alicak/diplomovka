/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.upjs.ics.diplomovka.ui.windows;

import sk.upjs.ics.diplomovka.data.models.view.AssignmentStatistics;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.ui.Main;
import sk.upjs.ics.diplomovka.ui.models.DisruptionListModel;
import sk.upjs.ics.diplomovka.ui.models.FlightTableModel;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MainFrame extends javax.swing.JFrame {

    private FlightTableModel flightTableModel = new FlightTableModel(Collections.emptyList());
    private DisruptionListModel disruptionListModel = new DisruptionListModel(Collections.emptyList());
    private Main main;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        main = new Main();
        main.applyDisruptions();
        refreshDisruptions(main.getDisruptions());
        refreshAssignment(main.getFlights());
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        assignmentTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        disruptionsList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        newAssignmentButton = new javax.swing.JButton();
        lastReassignmentTimeLabel = new javax.swing.JLabel();
        regularDelayCountLabel = new javax.swing.JLabel();
        regularDelayMaxLabel = new javax.swing.JLabel();
        regularDelayAverageLabel = new javax.swing.JLabel();
        assignmentDelayCountLabel = new javax.swing.JLabel();
        assignmentDelayMaxLabel = new javax.swing.JLabel();
        assignmentDelayAverageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reassignment creator");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel1.setText("Disruptions");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel2.setText("Assignment");

        assignmentTable.setModel(flightTableModel);
        jScrollPane1.setViewportView(assignmentTable);

        disruptionsList.setModel(disruptionListModel);
        jScrollPane2.setViewportView(disruptionsList);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel4.setText("Assignment delays");

        jLabel5.setText("Count:");

        jLabel6.setText("Max:");

        jLabel7.setText("Average:");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel8.setText("Regular delays");

        jLabel9.setText("Count:");

        jLabel10.setText("Max:");

        jLabel11.setText("Average:");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel12.setText("Last reassignment");

        newAssignmentButton.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        newAssignmentButton.setText("New assignment");
        newAssignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAssignmentButtonActionPerformed(evt);
            }
        });

        lastReassignmentTimeLabel.setText("n/a");

        regularDelayCountLabel.setText("n/a");

        regularDelayMaxLabel.setText("n/a");

        regularDelayAverageLabel.setText("n/a");

        assignmentDelayCountLabel.setText("n/a");

        assignmentDelayMaxLabel.setText("n/a");

        assignmentDelayAverageLabel.setText("n/a");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(46, Short.MAX_VALUE)
                                .addComponent(newAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(regularDelayCountLabel)
                                                        .addComponent(regularDelayMaxLabel)
                                                        .addComponent(regularDelayAverageLabel)))
                                        .addComponent(jLabel4)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11)
                                                        .addComponent(jLabel9))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(assignmentDelayCountLabel)
                                                        .addComponent(assignmentDelayMaxLabel)
                                                        .addComponent(assignmentDelayAverageLabel)))
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel8)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(lastReassignmentTimeLabel)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(regularDelayCountLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(regularDelayMaxLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(regularDelayAverageLabel))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(assignmentDelayCountLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(assignmentDelayMaxLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(assignmentDelayAverageLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lastReassignmentTimeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(newAssignmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(0, 468, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newAssignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAssignmentButtonActionPerformed
        NewAssignmentDialog newAssignmentDialog = new NewAssignmentDialog(this, true);
        newAssignmentDialog.setVisible(true);
    }//GEN-LAST:event_newAssignmentButtonActionPerformed

    public void refreshAssignment(List<FlightViewModel> flights) {
        refreshFlights(flights);
        setStatistics(main.calculateAssignmentStatistics(flights));
        setCurrentTime();
    }

    private void refreshFlights(List<FlightViewModel> flights) {
        Collections.sort(flights);
        flightTableModel.setData(flights);
    }

    private void setCurrentTime() {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        lastReassignmentTimeLabel.setText(sdf.format(new Date()));
    }

    private void setStatistics(AssignmentStatistics statistics) {
        regularDelayMaxLabel.setText(Integer.toString(statistics.getRegularDelayMax()));
        regularDelayCountLabel.setText(Integer.toString(statistics.getRegularDelayCount()));
        regularDelayAverageLabel.setText(Integer.toString(statistics.getRegularDelayAverage()));

        assignmentDelayMaxLabel.setText(Integer.toString(statistics.getAssignmentDelayMax()));
        assignmentDelayCountLabel.setText(Integer.toString(statistics.getAssignmentDelayCount()));
        assignmentDelayAverageLabel.setText(Integer.toString(statistics.getAssignmentDelayAverage()));
    }

    public void refreshDisruptions(List<Disruption> disruptions) {
        disruptionListModel.setData(disruptions);
    }

    public void calculateNewAssignment(ReassignmentParameters parameters) {
        InProgressDialog inProgressDialog = new InProgressDialog(this, true);

        parameters.setStartTime(main.getStartTime());
        CalculationWorker calculationWorker = new CalculationWorker(this, parameters, inProgressDialog);
        calculationWorker.execute();

        inProgressDialog.setVisible(true);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assignmentDelayAverageLabel;
    private javax.swing.JLabel assignmentDelayCountLabel;
    private javax.swing.JLabel assignmentDelayMaxLabel;
    private javax.swing.JTable assignmentTable;
    private javax.swing.JList<String> disruptionsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel lastReassignmentTimeLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton newAssignmentButton;
    private javax.swing.JLabel regularDelayAverageLabel;
    private javax.swing.JLabel regularDelayCountLabel;
    private javax.swing.JLabel regularDelayMaxLabel;
    // End of variables declaration//GEN-END:variables

    private class CalculationWorker extends SwingWorker<List<FlightViewModel>, Integer> {
        private MainFrame parentFrame;
        private InProgressDialog inProgressDialog;
        private ReassignmentParameters parameters;

        public CalculationWorker(MainFrame parentFrame, ReassignmentParameters parameters, InProgressDialog inProgressDialog) {
            this.parentFrame = parentFrame;
            this.parameters = parameters;
            this.inProgressDialog = inProgressDialog;
        }

        protected List<FlightViewModel> doInBackground() {
            return main.calculateNewAssignment(parameters);
        }

        protected void done() {
            inProgressDialog.dispose();

            try {
                ReassignmentFinishedDialog reassignmentFinishedDialog = new ReassignmentFinishedDialog(parentFrame,
                        true, main.getReassignmentStatistics(), get());
                reassignmentFinishedDialog.setVisible(true);
                main = new Main(); // we reset data
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
