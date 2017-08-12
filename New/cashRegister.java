import java.util.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
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

        String[] imported = {"imported", "foriegn"};
        String[] exempt = {"book", "medical", "chocolate"};
        // Item basket[] = new Item[4];
        // basket[1] = new Item("hi", 4.7, false);
        // // List<Item> order = new ArrayList<Item>();
        BufferedReader reader = null;

        try {

            File file = new File("input.txt");
            reader = new BufferedReader(new FileReader(file));
            String line;

            int customerNumber = 0;
            Double runningTotal = 0.0;


            while ((line = reader.readLine()) != null) { 

                System.out.println(line);
                if (line.contains("Input")) {
                  if (runningTotal != 0.0) {
                  System.out.println("");
                  }
                  runningTotal = 0.0;
                  customerNumber = customerNumber + 1;
                  System.out.println("Customer number: " + customerNumber);
                }


           

                else {
              // line is the string of a line as a whole, words is the line
              // as an object, current is the current word as a string. 

                    Item newItem = new Item("", 0, false, 0, false);                 
                    boolean isExempt = exemptFind(line, exempt);
                    boolean hasADuty = dutyFind(line, imported);
                    if (isExempt == true) {
                        newItem.setExemption(isExempt);
                     }
                    if (hasADuty == true) {
                        newItem.setDuty(hasADuty);
                    }
                    StringTokenizer words = new StringTokenizer(line);
                    int quantity = Integer.parseInt(words.nextToken()); 
                    newItem.setQuantity(quantity);
                    Double currentPrice = newItem.price;
                    while (words.hasMoreTokens()) {         
                    String current = words.nextToken();

                    // int quantity = Integer.parseInt(current);

                    // boolean isExempt = exemptFind(current, exempt);
                    // if (isExempt == true) {
                    // newItem.setExemption(isExempt);
                    // }
                    // if (current instanceof String) {
                    //   System.out.println("TheBeatles");
                    // }



                    // ctrl z a while to back to where regular tax was calculating
                    if (current.contains(".")) {
                      currentPrice = Double.parseDouble(current);
                      newItem.setPrice(currentPrice);
                        if (newItem.getExemption() == false) {
                        Double afterTax = newItem.price + (newItem.price * 0.1);
                        newItem.setPrice(afterTax * quantity);
                        }
                          else {
                            newItem.setPrice(currentPrice * quantity);
                          }
                        if (newItem.getDuty() == true) {
                        Double afterDuty = newItem.price + (newItem.price * 0.05);
                        newItem.setPrice(afterDuty);
                        }
                      runningTotal = runningTotal + (newItem.getPrice());
                    }

                }

                  System.out.println("With tax: " + newItem.getPrice());
                  
                  
            }
                  System.out.println("Total: " + runningTotal + "\n");
          }
            

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } 
    // *********MAIN*************





  public static boolean exemptFind(String word, String[] taxBracket) {

      for(int i = 0; i < (taxBracket.length); i++)
      {
          if (word.contains(taxBracket[i]))
          {
              return true;
          }
      }
      return false;
  }

  public static boolean dutyFind(String wordd, String[] dutyCheck) {

      for(int i = 0; i < (dutyCheck.length); i++)
      {
          if (wordd.contains(dutyCheck[i]))
          {
              return true;
          }
      }
      return false;
  }





};

