import java.sql.*;

public class voiceRead {
    private Connection connect() {

        String url = "jdbc:sqlite:C:\\UCS\\QMSVoice\\rk7_voice\\db\\voice.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public String getGreaterThanZero() {

        String sql = "SELECT visit, qmsNumber, voiceState "
                + "FROM orders WHERE voiceState = 0";
        String qmsNum = null;
        Boolean voiceState = false;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //visit = rs.getInt("visit") ;
                 qmsNum = rs.getString("qmsNumber");
                voiceState = rs.getBoolean("voiceState");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return qmsNum;
    }
}