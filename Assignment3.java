import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntData;
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
            conn=DriverManager.getConnection(url,username,password);
            if(conn!=null){
            System.out.println("Congrats u have already connected with the database");
            return true;
        }
        }
            catch(Exception exc){
                exc.printStackTrace();
                System.out.println("Conncetion failed;");
                return false;
          }
    }

    @Override
    public boolean disconnectDB() {
        try
        {
            if (conn!=null)
                conn.close();
            if(conn.isClosed())
                return true;
        }
        catch (SQLException e){
            System.out.println("can not close");
            return false;
        }
    }

    @Override
    public ElectionResult presidentSequence(String countryName) {
           
      ElectionResult result =null;
      List<INT> presidentIds=new ArrayList<INT>();
      List<String> partyNames=new ArrayList<String>();

        try
        {
            PreparedStatement presidentStat= conn.prepareStatement(
                "Select politican_president.id,party.name,politican_president.start_date"+
                "from politician_president.id join party"+
                "on politician_president.party_id=party.id"+ 
                "join country on country.id=politician_president.country_id"+
                "where country.name="+"\'"+countryName+"\'"+
                "order by politician_president.start_date desc;"
                );
                ResultSet presidents=presidentStat.executeQuery();
                While(presidents.next()){
                    int currentPresident =presidents.getInt(1);
                    String currentParty=presidents.getString(2);
                    presidentIds.add(currentPresident);
                    partyNames.add(currentParty);
                }
                result = new ElectionResult(presidentIds,partyNames);
        }
        catch (SQLException e){
            System.out.println("can not close");
        }
        
            /* 
            find presidentSequence logic        return list of President in that country, in descending order of date of occupying the office, and the name of the party
            that the president belongs to 
            
            create view Prisdent_country as:
            Select id ,country_id, party_id from politician_president 
            */
            return result;
	}

    @Override
    public List<INT> findSimilarParties(INT partyId, Float threshold) {
    //Write your code here.
    List<INT> similarParties= new ArrayList<INT>();
    try{
        PreparedStatement getParties=conn.prepareStatement(
        " select id,description from party where id= "+
        INT.toString(partyId)+";");

        PreparedStatement comparedParty=conn.prepareStatement(
            "select description from party where id = "+INT.toString(partyId)+";"
        );

        ResultSet singleParty=comparedParty.executeQuery();
        singleParty.next();
        String comparedDescription=singleParty.getString(1);
        ResultSet allParties=getParties.executeQuery();
        while(allParties.next()){
            String currentDescription=allParties.getString(2);
            int currentParty=allParties.getInt(1);
            if((similarity(currentDescription,comparedDescription))>=threshold){
                similarParties.add(currentParty);
            }
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }

        return similarParties;
    }

    public static void main(String[] args) throws Exception {
   	    //Write code here. 
        
           System.out.println("Hellow World");
    }

}



