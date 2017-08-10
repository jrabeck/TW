import java.util.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


// originally I was thinking of how to add in user data with prompts, but 
// remembered the directions say to take input instead. this means that 
// structurally i *DONT* need an array built for cart that I can push into, 
// or at least not until I'm ready to get faadfadfadsfncy. 

class Item {
    private String name;
    public double price;
    public boolean exemption;
    private String newName;

    public Item(String name, double price, boolean exemption) {
        this.name = name;
        this.price = price;
        this.exemption = exemption;
    }

    public void setPrice(double beforeTax) {
        price = beforeTax;
    }

    public double getPrice() {
        return price;
    }

    public void setExemption(boolean bool) {
        exemption = bool;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void standardTax() {
        price = price = price + (price / 10); 
    }
}

public class cashRegister {
    public static void main(String[] args) {
        

        System.out.println("Please enter Cart");
        System.out.println("Thank you! Your total is :");


        String currentLine = null;

        String[] imported = {"imported", "foriegn"};
        String[] exempt = {"book", "medical", "chocolate"};
        Item basket[] = new Item[4];
        // List<Item> order = new ArrayList<Item>();
        BufferedReader reader = null;

        try {

            File file = new File("input.txt");
            reader = new BufferedReader(new FileReader(file));
            String line;

            int itemNumber = 0;

            while ((line = reader.readLine()) != null) { 

                System.out.println(line);
                if (!line.contains("Input")) {
                  itemNumber = itemNumber + 1;
                }
              // line is the string of a line as a whole, words is the line
              // as an object, current is the current word as a string. 
              StringTokenizer words = new StringTokenizer(line);

              Item newItem = new Item("", 0, false);
              Double currentPrice = newItem.price;

                while (words.hasMoreTokens()) {         
                  String current = words.nextToken();
                  boolean exemption = exemptFind(current, exempt);
                 
                  if (current.contains(".")) {
                    currentPrice = Double.parseDouble(current);
                  }

                  if (exemption = false) {
                    newItem.setExemption(true);
                    Double afterTax = currentPrice + (currentPrice / 10);
                    System.out.println("With tax: " + afterTax);
                  }
                
              }
                basket[1] = new Item("hi", 4.7, false);

                // order.add(newItem);

                System.out.println("Item number: " + itemNumber);
                boolean exemptStatus = newItem.exemption; 
                if (exemptStatus == true) {
                System.out.println("working");
              }
                System.out.println(newItem.getName());
              
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
      for(int i =0; i < taxBracket.length; i++)
      {
          if(word.contains(taxBracket[i]))
          {
              return true;
          }
      }
      return false;
  }





};
