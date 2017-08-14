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
        String[] exempt = {"book", "pills", "chocolate", "chocolates"};
        // Item basket[] = new Item[4];
        // basket[1] = new Item("hi", 4.7, false);
        // // List<Item> order = new ArrayList<Item>();
        BufferedReader reader = null;

        try {

            File input = new File("input.txt");
            reader = new BufferedReader(new FileReader(input));
            String itemDesc;
            Integer customerNumber = 0;
            Float runningTotal = 0.0f;
            Float runningTax = 0.0f;

            while ((itemDesc = reader.readLine()) != null) { 
              
                if (itemDesc.contains("Input")) {
                    if (runningTotal != 0.0) {
                        System.out.println("Sales Taxes: " + String.format("%.2f", runningTax));
                        System.out.println("Total: " + String.format("%.2f", runningTotal));
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
                                // Float priceCents = price * 100;
                                regularTax = (price * 0.1f);
                                
                                
                                // System.out.println(newItem.getName() + "Regular tax = " + String.format("%.2f", regularTax));
                                runningTax = (runningTax + (regularTax * quantity));
                            }

                            if (newItem.getDuty() == true) {
                                
                                importTax = (price * 0.05f);
                                // System.out.println(newItem.getName() + "INPUT TAX" + String.format("%.2f", importTax));
                                // int taxx = Math.round(importTax);
                                // int modulus = Math.round(taxx % 5);
                                // if (modulus == 1) {
                                //     importTax = importTax;
                                // }
                                // else if (modulus == 2) {
                                //     importTax = importTax;
                                // }
                                // else if (modulus == 3) {
                                //     importTax = (importTax + 0.02f);
                                // }
                                // else if (modulus == 4) {
                                //     importTax = (importTax + 0.01f);
                                // }
                                // else {

                                // }

                                // import tax as string
                                String importTaxString = String.format("%.2f", importTax);
                                // import tax as four digit float
                                Float fourDigitTax = Float.parseFloat(importTaxString);
                                // four digit as string
                                String fourDigitTaxString = String.format("%.2f", fourDigitTax);
                                String[] splitter = fourDigitTaxString.split("");
                                // lastDigit as string
                                String lastDigit = splitter[splitter.length-1];
                                Integer lastDigitInt = Integer.parseInt(lastDigit);

                                if (lastDigitInt == 1) {
                                    importTax = importTax - 0.01f;
                                } 

                                if (lastDigitInt == 2) {
                                    importTax = importTax - 0.02f;
                                } 

                                if (lastDigitInt == 3) {
                                    importTax = importTax + 0.02f;
                                } 

                                if (lastDigitInt == 4) {
                                    importTax = importTax + 0.01f; 
                                } 

                                if (lastDigitInt == 6) {
                                    importTax = importTax - 0.01f;  
                                } 

                                if (lastDigitInt == 7) {
                                    importTax = importTax - 0.02f;  
                                } 

                                if (lastDigitInt == 8) {
                                    importTax = importTax + 0.02f; 
                                } 

                                if (lastDigitInt == 9) {
                                    importTax = importTax + 0.01f; 
                                } 

                                runningTax = (runningTax + (importTax * quantity));
                            }

                            Float finalItemTax = regularTax + importTax;



                            newItem.setPrice(newItem.getPrice() + regularTax + importTax);
                            runningTotal = runningTotal + (newItem.getPrice());
                           
                        }

                    }
                    // Float tax = newItem.getPrice();
                    // DecimalFormat dec = new DecimalFormat("#.##").format(tax);
                    System.out.println(newItem.getQuantity() + newItem.getName() + ": " + String.format("%.2f", newItem.getPrice()));
                }

                
            }

                        System.out.println("Sales Taxes: " + String.format("%.2f", runningTax));
                        System.out.println("Total: " + String.format("%.2f", runningTotal));

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
