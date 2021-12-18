import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class InvalidInputException extends Exception{

}
public class emailId {
    int srno;
    String email;

    emailId(int srno, String email) {
        this.srno = srno;
        this.email = email;
    }
}
class test{
    public boolean rgx(String s){
        Pattern P = Pattern.compile("[a-zA-Z0-9_.]+[@](gla)[.]com");
        Matcher m = P.matcher(s);
        return m.find();
    }
    public void insert(int srno,String email) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtest","root","")){
                PreparedStatement s = con.prepareStatement("insert into info values (?,?)");
                s.setInt(1,srno);
                s.setString(2,email);
                s.execute();
        }
        catch (Exception e) {
        }

    }
    public void display(int r) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtest", "root", "")) {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("Select * from info where srno = '" + r + "'");
            if(rs.next()){
                System.out.println("Email id is present");
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                }

            else {
               throw  new InvalidInputException();
            }
        } catch (Exception e) {

        }
    }
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        ArrayList<emailId> arr = new ArrayList<>();
        test obj = new test();
        for(int i=0;i<3;i++) {
            arr.add(new emailId(sc.nextInt(), sc.next()));
            if (obj.rgx(arr.get(i).email)) {
                obj.insert(arr.get(i).srno, arr.get(i).email);
            }
        }
        System.out.println("Enter the srno of the emailId you want");
        int m= sc.nextInt();
        obj.display(m);
}
    }
