class Item {
    private String name;
    private double price;
    private boolean exemption;
    private String newName;
    private int quantity;
    private boolean duty; 

    public Item(String name, double price, boolean exemption, int quantity, boolean duty) {
        this.name = name;
        this.price = price;
        this.exemption = exemption;
        this.quantity = quantity;
        this.duty = duty;
    }

    public void setPrice(double beforeTax) {
        price = beforeTax;
    }

    public void setDuty(boolean hasDuty) {
        duty = hasDuty;
    }

    public boolean getDuty() {
        return duty;
    }

    public void setQuantity(int newQuantity) {
      quantity = newQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setExemption(boolean bool) {
        exemption = bool;
    }

    public boolean getExemption() {
        return this.exemption;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void standardTax() {
        price = price + (price / 10); 
    }

    public void importTax() {
        price = price + (price * 0.05);
    }

    public Double getImportTax() {
        return (price * 0.05);
    }
}
