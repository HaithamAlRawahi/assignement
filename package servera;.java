package servera;
import  java.io.*;
import java.net.*;
import java.sql.*;
// Create Thread 
class SSrever implements Runnable
{
    public void run()
    {
        // code  for server 
        try {
            ServerSocket SS =new ServerSocket(6666);
            Socket S =SS.accept();
            DataOutputStream DOS=new DataOutputStream(S.getOutputStream());
            DataInputStream DIS =new DataInputStream(S.getInputStream());
            // recteve  the data form client - option 
            int option=DIS.readInt();
            // make connection to the database 
            String host ="jdbc:derby://localhost:1527/Database";
            String UN="Haitham";
            String pwp ="123";
            Connection con = DriverManager.getConnection(host,UN,pwp);
            // search the database to retrieve the data 
            String query = "Select * from DISCOUNT where id ="+option;
            Statement stmt = con.createStatement();
            stmt.execute(query);
            ResultSet RS =stmt.getResultSet();
            int ticket=5;
            int days = 30;
            //calculates the price ,discount ,
            if(RS.next())
            {
                int ID =RS.getInt("ID");
                String Duration = RS.getString("DURATION");
                double discount =RS.getDouble("DISCOUNT");
                double cp = 0;
                if(ID==1) {
                    cp=ticket*days;
                }else if(ID == 2) cp=ticket*days*6;
                else cp=ticket*days*12; 
                double discountAmount = cp * discount;
                double fp = cp - discountAmount;
                // Send the result to client ,final price
                DOS.writeUTF("CP: " + cp + " Discount: " + discountAmount + " Final Price: " + fp);
            }
                  // Close the connection 
            S.close();
            SS.close();
                  } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
public class ServerA {
    public static void main(String[] args) {
        Thread t = new Thread(new SSrever());
        t.start();
    }
  }
