import java.util.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.text.NumberFormat;
import java.io.IOException;

// Hello ThoughtWorks!
// Learning Java almost from scratch in 1.5 weeks and writing this program was one of the hardest
// technological/academic things I've done and regardless of how silly this code is at times, I'm 
// very proud of it, every line is hard won. It takes in Items as objects with attributes but does
// not store them longer than it takes to print them out. It's basically all in the main method
// which I know is sloppy and the logic isn't split up into methods the way it should be. The rounding
// to 5 strategy is almost a parody of itself, but it works! I mention all this not to talk myself 
// down (this thing runs I'm proud of it!) but to demonstrate that I understand concepts beyond what 
// I've show here. I'd love the opportunity to come in and work with the team on how to implement my 
// real vision for the program of having an Abstract Factory pattern, where each type of item is 
// its own factory.   

public class cashRegister {
    public static void main(String[] args) {

        String[] imported = {"imported"};
        String[] exempt = {"book", "pills", "chocolate"};
        String currentLine = null;
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
                    Item newItem = new Item("", 0.0f, false, 0.0f, false);                 
                    boolean isExempt = exemptFind(itemDesc, exempt);
                    boolean hasADuty = dutyFind(itemDesc, imported);
                    StringTokenizer words = new StringTokenizer(itemDesc);
                    String firstCharacter = words.nextToken();
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

                        if (current.contains(".")) {
                            currentPrice = Float.parseFloat(current);
                            newItem.setPrice(currentPrice);
                            Float price = newItem.getPrice();
                            Float regularTax = 0.0f;
                            Float importTax = 0.0f;
                            Float cents = newItem.getPrice() * 100;
                            Float realTax = 0.0f;
                            if (newItem.getExemption() == false) {
                                regularTax = (price * 0.1f);
                                runningTax = (runningTax + (regularTax * quantity));
                            }

                            if (newItem.getDuty() == true) {  
                                importTax = (price * 0.05f);

                                // This was a bit of a more sustainable strategy I attempted but it didn't work 
                                // out for me. 
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

                                String importTaxString = String.format("%.2f", importTax);
                                Float fourDigitTax = Float.parseFloat(importTaxString);
                                String fourDigitTaxString = String.format("%.2f", fourDigitTax);
                                String[] splitter = fourDigitTaxString.split("");
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
                    System.out.println(Math.round(newItem.getQuantity()) + newItem.getName() + ": " + String.format("%.2f", newItem.getPrice()));
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
