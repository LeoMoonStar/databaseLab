import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.math.*;

public class Assignment3 extends JDBCSubmission {
    private Connection conn=null;

    public Assignment3() throws ClassNotFoundException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex){
            System.out.println("Error: unable to loaf driver class!");
            System.exit(1);
        }
    }

    @Override
    public boolean connectDB(String url, String username, String password) {
        try{
            conn=DriverManager.getConnection("jdbc::postgresql:localhost:5432/csc343h-yangjiaw","yangjiaw","");
            System.out.println("Congrats u have already connected with the database");
        }
            catch(Exception exc){
                System.out.println("Conncetion failed;");
          }
        
            
        return true;

    }

    @Override
    public boolean disconnectDB() {
	    conn.close();
            return true;
    }

    @Override
    public ElectionResult presidentSequence(String countryName) {
            Statement a1=conn.createStatement();
            /* 
            find presidentSequence logic        return list of President in that country, in descending order of date of occupying the office, and the name of the party
            that the president belongs to 
            
            create view Prisdent_country as:
            Select id ,country_id, party_id from politician_president 
            */
            return null;
	}

    @Override
    public List<Integer> findSimilarParties(Integer partyId, Float threshold) {
	//Write your code here.
        return null;
    }

    public static void main(String[] args) throws Exception {
   	    //Write code here. 
	    System.out.println("Hellow World");
    }

}



