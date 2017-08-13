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
// or at least not until I'm ready to get fancy. 

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
            Float customerNumber = 0f;
            Float runningTotal = 0.0f;
            Float runningTax = 0.0f;

            while ((itemDesc = reader.readLine()) != null) { 
              
                if (itemDesc.contains("Input")) {
                    if (runningTotal != 0.0) {
                        System.out.println("Sales Taxes: " + runningTax);
                        System.out.println("Total: " + runningTotal);
                        System.out.println();
                    }
                    runningTotal = 0.0f;
                    runningTax = 0.0f;
                    customerNumber = customerNumber + 1;
                    System.out.println("Output " + customerNumber + ":");
                }

                else if (itemDesc.isEmpty()) {
                    continue;
                }

                else {
                    // itemDesc is the string of a itemDesc as a whole, words is the itemDesc
                    // as an object, current is the current word as a string. 
                    Item newItem = new Item("", 0.0f, false, 0.0f, false);                 
                    boolean isExempt = exemptFind(itemDesc, exempt);
                    boolean hasADuty = dutyFind(itemDesc, imported);
                    StringTokenizer words = new StringTokenizer(itemDesc);
                    // System.out.println(itemDesc);
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
                    Float quantity = Float.parseFloat(firstCharacter); 
                    newItem.setQuantity(quantity);
                    Float currentPrice = newItem.getPrice();
                    if (isExempt == true) {
                        newItem.setExemption(isExempt);
                    }
                    if (hasADuty == true) {
                        newItem.setDuty(hasADuty);
                    }
                    while (words.hasMoreTokens()) {         
                    String current = words.nextToken();

                        if (!current.equals("at") && !current.contains(".")) {
                            newItem.setName(newItem.getName() + " " + current);
                        }

                        // else {
                        //   currentPrice = current.nextToken();
                        //   currentPrice = Float.parseFloat(nextToken());
                        //   newItem.setPrice(currentPrice);
                        // }

                        if (current.contains(".")) {
                            currentPrice = Float.parseFloat(current);
                            newItem.setPrice(currentPrice);
                            Float price = newItem.getPrice();
                            Float regularTax = 0.0f;
                            Float importTax = 0.0f;
                            // 14.99
                            Float cents = newItem.getPrice() * 100;
                            // 1499
                            Float realTax = 0.0f;
                            // 0.00
                            if (newItem.getExemption() == false) {
                                regularTax = (price * 0.1f);
                                
                                System.out.println("tax = " + regularTax);
                                runningTax = (runningTax + (regularTax * quantity));
                            }

                            if (newItem.getDuty() == true) {
                                importTax = (price * 0.05f);
                                System.out.println("tax = " + importTax);
                                runningTax = (runningTax + (importTax * quantity));
                            }
                            newItem.setPrice(newItem.getPrice() + regularTax + importTax);
                            runningTotal = runningTotal + (newItem.getPrice());
                            System.out.println();
                        }

                    }
                    String zero = "";
                    
                    if (newItem.getPrice() % 0.01 < 0.0 ) {
                        zero = "0";
                    } 

                    // Float tax = newItem.getPrice();
                    // DecimalFormat dec = new DecimalFormat("#.##").format(tax);
                    System.out.println(newItem.getQuantity() + newItem.getName() + ": " + newItem.getPrice() + zero);
                }

                
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

    public static Float roundTo5(Float tax) {
          Float cents   = tax * 100;
          Float fiveValue = cents % 5.0f;
          if (fiveValue == 1.0) {
              return ((tax - fiveValue) / 10);
          }
          else if (fiveValue == 2.0) {
              return ((tax - fiveValue) / 10);
          }
          else if (fiveValue == 3.0) {
              return ((tax + 0.02f) / 10);
          }
          else if (fiveValue == 4.0) {
              return ((tax + 0.01f) / 10f);
          }
              return cents / 1000;
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
