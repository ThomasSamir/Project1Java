package Services;

import Models.InvoiceItemModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceItemService {

    private List<InvoiceItemModel> listItemModels;
    private File invoiceItemFile;
    private BufferedReader invoiceItemReader;
    private BufferedWriter invoiceItemWriter;

    public InvoiceItemService() throws FileNotFoundException, IOException {
        listItemModels = new ArrayList<InvoiceItemModel>();
        String fileName = "C:\\Users\\Thomas Samir\\Downloads\\GuiDemo_1\\src\\Files\\InvoiceItemFile.txt";
        invoiceItemFile = new File(fileName);
        
        invoiceItemWriter = new BufferedWriter(new FileWriter(fileName, true));
        loadAllInvoiceItems();
    }

    public List<InvoiceItemModel> getListInvoiceItem(String invoiceId) throws IOException {

        List<InvoiceItemModel> resultList = new ArrayList<InvoiceItemModel>();
        for (InvoiceItemModel item : listItemModels) {
            if (item.getId().equals(invoiceId)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    private void loadAllInvoiceItems() throws IOException {
        invoiceItemReader = new BufferedReader(new FileReader(invoiceItemFile));
        String line;
        while ((line = invoiceItemReader.readLine()) != null) {
            String ar[] = line.split("%");
            InvoiceItemModel model = new InvoiceItemModel();
            model.setId(ar[0]);
            model.setInvoiceId(ar[1]);
            model.setItemName(ar[2]);
            model.setCount(Double.parseDouble(ar[3]));
            model.setItemPrice(Double.parseDouble(ar[4]));
            model.setItemTotal(Double.parseDouble(ar[5]));
            listItemModels.add(model);
        }
    }

    public void saveInvoiceITem(List<InvoiceItemModel> models) throws IOException {
        invoiceItemWriter = new BufferedWriter(new FileWriter(invoiceItemFile));
        listItemModels = new ArrayList<>();
        String buffer = "";
        for (InvoiceItemModel model : models) {
            String line = model.getId() + "%" + model.getInvoiceId()
                    + "%" + model.getItemName() + "%" + model.getCount()
                    + "%" + model.getItemPrice() + "%" + model.getItemTotal() + "\n";
            buffer += line;
        }
        try {
            invoiceItemWriter.write(buffer);
            invoiceItemWriter.close();
        } catch (IOException ex) {
            System.out.println("Error while save invoice item");
        }
    }
}
