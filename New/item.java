class Item {
    private String name;
    private Float price;
    private boolean exemption;
    private String newName;
    private Float quantity;
    private boolean duty; 

    public Item(String name, Float price, boolean exemption, Float quantity, boolean duty) {
        this.name = name;
        this.price = price;
        this.exemption = exemption;
        this.quantity = quantity;
        this.duty = duty;
    }

    public void setPrice(Float beforeTax) {
        price = beforeTax;
    }

    public void setDuty(boolean hasDuty) {
        duty = hasDuty;
    }

    public boolean getDuty() {
        return duty;
    }

    public void setQuantity(Float newQuantity) {
      quantity = newQuantity;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Float getPrice() {
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
        price = price + (price / 10.0f); 
    }

    public void importTax() {
        price = price + (price * 0.05f);
    }

    public Float getImportTax() {
        return (price * 0.05f);
    }
}
