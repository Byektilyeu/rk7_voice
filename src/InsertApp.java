import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertApp {
    private Connection connect(){
        String url = "jdbc:sqlite:C:\\UCS\\Voice\\rk7_voice-master\\db\\voice.db";
        Connection conn = null;
        try {
            conn= DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void insert(Integer visit, String qmsNumber, String kdsState)

    {

        String sql = "INSERT INTO orders(visit, qmsNumber, kdsState, capacity) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, visit);
            pstmt.setString(2, qmsNumber);
            pstmt.setString(3, kdsState);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}