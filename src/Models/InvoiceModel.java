package Models;

import java.util.List;

public class InvoiceModel {
    private String Id;
    private String Date;
    private String Cutomer;
    private double Total;
    
    private List<InvoiceItemModel> listItems;

    public List<InvoiceItemModel> getListItems() {
        return listItems;
    }

    public void setListItems(List<InvoiceItemModel> listItems) {
        this.listItems = listItems;
    }
  
    
    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getCutomer() {
        return Cutomer;
    }

    public void setCutomer(String Cutomer) {
        this.Cutomer = Cutomer;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }   
}