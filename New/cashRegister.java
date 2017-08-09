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

public class cashRegister {
    public static void main(String[] args) {
        

        System.out.println("Please enter Cart");
        System.out.println("Thank you! Your total is :");


        String currentLine = null;
       
        String[] imported = {"imported", "foriegn"};
        String[] exempt = {"book", "medical", "chocolate"};

        BufferedReader reader = null;

        try {
            File file = new File("/Users/jakerabeck/Programming/TW/New/input.txt");
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

              // line is the string of a line as a whole, words is the line
              // as an object, current is the current word as a string. 
              StringTokenizer words = new StringTokenizer(line);
              while (words.hasMoreTokens()) {         
                String current = words.nextToken();
                String goodbye = "GOODBYE";
                System.out.println(current);
                if (current.equals(exempt)) {
                  System.out.println("balls");

                };
                
              }
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

      int methodName(int a, int b) {
        return  0;
   // body
}

    public static int minFunction(int n1, int n2) {
               int min;
               if (n1 > n2)
                  min = n2;
               else
                  min = n1;

               return min; 
            }
    private int hi = methodName(5,9);

};

   


     
    // while there is a line to read
    