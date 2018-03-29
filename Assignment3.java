import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Assignment3 extends JDBCSubmission {
    //private Connection connection=null;

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
            connection =DriverManager.getConnection(url,username,password);
            if(connection!=null){
            System.out.println("Congrats u have already connected with the database");
            return true;
        }
        }
            catch(SQLException exc){
                exc.printStackTrace();
                System.out.println("Conncetion failed;");
          }
          return false;
    }

    @Override
    public boolean disconnectDB() {
        try
        {
            if (connection!=null)
                connection.close();
            if(connection.isClosed())
                return true;
        }
        catch (SQLException e){
            System.out.println("can not close");
            
        }
        return false;
    }

    @Override
    public ElectionResult presidentSequence(String countryName) {
           
      ElectionResult result =null;
      List<Integer> presidentIds=new ArrayList<Integer>();
      List<String> partyNames=new ArrayList<String>();

        try
        {
            PreparedStatement presidentStat= connection.prepareStatement(
                "SELECT politician_president.id, party.name " +
                "from politician_president join party " +
                "on politician_president.party_id = party.id join country " +
                "on country.id = politician_president.country_id " +
                "where country.name = " + "\'" + countryName + "\' " +
                "order by politician_president.start_date desc;");
                ResultSet presidents=presidentStat.executeQuery();
                /*if(presidentIds!=null){
                    Integer currentPresident =presidents.getInt(1);
                    String currentParty=presidents.getString(2);
                    presidentIds.add(currentPresident);
                    partyNames.add(currentParty);
                }*/
                while(presidents.next())
                {
                    Integer currentPresident =presidents.getInt(1);
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
    public List<Integer> findSimilarParties(Integer partyId, Float threshold) {
    //Write your code here.
    List<Integer> similarParties= new ArrayList<Integer>();
    try{
        //pick the description
        PreparedStatement description=connection.prepareStatement(
        " select description from party where id="+Integer.toString(partyId)+";");

        PreparedStatement otherParties=connection.prepareStatement(
            "select id ,description from party where id !="+Integer.toString(partyId)+";"
        );

        ResultSet sample=description.executeQuery();
        sample.next();
        String ds=sample.getString(1);
        
        ResultSet restParties=otherParties.executeQuery();
        /*if(allParties!=null){
            String currentDescription=allParties.getString(2);
            Integer currentParty=allParties.getInt(1);
            if((similarity(currentDescription,comparedDescription))>=threshold){
                similarParties.add(currentParty);
            }
        }*/
        while(restParties.next()){
            String currentDescription=restParties.getString(2);
            Integer currentParty=restParties.getInt(1);
            if((similarity(currentDescription,ds))>=threshold){
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



