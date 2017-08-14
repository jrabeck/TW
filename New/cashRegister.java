import java.util.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.text.NumberFormat;
import java.io.IOException;

public class cashRegister {
    public static void main(String[] args) {

        String[] imported = {"imported"};
        String[] exempt = {"book", "pills", "chocolate"};
        String currentLine = null;
        BufferedReader reader = null;

        try {

            // The BufferedReader tool works with FileReader to go through the .txt document and bring each line in 
            // as a string
            File input = new File("input.txt");
            reader = new BufferedReader(new FileReader(input));
            String itemDesc;
            Integer customerNumber = 0;

            // tax and total is initilized here where it will be available to all of the try method
            Float runningTotal = 0.0f;
            Float runningTax = 0.0f;

            while ((itemDesc = reader.readLine()) != null) { 
              
              // if the line is input, the program registers it as a new customer order. I also print out the 
              // previous totals here, I know it's not super elegant. 
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

                    // Woohoo! Instantiation! Here's where I set up the golden objects of my OOP! 
                    Item newItem = new Item("", 0.0f, false, 0.0f, false);                 
                    boolean isExempt = exemptFind(itemDesc, exempt);
                    boolean hasADuty = dutyFind(itemDesc, imported);

                    // The tokenizer was a life saver that turns each line into an object that I can then call
                    // the nextToken() method. This allows me to iterate through the string and define my object's
                    // attributes as I go. 
                    StringTokenizer words = new StringTokenizer(itemDesc);
                    String firstCharacter = words.nextToken();
                    Float quantity = Float.parseFloat(firstCharacter); 
                    newItem.setQuantity(quantity);
                    Float currentPrice = newItem.getPrice();

                    // Here, I'm calling the methods at the bottom of the code which check each word i'm passing to it
                    // in order to determine if the product has any special tax status
                    if (isExempt == true) {
                        newItem.setExemption(isExempt);
                    }

                    if (hasADuty == true) {
                        newItem.setDuty(hasADuty);
                    }

                    // Now I can go through each word and add it to the name of the item and also grab price values.
                    while (words.hasMoreTokens()) {         
                    String current = words.nextToken();

                        if (!current.equals("at") && !current.contains(".")) {
                            newItem.setName(newItem.getName() + " " + current);
                        }

                        if (current.contains(".")) {

                          // Luckily this isn't a software shop with versions of software on the shopping list so
                          // I use presence of a decimal to grab my numbers. With more time I would have liked to 
                          // use the currency class to avoid later problems. 
                            currentPrice = Float.parseFloat(current);
                            newItem.setPrice(currentPrice);
                            Float price = newItem.getPrice();
                            Float regularTax = 0.0f;
                            Float importTax = 0.0f;
                            Float cents = newItem.getPrice() * 100;
                            Float realTax = 0.0f;

                            // regular tax is easy, it's just 10%
                            if (newItem.getExemption() == false) {
                                regularTax = (price * 0.1f);
                                runningTax = (runningTax + (regularTax * quantity));
                            }
                             // imported items are 5%, the float filetype and Java's complicated designs for storing 
                             // different types of number values made this a challenge. I tried a few other datatypes
                             // but they weren't much better.  
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

                                // I know it's not very scalable but this solution I came up with grabs the last
                                // digit of a tax amount and converts its type until I can work with it and then 
                                // adds or subtracts the needed value to round the amount to the nearest 0.05.
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

                            newItem.setPrice(newItem.getPrice() + regularTax + importTax);
                            runningTotal = runningTotal + (newItem.getPrice());
                           
                        }

                    }
                    // the String.format method allows me to only show two digits of the number but it's solely 
                    // aesthetic, the price is not actually necessarily formatted as 4 digits. 
                    System.out.println(Math.round(newItem.getQuantity()) + newItem.getName() + ": " + String.format("%.2f", newItem.getPrice()));
                }

            }

            System.out.println("Sales Taxes: " + String.format("%.2f", runningTax));
            System.out.println("Total: " + String.format("%.2f", runningTotal));

        // This is the end of the BufferedReader. The whole reader "tries" to read lines, if something is off about
        // the input it can't, so having it as a try allows the program to compile, but it still allows for runtime
        // errors. 
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


    // this is the format for iterating through a String to check if it matches any strings in an array. It has a
    // standard for loop that goes through each word and then checks to see if it has anything in common with the 
    // words in the array. After it goes through all the items, it returns false and the item is later taxed 10%, 
    // otherwise it sets the exempt status to true which means the tax is omitted. 
    public static boolean exemptFind(String word, String[] taxExempt) {
        for(int i = 0; i < (taxExempt.length); i++) {
            if (word.contains(taxExempt[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean dutyFind(String inputWord, String[] dutyCheck) {
        for(int i = 0; i < (dutyCheck.length); i++) {
            if (inputWord.contains(dutyCheck[i])) {
                return true;
            }
        }
        return false;
    }

};
