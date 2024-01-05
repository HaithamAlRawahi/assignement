package clienta;
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
//create thread for the client code
class SClient implements  Runnable
{
    public void run()
    {
        try {
            // Create a socket to connect to the server 
            Socket S=new Socket("localhost",6666); 
            // Create input and output streams for sending/receiving data 
            DataInputStream DIS = new DataInputStream(S.getInputStream()); 
            DataOutputStream DOS = new DataOutputStream(S.getOutputStream()); 
            // Accept and validate input from the user 
            Scanner Sc = new Scanner(System.in); 
            System.out.print("Enter Civil Number: "); 
            int civilNumber = Sc.nextInt(); 
            // Validate the civil number format here 
            System.out.println("1. Monthly"); 
            System.out.println("2. Half yearly"); 
            System.out.println("3. Yearly"); 
            int option = 0; 
            boolean validOption = false; 
            // Validate the selected option here 
            while (!validOption) { 
                System.out.print("Select an option by entering: "); 
                option = Sc.nextInt(); 
                if (option >= 1 && option <= 3) { 
                    validOption = true; 
                } else { 
                    System.out.println("Invalid option. Please try again."); 
                } 
            // Send the civil number and option to the server 
            DOS.writeInt(option); 
            DOS.flush(); 
            // Receive and display the results from the server 
            String result = DIS.readUTF(); 
            System.out.println("Result: " + result); 
            // Close the connection 
            S.close();
            Sc.close();
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
} 
public class ClientA {
    public static void main(String[] args) {
        SClient C= new SClient();
        Thread t =new Thread (C);
        t.start();
    }
  }
