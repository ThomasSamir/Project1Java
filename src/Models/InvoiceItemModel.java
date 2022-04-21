
package Models;

public class InvoiceItemModel {
    private String Id;
    private String InvoiceId;
    private String ItemName;
    private double ItemPrice;
    private double Count;
    private double ItemTotal;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String InvoiceId) {
        this.InvoiceId = InvoiceId;
    }
    
    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public double getCount() {
        return Count;
    }

    public void setCount(double Count) {
        this.Count = Count;
    }

    public double getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(double ItemTotal) {
        this.ItemTotal = ItemTotal;
    }   
}
