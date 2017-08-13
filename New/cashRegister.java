import java.util.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.text.NumberFormat;
import java.io.IOException;


// originally I was thinking of how to add in user data with prompts, but 
// remembered the directions say to take input instead. this means that 
// structurally i *DONT* need an array built for cart that I can push into, 
// or at least not until I'm ready to get faadfadfadsfncy. 

class Item {
  // yes, i know this is abbhorent having everything public
    public String name;
    public double price;
    public boolean exemption;
    public String newName;
    public int quantity;
    public boolean duty; 

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
}

public class cashRegister {
    public static void main(String[] args) {

        String currentLine = null;
        String[] imported = {"imported"};
        String[] exempt = {"book", "pills", "chocolate"};
        // Item basket[] = new Item[4];
        // basket[1] = new Item("hi", 4.7, false);
        // // List<Item> order = new ArrayList<Item>();
        BufferedReader reader = null;

        try {

            File input = new File("input.txt");
            reader = new BufferedReader(new FileReader(input));
            String itemDesc;

            int customerNumber = 0;
            Double runningTotal = 0.0;
            Double runningTax = 0.0;

            while ((itemDesc = reader.readLine()) != null) { 
              
                if (itemDesc.contains("Input")) {
                  if (runningTotal != 0.0) {
                    System.out.println("Sales Taxes: " + runningTax);
                    System.out.println("Total: " + runningTotal);
                    System.out.println();
                  }
                  runningTotal = 0.0;
                  runningTax = 0.0;
                  customerNumber = customerNumber + 1;
                  System.out.println("Output: " + customerNumber);
                }

                else if (itemDesc.isEmpty()) {
                  continue;
                }

                else {
              // itemDesc is the string of a itemDesc as a whole, words is the itemDesc
              // as an object, current is the current word as a string. 
                      Item newItem = new Item("", 0, false, 0, false);                 
                      boolean isExempt = exemptFind(itemDesc, exempt);
                      boolean hasADuty = dutyFind(itemDesc, imported);
                      StringTokenizer words = new StringTokenizer(itemDesc);
              
                      System.out.println(itemDesc);
                      // this whole first character thing I wrote to help with the quantity
                      // problem, to have firstCharacter to use in a method
                      String firstCharacter = words.nextToken();
                      //  while (words.hasMoreTokens()) {  
                      //  String currentt = words.nextToken();
                      //  if (currentt == "a") {
                      //   currentt = ":";
                      //  }       
                      // }
                      // System.out.println(firstCharacter);
                      // so what's happening is it's going to the first character and seeing
                      // that it ("I") isn't int convertable, and this is breaking the try 
                      // method, which even though I don't understadn try really, i get. it's an
                      // exception that it catches. I 
                      // can't do any code after the final because of this. still compiles
                      // for some reason. 
                      int quantity = Integer.parseInt(firstCharacter); 
                      newItem.setQuantity(quantity);
                      Double currentPrice = newItem.price;
                      if (isExempt == true) {
                          newItem.setExemption(isExempt);
                      }
                      if (hasADuty == true) {
                          newItem.setDuty(hasADuty);
                      }
                      while (words.hasMoreTokens()) {         
                      String current = words.nextToken();

                        if (current.contains(".")) {
                          currentPrice = Double.parseDouble(current);
                          newItem.setPrice(currentPrice);

                            if (newItem.getExemption() == false) {
                              Double tax = (newItem.price * 0.1);
                              Double roundTax = Math.round(tax*100.0)/100.0;
                              Double afterTax = newItem.price + roundTax;
                              afterTax = Math.round(afterTax*100.0)/100.0;
                              newItem.setPrice(afterTax * quantity);
                              runningTax = (runningTax + tax);
                            }
                                else {
                                  newItem.setPrice(currentPrice * quantity);
                                }

                            if (newItem.getDuty() == true) {
                              Double duty = (newItem.price * 0.05);
                              Double roundDuty = Math.round(duty*100.0)/100.0;
                              Double afterDuty = newItem.price + roundDuty;
                              afterDuty = Math.round(afterDuty*100.0)/100.0;
                              newItem.setPrice(afterDuty);
                              runningTax = (runningTax + duty);
                            }
                          runningTotal = runningTotal + (newItem.getPrice());
                        }

                      }
                  
                }
                  // pretty sure this is where the total should be printed out after fixing 
                  // the quantity problem.
            }

            System.out.println("Sales Taxes: " + runningTax);
            System.out.println("Total: " + runningTotal);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("==========");
    } 


  public static boolean exemptFind(String word, String[] taxExempt) {
      for(int i = 0; i < (taxExempt.length); i++) {
          if (word.contains(taxExempt[i])) {
              return true;
          }
      }
      return false;
  }

  public static boolean dutyFind(String wordd, String[] dutyCheck) {
      for(int i = 0; i < (dutyCheck.length); i++) {
          if (wordd.contains(dutyCheck[i])) {
              return true;
          }
      }
      return false;
  }

};

