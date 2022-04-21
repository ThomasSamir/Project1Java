package Services;

import Models.InvoiceItemModel;
import Models.InvoiceModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private List<InvoiceModel> listInvoiceModels;

    public List<InvoiceModel> getListInvoiceModels() {
        return listInvoiceModels;
    }
    private File invoiceFile;
    private File tempInvoiceFile;

    private BufferedReader invoiceReader, tempInvoiceReader;
    private BufferedWriter invoiceWriter, tempInvoiceWriter;

    String tempFileName, fileName;

    public InvoiceService() throws FileNotFoundException, IOException {
        listInvoiceModels = new ArrayList<InvoiceModel>();
        fileName = "C:\\Users\\thomas\\Downloads\\GuiDemo_1\\src\\Files\\InvoiceFile.txt";
        tempFileName = "C:\\Users\\thomas\\Downloads\\GuiDemo_1\\src\\Files\\TempInvoiceFile.txt";
        invoiceFile = new File(fileName);

        invoiceWriter = new BufferedWriter(new FileWriter(invoiceFile.getPath(), true));
        loadAllInvoices();
    }

    private void loadAllInvoices() throws IOException {
        invoiceReader = new BufferedReader(new FileReader(invoiceFile.getPath()));
        String line;
        while ((line = invoiceReader.readLine()) != null) {
            String ar[] = line.split("%");
            InvoiceModel model = new InvoiceModel();
            model.setId(ar[0]);
            model.setDate(ar[1]);
            model.setCutomer(ar[2]);
            model.setTotal(Double.parseDouble(ar[3]));
            listInvoiceModels.add(model);
        }
        invoiceReader.close();
    }

    public boolean deleteInvoice(String lineToRemove) throws IOException {
        tempInvoiceFile = new File(tempFileName);
        tempInvoiceWriter = new BufferedWriter(new FileWriter(tempInvoiceFile.getPath(), true));
       
        invoiceReader = new BufferedReader(new FileReader(invoiceFile.getPath()));

        String currentLine;
        lineToRemove = lineToRemove.trim();
        while ((currentLine = invoiceReader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove)) {
                continue;
            }
            tempInvoiceWriter.write(trimmedLine);
            tempInvoiceWriter.newLine();
        }

        invoiceFile = new File(fileName);
        invoiceWriter = new BufferedWriter(new FileWriter(invoiceFile.getPath()));
        tempInvoiceReader = new BufferedReader(new FileReader(tempInvoiceFile.getPath()));
        currentLine = null;
        while ((currentLine = tempInvoiceReader.readLine()) != null) {
            invoiceWriter.write(currentLine);
            invoiceWriter.newLine();
        }
        invoiceWriter.close();
        invoiceReader.close();
        tempInvoiceReader.close();
        tempInvoiceWriter.close();
        return true;
    }

    public void saveInvoiceITem(List<InvoiceModel> models) throws IOException {

        invoiceWriter = new BufferedWriter(new FileWriter(invoiceFile));
        listInvoiceModels = new ArrayList<>();
        String buffer = "";
        for (InvoiceModel model : models) {
            //int nextId = getNextId();
            String line = model.getId() + "%" + model.getDate() + "%" + model.getCutomer() + "%" + model.getTotal() + "\n";
            buffer += line;
        }
        try {
            invoiceWriter.write(buffer);
            invoiceWriter.close();
        } catch (IOException ex) {
            System.out.println("Error while save invoice");
        }
    }

//    private int getNextId() {
//        if (listInvoiceModels == null || listInvoiceModels.size() == 0) {
//            return 1;
//        }
//        int maxId = listInvoiceModels.get(0).getId();
//        for (int i = 0; i < listInvoiceModels.size(); i++) {
//            maxId = Math.max(maxId, listInvoiceModels.get(i).getId());
//        }
//        maxId++;
//        return maxId;
//    }
}
