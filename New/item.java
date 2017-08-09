
  public class Item {
      private String name;
      private double price;
      private boolean exemption;

      public Item(String name, double price, boolean exemption) {
        name = name;
        price = price;
        exemption = exemption;
      }

      public void setPrice(float beforeTax) {
        price = beforeTax;
      }

      public double getPrice () {
        return price;
      }

      public void standardTax() {
        price = price = price + (price / 10); 
      }
  }