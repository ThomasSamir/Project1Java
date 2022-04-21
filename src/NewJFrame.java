
import Models.InvoiceItemModel;
import Models.InvoiceModel;
import Services.InvoiceItemService;
import Services.InvoiceService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class NewJFrame extends javax.swing.JFrame {

    private InvoiceService invoiceService;
    private InvoiceItemService invoiceItemService;

    private List<InvoiceModel> listInvoices;
    private List<InvoiceItemModel> listInvoiceItems;

    private DefaultTableModel tempDataTableInvoiceModel;
    private DefaultTableModel tempDataTableInvoiceItemsModel;

    private int currentSelectedRow = -1;

    public NewJFrame() throws IOException {
        initComponents();

        invoiceService = new InvoiceService();
        invoiceItemService = new InvoiceItemService();
        listInvoices = new ArrayList<>();
        listInvoiceItems = new ArrayList<>();
        listInvoices = invoiceService.getListInvoiceModels();
        tempDataTableInvoiceModel = (DefaultTableModel) invoiceTable.getModel();
        tempDataTableInvoiceItemsModel = (DefaultTableModel) itemTable.getModel();

        DefaultTableModel dtmodel = (DefaultTableModel) invoiceTable.getModel();
        dtmodel.setRowCount(0);
        for (InvoiceModel item : listInvoices) {
            String dataRow[]
                    = {
                        item.getId() + "",
                        item.getDate(),
                        item.getCutomer(),
                        item.getTotal() + ""
                    };

            dtmodel.addRow(dataRow);
        }
        dtmodel.setRowCount(listInvoices.size() + 1);

        invoiceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "No selection");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    currentSelectedRow = selectedRow;
                    InvoiceModel model = listInvoices.get(selectedRow);
                    lbl_CustomerName.setText(model.getCutomer());
                    lbl_InvoiceNumber.setText(model.getId() + "");
                    lbl_InvoiceTotal.setText(model.getTotal() + "");
                    lbl_InvoiceDate.setText(model.getDate());
                    try {

                        listInvoiceItems = invoiceItemService.getListInvoiceItem(model.getId());
                        listInvoices.get(selectedRow).setListItems(listInvoiceItems);
                        DefaultTableModel tableModel = (DefaultTableModel) itemTable.getModel();
                        tableModel.setRowCount(0);
                        for (InvoiceItemModel item : listInvoiceItems) {
                            String dataRow[]
                                    = {
                                        item.getId() + "",
                                        item.getItemName(),
                                        item.getCount() + "",
                                        item.getItemPrice() + "",
                                        item.getItemTotal() + ""
                                    };

                            tableModel.addRow(dataRow);
                        }
                        tableModel.setRowCount(listInvoiceItems.size() + 1);
                    } catch (IOException ex) {
                        Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
    }

    private void reset() {
        lbl_CustomerName.setText("");
        lbl_InvoiceDate.setText("");
        lbl_InvoiceNumber.setText("");
        lbl_InvoiceTotal.setText("");
        currentSelectedRow = -1;
    }

    private void saveChanges() throws IOException {
        invoiceService.saveInvoiceITem(listInvoices);
        for (InvoiceModel m : listInvoices) {
            invoiceItemService.saveInvoiceITem(m.getListItems());
        }
    }

    private void saveItems() {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        int rowCount = model.getRowCount();
        List<InvoiceItemModel> tempList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            InvoiceItemModel item = new InvoiceItemModel();
            item.setId(itemTable.getModel().getValueAt(i, 0).toString());
            item.setInvoiceId(listInvoices.get(currentSelectedRow).getId());
            item.setItemName(itemTable.getModel().getValueAt(i, 1).toString());
            item.setItemPrice(Double.parseDouble(itemTable.getModel().getValueAt(i, 2).toString()));
            item.setCount(Double.parseDouble(itemTable.getModel().getValueAt(i, 3).toString()));
            item.setItemTotal(Double.parseDouble(itemTable.getModel().getValueAt(i, 4).toString()));
            tempList.add(item);
        }
        listInvoices.get(currentSelectedRow).setListItems(tempList);
        listInvoiceItems = tempList;
    }

    private void saveInvoices() {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        int rowCount = model.getRowCount();
        List<InvoiceModel> tempList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            InvoiceModel item = new InvoiceModel();
            item.setListItems(listInvoices.get(i).getListItems());
            item.setId(invoiceTable.getValueAt(i, 0).toString());
            item.setDate(invoiceTable.getValueAt(i, 1).toString());
            item.setCutomer(invoiceTable.getValueAt(i, 2).toString());
            item.setTotal(Double.parseDouble(invoiceTable.getValueAt(i, 3).toString()));
            tempList.add(item);
        }

        listInvoices = tempList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        lbl_InvoiceNumber = new java.awt.Label();
        lbl_InvoiceDate = new java.awt.TextField();
        lbl_CustomerName = new java.awt.TextField();
        lbl_InvoiceTotal = new java.awt.Label();
        label7 = new java.awt.Label();
        jPanel3 = new javax.swing.JPanel();
        btn_cancel = new javax.swing.JButton();
        btn_saveItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        invoiceTable = new javax.swing.JTable();
        btn_loadFiles = new javax.swing.JButton();
        btn_save_File = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btn_saveInvoice = new javax.swing.JButton();
        btn_deleteInvoice = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Invoice Number");

        label2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("Invoice Date\n");

        label3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Customer Name");

        label4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("Invoice Total");

        lbl_InvoiceNumber.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl_InvoiceNumber.setForeground(new java.awt.Color(255, 255, 255));
        lbl_InvoiceNumber.setText("Num");

        lbl_InvoiceDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_InvoiceDateActionPerformed(evt);
            }
        });

        lbl_CustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_CustomerNameActionPerformed(evt);
            }
        });

        lbl_InvoiceTotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl_InvoiceTotal.setForeground(new java.awt.Color(255, 255, 255));
        lbl_InvoiceTotal.setText("Num");

        label7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label7.setForeground(new java.awt.Color(255, 255, 255));
        label7.setText("Invoice Items");

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        btn_cancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_saveItem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_saveItem.setText("Save");
        btn_saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(btn_saveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cancel)
                .addGap(90, 90, 90))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_saveItem))
                .addGap(26, 26, 26))
        );

        itemTable.setBackground(new java.awt.Color(255, 255, 204));
        itemTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Items Name", "Items Price", "Count", "Items Total"
            }
        ));
        itemTable.setColumnSelectionAllowed(true);
        itemTable.setGridColor(new java.awt.Color(0, 153, 51));
        itemTable.setShowGrid(true);
        jScrollPane1.setViewportView(itemTable);
        itemTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(label3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_InvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_InvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_CustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(lbl_InvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_InvoiceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_InvoiceDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_CustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_InvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setForeground(new java.awt.Color(0, 153, 51));

        invoiceTable.setBackground(new java.awt.Color(255, 255, 204));
        invoiceTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        invoiceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "No.", "Date", "Customer", "Total"
            }
        ));
        invoiceTable.setGridColor(new java.awt.Color(102, 255, 102));
        invoiceTable.setSelectionBackground(new java.awt.Color(102, 255, 51));
        invoiceTable.setShowGrid(true);
        jScrollPane2.setViewportView(invoiceTable);

        btn_loadFiles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_loadFiles.setText("Load file");
        btn_loadFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loadFilesActionPerformed(evt);
            }
        });

        btn_save_File.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_save_File.setText("Save file");
        btn_save_File.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_FileActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        btn_saveInvoice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_saveInvoice.setText("Create new invoice");
        btn_saveInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveInvoiceActionPerformed(evt);
            }
        });

        btn_deleteInvoice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_deleteInvoice.setText("Delete invoice");
        btn_deleteInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(btn_saveInvoice)
                .addGap(76, 76, 76)
                .addComponent(btn_deleteInvoice)
                .addGap(49, 49, 49))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(376, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_deleteInvoice)
                    .addComponent(btn_saveInvoice))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_loadFiles, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_save_File, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btn_loadFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save_File)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(575, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_InvoiceDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_InvoiceDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_InvoiceDateActionPerformed

    private void lbl_CustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_CustomerNameActionPerformed
        try {
            // TODO add your handling code here:
            invoiceItemService.saveInvoiceITem(listInvoiceItems);
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lbl_CustomerNameActionPerformed

    private void btn_saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveItemActionPerformed
        // TODO add your handling code here:
        saveItems();
    }//GEN-LAST:event_btn_saveItemActionPerformed

    private void btn_loadFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadFilesActionPerformed

        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.setRowCount(0);
        for (InvoiceModel item : listInvoices) {
            String dataRow[]
                    = {
                        item.getId() + "",
                        item.getDate(),
                        item.getCutomer(),
                        item.getTotal() + ""
                    };

            model.addRow(dataRow);
        }
        model.setRowCount(listInvoices.size() + 1);
        reset();
    }//GEN-LAST:event_btn_loadFilesActionPerformed

    private void btn_save_FileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_FileActionPerformed
        try {
            // TODO add your handling code here:
            saveItems();
            saveInvoices();
            saveChanges();
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset();
    }//GEN-LAST:event_btn_save_FileActionPerformed

    private void btn_saveInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveInvoiceActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        int lastRow = model.getRowCount() - 1;
        String newId = invoiceTable.getValueAt(lastRow, 0).toString();
        String newDate = invoiceTable.getValueAt(lastRow, 1).toString();
        String newCustomer = invoiceTable.getValueAt(lastRow, 2).toString();
        String newTotal = invoiceTable.getValueAt(lastRow, 3).toString();
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setCutomer(newCustomer);
        invoiceModel.setDate(newDate);
        invoiceModel.setId(newId);
        invoiceModel.setTotal(Double.parseDouble(newTotal));
        listInvoices.add(invoiceModel);
        model.setRowCount(listInvoices.size() + 1);
        reset();
    }//GEN-LAST:event_btn_saveInvoiceActionPerformed

    private void btn_deleteInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteInvoiceActionPerformed
        // TODO add your handling code here:
        if (currentSelectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No selection");
            return;
        }

        String deletedLine = listInvoices.get(currentSelectedRow).getId() + "%"
                + listInvoices.get(currentSelectedRow).getDate() + "%"
                + listInvoices.get(currentSelectedRow).getCutomer() + "%"
                + listInvoices.get(currentSelectedRow).getTotal() + "\n";
        listInvoices.remove(currentSelectedRow);

        DefaultTableModel tm = (DefaultTableModel) invoiceTable.getModel();
        tm.removeRow(currentSelectedRow);
        DefaultTableModel ttmm = (DefaultTableModel) itemTable.getModel();
        ttmm.setRowCount(0);
        try {

            boolean result = invoiceService.deleteInvoice(deletedLine);
            if (result) {
                JOptionPane.showMessageDialog(null, "deleted");
            } else {
                JOptionPane.showMessageDialog(null, "delete error");
            }
        } catch (IOException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset();
    }//GEN-LAST:event_btn_deleteInvoiceActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) itemTable.getModel();
        tableModel.setRowCount(0);
        for (InvoiceItemModel item : listInvoiceItems) {
            String dataRow[]
                    = {
                        item.getId() + "",
                        item.getItemName(),
                        item.getCount() + "",
                        item.getItemPrice() + "",
                        item.getItemTotal() + ""
                    };

            tableModel.addRow(dataRow);
        }
        tableModel.setRowCount(listInvoiceItems.size() + 1);
    }//GEN-LAST:event_btn_cancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws FileNotFoundException, IOException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewJFrame().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_deleteInvoice;
    private javax.swing.JButton btn_loadFiles;
    private javax.swing.JButton btn_saveInvoice;
    private javax.swing.JButton btn_saveItem;
    private javax.swing.JButton btn_save_File;
    private javax.swing.JTable invoiceTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label7;
    private java.awt.TextField lbl_CustomerName;
    private java.awt.TextField lbl_InvoiceDate;
    private java.awt.Label lbl_InvoiceNumber;
    private java.awt.Label lbl_InvoiceTotal;
    // End of variables declaration//GEN-END:variables
}
