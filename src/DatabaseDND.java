
import java.sql.*;
import javax.swing.JOptionPane;
/**
 * 
 * This is the DatabaseDND class.
 */
public class DatabaseDND {
    private int compID = 0;    
    private double dollar;
    private String competitorName;       
    private ResultSet rs = null;
    private Connection connect = DatabaseConnect.dbconnect();
    private PreparedStatement pst = null;

    public String getComptetiorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }   

    public DatabaseDND(String competitorName, double dollar) {//Constructor with
        //4 parameters
        this.competitorName = competitorName;
        this.dollar = dollar;
    }

    public DatabaseDND() {//Default constructor
        this.compID = 0;
        this.competitorName = null;
        this.dollar = 0.0;        
    }

    
    
    public void insert() {//This method is to insert into the database
        String query = "insert into HARAMBAE.HIGHSCORE(UserID, ContestentName, PrizeMoney) values ("
                + "?, ?, ?)";

        try {
            count_player();
            pst = connect.prepareStatement(query);
            pst.setInt(1, this.compID);
            pst.setString(2, this.competitorName);
            pst.setDouble(3, this.dollar);        
            

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Saved", "Insert Successfully", JOptionPane.INFORMATION_MESSAGE);
            pst.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void count_player() {//This method is to count the number of row and add as UserID
        try {
            String insertquery = "select count(*) from HARAMBAE.HIGHSCORE";
            pst = connect.prepareStatement(insertquery);
            rs = pst.executeQuery();
            if (rs.next()) {
                this.compID = rs.getInt(1);
            }
            this.compID += 1;
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
