package youtubeAPI;
import java.sql.*;
import java.util.Properties;

// Not Done by any means!
public class DatabaseAccess {
    private Connection db;

    public static void main(String[] args){
        DatabaseAccess dbA = new DatabaseAccess();
        dbA.createTables();

        String video_name = "https://www.youtube.com/watch?v=vUtjt13k9m4";
        System.out.println(video_name.substring(video_name.length() - 11));
        dbA.putVideoEntry("youtube",video_name.substring(video_name.length() - 11));
    }

    public DatabaseAccess() {
        try
        {
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","appsandorgs");
            this.db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/forticlassifier", props);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createTables(){ // check if tables already exist
        // creates the tables in proper order to prevent PK, FK conflicts
        createVideosTable();
    }

    public void dropTables(){
        try
        {
            String sql = "DROP TABLE IF EXISTS Videos CASCADE;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void emptyTables(){
        try
        {
            String sql = "TRUNCATE Videos CASCADE;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createTestTable() {
        try
        {
            String sql = "CREATE TABLE public.test\n" +
                    "(\n" +
                    "test_id serial NOT NULL,\n" +
                    "name character varying(70) NOT NULL\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE public.test\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createVideosTable(){
        try{
            String sqlS = "CREATE TABLE IF NOT EXISTS Videos\n"
                    + "(\n"
                    + "video_type character varying(20) NOT NULL,\n"
                    + "video_id character varying(11) NOT NULL UNIQUE,\n"
                    + "video_classification character varying(25),\n"
                    + "access_count int NOT NULL\n"
                    + ") WITH (OIDS = FALSE);"
                    + "ALTER TABLE Videos " +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sqlS);
            st.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // add logic for videoType finding and videoId elsewhere
    public void putVideoEntry(String videoType, String videoId){
        try{
            String sql = "INSERT INTO Videos\n" +
                    "(video_type, video_id, access_count) VALUES \n" +
                    "(?,?,0);\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1,videoType);
            st.setString(2,videoId);
            st.execute();
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void incrementAccessCount(String videoType, String videoId){
        try{
            String sql = "UPDATE Videos\n" +
                    "SET access_count = access_count + 1 \n" +
                    "WHERE video_id = ? AND video_type = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1,videoId);
            st.setString(2,videoType);
            st.execute();
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateVideoEntry(String videoClassification, String videoId){
        try
        {
            String sql = "UPDATE Videos\n" +
                    "SET video_classification = ? " +
                    "WHERE video_id = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, videoClassification);
            st.setString(2, videoId);
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getVideoClassification(String videoType, String videoId){
        int i = 0;  String videoClassification = null;
        try
        {
            String sql1 = "SELECT *" +
                    " FROM Videos WHERE video_type = ? " +
                    "AND video_id = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, videoType);
            st1.setString(2, videoId);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
               videoClassification = rs1.getString("video_classification");

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Video not found: " + videoType + " " + videoId);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return videoClassification;
    }
}