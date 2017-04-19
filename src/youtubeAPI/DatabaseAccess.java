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
            String sql = "DROP TABLE IF EXISTS Videos CASCADE;"
                    + "DROP TABLE IF EXISTS Client CASCADE;"
                    + "DROP TABLE IF EXISTS Project CASCADE;"
                    + "DROP TABLE IF EXISTS Employees CASCADE;"
                    + "DROP TABLE IF EXISTS Payment CASCADE;"
                    // + "DROP TABLE IF EXISTS Schedule CASCADE;"
                    + "DROP TABLE IF EXISTS Contact CASCADE;"
                    + "DROP TABLE IF EXISTS Account CASCADE;"
                    + "DROP TABLE IF EXISTS Event CASCADE;";
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
            String sql = "TRUNCATE Videos CASCADE;"
                    + "TRUNCATE Client CASCADE;"
                    + "TRUNCATE Project CASCADE;"
                    + "TRUNCATE Employees CASCADE;"
                    + "TRUNCATE Payment CASCADE;"
                    //       + "TRUNCATE Schedule CASCADE;"
                    + "TRUNCATE Contact CASCADE;"
                    + "TRUNCATE Account CASCADE;"
                    + "TRUNCATE Event CASCADE;";
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

    // Modules we have - Tags Module, Description Module, Related Sites Module, Channel Module (mine)
    // Carolina - Twitter Module, Country Module
    // Cantrelle - Google Module

    public void createVideosTable(){
        try{
            String sqlS = "CREATE TABLE IF NOT EXISTS Videos\n"
                    + "(\n"
                    + "video_type character varying(20) NOT NULL,\n"
                    + "video_id character varying(11) NOT NULL,\n"
                    + "video_classification character varying(25)"
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
                    "(video_type, video_id) VALUES \n" +
                    "(?,?);\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1,videoType);
            st.setString(2,videoId);
            st.execute();
            st.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // will change based on how we decide to do the different Modules
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

    // MODIFIED for sequence, check it out!
    public void createUserTable(){
        try
        {
            String sql2 = "CREATE TABLE IF NOT EXISTS Users\n" +
                    "(\n" +
                    "UserID serial PRIMARY KEY NOT NULL, \n" +
                    "companyName character varying (50) NOT NULL\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Users\n" +
                    "OWNER TO postgres;";
            Statement st2 = db.createStatement();
            st2.execute(sql2);
            st2.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createClientTable(){
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Client\n" +
                    "(\n" +
                    "ClientID serial PRIMARY KEY NOT NULL, \n" +
                    "companyName character varying (50) NOT NULL\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Client\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createAccountTable(){
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Account\n" +
                    "(\n" +
                    "AccountID serial PRIMARY KEY NOT NULL, \n" +
                    "bankName character varying (50) NOT NULL,\n" +
                    "bankAddress character varying (100),\n" +
                    "amount decimal NOT NULL,\n" +
                    "UserID integer NOT NULL references Users(UserID) ON DELETE CASCADE\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Account\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createContactTable() {
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Contact\n" +
                    "(\n" +
                    "ContactID serial PRIMARY KEY NOT NULL, \n" +
                    "fname character varying (25) NOT NULL,\n" +
                    "lname character varying (25) NOT NULL,\n" +
                    "description character varying (200) NOT NULL,\n" +
                    "phoneNum character (10) NOT NULL,\n" +
                    "emailAddress character varying (50) NOT NULL,\n" +
                    "ClientID integer NOT NULL references Client(ClientID) ON DELETE CASCADE\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Contact\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createProjectTable(){
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Project\n" +
                    "(\n" +
                    "ProjectID serial PRIMARY KEY NOT NULL, \n" +
                    "name character varying (50) NOT NULL,\n" +
                    "description character varying (200),\n" +
                    "ClientID integer NOT NULL references Client(ClientID) ON DELETE CASCADE\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Project\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createEmployeeTable(){
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Employees\n" +
                    "(\n" +
                    "EmployeeID serial PRIMARY KEY NOT NULL, \n" +
                    "UserID integer NOT NULL references Users(UserID) ON DELETE CASCADE, \n" +
                    "fname character varying (25) NOT NULL,\n" +
                    "lname character varying (25) NOT NULL,\n" +
                    "role character varying (100) NOT NULL\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Employees\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createEventTable(){ // date info https://www.postgresql.org/docs/9.1/static/datatype-datetime.html
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Event\n" +
                    "(\n" +
                    "EventID serial PRIMARY KEY NOT NULL, \n" +
                    "name character varying (50) NOT NULL,\n" +
                    "eventDate date NOT NULL,\n" +
                    "eventTime integer NOT NULL,\n" +
                    "eventDuration integer,\n" +
                    "ProjectID integer references Project(ProjectID) ON DELETE CASCADE,\n" +
                    "EmployeeID integer references Employees(EmployeeID) ON DELETE CASCADE,\n" +
                    "ContactID integer references Contact(ContactID) ON DELETE CASCADE\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Event\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createPaymentTable(){
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Payment\n" +
                    "(\n" +
                    "PaymentID serial PRIMARY KEY NOT NULL, \n" +
                    "paymentName character varying (50) NOT NULL,\n" +
                    "paymentAmount decimal NOT NULL,\n" +
                    "dateOfPayment date NOT NULL,\n" +
                    "paymentType integer,\n" +
                    "AccountID integer references Account(AccountID) ON DELETE CASCADE,\n" +
                    "ProjectID integer references Project(ProjectID) ON DELETE CASCADE\n" +
                    ")\n" +
                    "WITH (\n" +
                    "OIDS = FALSE\n" +
                    ")\n" +
                    ";\n" +
                    "ALTER TABLE Payment\n" +
                    "OWNER TO postgres;";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*public void putUser(User user){
        try
        {
            String sql = "INSERT INTO Users\n" +
                    "(companyName) VALUES \n" +
                    "(?) RETURNING userid;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, user.getName());
            ResultSet res = st.executeQuery();
            if (res.next())
                user.setUserID(res.getInt("userid"));
            res.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        try
        {
            String sql = "UPDATE Users\n" +
                    "SET companyname = ? " +
                    "WHERE userid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, user.getCompanyName());
            st.setInt(2, user.getUserID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeUser(User user){
        try
        {
            String sql = "DELETE FROM Users\n" +
                    "WHERE UserID = \n" +
                    "'" + user.getUserID() +
                    "';\n";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
            user.setUserID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public User getUser(String companyName){
        int i = 0;
        User user = null;
        try
        {
            String sql1 = "SELECT * FROM Users WHERE companyname = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, companyName);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){ // currently assumes only one companyName
                user = new User(rs1.getString("companyname")); // added a constructor to User
                user.setUserID(rs1.getInt("userid"));
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("User not found: " + companyName);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public User getUser(int UserID){
        int i = 0;
        User user = null;
        try
        {
            String sql1 = "SELECT * FROM Users WHERE userid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, UserID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){ // currently assumes only one companyName
                user = new User(rs1.getString("companyname")); // added a constructor to User
                user.setUserID(rs1.getInt("userid"));
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("User not found: " + UserID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public Vector<User> getAllUsers(){ // change to Vector<Users>
        int i = 0;
        Vector<User> userVector = new Vector<User>();
        try
        {
            String sql1 = "SELECT * FROM Users;\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                User user = new User(rs1.getString("companyname"));
                user.setUserID(rs1.getInt("userid"));

                userVector.add(user); // make sure this is correct
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userVector;
    }
    */

    /*
    public void putClient(Client client){
        try
        {
            String sql1 = "INSERT INTO Client (companyName) VALUES (?) RETURNING clientid;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, client.getName());
            ResultSet res = st1.executeQuery();
            if (res.next())
                client.setClientID(res.getInt("clientid"));
            res.close();
            st1.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateClient(Client client){
        try
        {
            String sql = "UPDATE Client\n" +
                    "SET companyname = ? " +
                    "WHERE clientid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, client.getName());
            st.setInt(2, client.getClientID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeClient(Client client){
        try
        {
            String sql1 = "DELETE FROM Client WHERE clientid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, client.getClientID());
            st1.executeUpdate();
            st1.close();

            client.setClientID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Client getClient(int ClientID){
        int i = 0;
        Client client = null;
        try
        {
            String sql1 = "SELECT * FROM Client WHERE clientid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, ClientID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                client = new Client(rs1.getString("companyname")); // made a new constructor
                client.setClientID(ClientID);
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Client not found: " + ClientID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return client;
    }

    public void putProject(Project proj){
        try
        {
            String sql1 = "INSERT INTO Project (name, description, ClientID) VALUES (?, ?, ?) RETURNING projectid;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, proj.getName());
            st1.setString(2, proj.getDescription());
            st1.setInt(3, proj.getClientID());  //need to find a way to get outside IDs. Add an ID to java class?
            ResultSet res = st1.executeQuery();
            if (res.next())
                proj.setProjectID(res.getInt("projectid"));
            res.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateProject(Project proj){
        try
        {
            String sql = "UPDATE Project\n" +
                    "SET name = ?, description = ?, ClientID = ? " +
                    "WHERE projectid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, proj.getName());
            st.setString(2, proj.getDescription());
            st.setInt(3, proj.getClientID());
            st.setInt(4, proj.getProjectID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeProject(Project proj){
        try
        {
            String sql1 = "DELETE FROM Project WHERE projectid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, proj.getProjectID());
            st1.executeUpdate();
            st1.close();
            proj.setProjectID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Project getProject(String name){
        int i = 0;
        Project project = null;
        try
        {
            String sql1 = "SELECT * FROM Project WHERE name = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, name);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                project = new Project(rs1.getString("name"),
                        rs1.getString("description"),
                        rs1.getInt("clientid"),
                        rs1.getInt("projectid"));
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Project not found: " + name);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return project;
    }

    public Vector<Project> getAllProjects(){
        int i = 0;
        Vector<Project> projectVector = new Vector<Project>();
        try
        {
            String sql1 = "SELECT * FROM Project;\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                Project project = new Project(rs1.getString("name"),
                        rs1.getString("description"),
                        rs1.getInt("clientid"),
                        rs1.getInt("projectid"));

                projectVector.add(project); // make sure this is correct
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return projectVector;
    }

    public Vector<Project> getAllProjectsForClient(int ClientID){
        int i = 0;
        Vector<Project> projectVector = new Vector<Project>();
        try
        {
            String sql1 = "SELECT * FROM Project WHERE ClientID = " + ClientID + ";\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                Project project = new Project(rs1.getString("name"),
                        rs1.getString("description"),
                        rs1.getInt("clientid"),
                        rs1.getInt("projectid"));

                projectVector.add(project); // make sure this is correct
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return projectVector;
    }

    public void putContact(Contact contact){
        try
        {
            String sql1 = "INSERT INTO Contact (fname, lname, description, " +
                    "phoneNum, emailAddress, ClientID) VALUES (?, ?, ?, ?, ?, ?) RETURNING contactid;\n";
            PreparedStatement st = db.prepareStatement(sql1);
            st.setString(1, contact.getFName());
            st.setString(2, contact.getLName());
            st.setString(3, contact.getDescription());
            st.setString(4, contact.getPhoneNum());
            st.setString(5, contact.getEmailAddress());
            st.setInt(6, contact.getClientID());
            ResultSet res = st.executeQuery();
            if (res.next())
                contact.setContactID(res.getInt("contactid"));
            res.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateContact(Contact contact){
        try
        {
            String sql = "UPDATE Contact\n" +
                    "SET fname = ?, lname = ?, phoneNum = ?, emailAddress = ?, ClientID = ? " +
                    "WHERE contactid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, contact.getFName());
            st.setString(2, contact.getLName());
            st.setString(3, contact.getPhoneNum());
            st.setString(4, contact.getEmailAddress());
            st.setInt(5, contact.getClientID());
            st.setInt(6, contact.getContactID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeContact(Contact contact){
        try
        {
            String sql1 = "DELETE FROM Contact WHERE contactid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, contact.getContactID());
            st1.executeUpdate();
            st1.close();
            contact.setContactID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Contact getContact(Contact cont){
        int i = 0;
        Contact contact = null;
        try
        {
            String sql1 = "SELECT * FROM Contact WHERE ContactID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, cont.getContactID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                contact = new Contact(rs1.getString("fname"), rs1.getString("lname"),
                        rs1.getString("description"),rs1.getString("phonenum"),rs1.getString("emailaddress"));
                contact.setClientID(rs1.getInt("clientid"));
                contact.setContactID(rs1.getInt("contactid"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found: " + cont.getContactID());
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contact;
    }

    public Vector<Contact> getAllContactsForClient(int ClientID){
        Vector<Contact> contactVector = new Vector<Contact>();
        Contact contact = null;
        int i = 0;
        try
        {
            String sql1 = "SELECT * FROM Contact WHERE ClientID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, ClientID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                contact = new Contact(rs1.getString("fname"), rs1.getString("lname"),
                        rs1.getString("description"),rs1.getString("phonenum"),
                        rs1.getString("emailaddress"));
                contact.setClientID(rs1.getInt("clientid"));
                contact.setContactID(rs1.getInt("contactid"));

                contactVector.add(contact);

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found with given ClientID: " + ClientID);
                return null;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactVector;
    }

    public Vector<Contact> getAllContactsForClient(Client client){
        Vector<Contact> contactVector = new Vector<Contact>();
        Contact contact = null;
        int i = 0;
        try
        {
            String sql1 = "SELECT * FROM Contact WHERE ClientID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, client.getClientID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                contact = new Contact(rs1.getString("fname"), rs1.getString("lname"),
                        rs1.getString("description"),rs1.getString("phonenum"),
                        rs1.getString("emailaddress"));
                contact.setClientID(rs1.getInt("clientid"));
                contact.setContactID(rs1.getInt("contactid"));

                contactVector.add(contact);

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found with given ClientID: " + client.getClientID());
                return null;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactVector;
    }

    public void putPayment(Payment payment){
        try
        {
            String sql1 = "INSERT INTO Payment (paymentName, paymentAmount, dateOfPayment, paymentType," +
                    "AccountID, ProjectID) VALUES (?, ?, ?, ?, ?, ?) RETURNING paymentid;\n";
            PreparedStatement st = db.prepareStatement(sql1);
            st.setString(1, payment.getName());
            st.setDouble(2, payment.getAmount());
            st.setDate(3, new java.sql.Date(payment.getDate().getTime())); // http://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date
            st.setInt(4, payment.getPaymentType().ordinal());
            st.setInt(5, payment.getAccountID());
            st.setInt(6, payment.getProjectID());
            ResultSet res = st.executeQuery();
            if (res.next())
                payment.setPaymentID(res.getInt("paymentid"));
            res.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // updatePayment has the proper way to do Date
    public void updatePayment(Payment payment){
        try
        {
            String sql = "UPDATE Payment\n" +
                    "SET paymentname = ?, paymentamount = ?, dateOfPayment = ?, paymentType = ?, AccountID = ? " +
                    "WHERE paymentid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, payment.getName());
            st.setDouble(2, payment.getAmount());
            st.setDate(3, new Date(payment.getDate().getTime()));
            st.setInt(4, payment.getPaymentType().ordinal());
            st.setInt(5, payment.getAccountID());
            st.setInt(6, payment.getPaymentID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removePayment(Payment payment){
        try
        {
            String sql1 = "DELETE FROM Payment WHERE paymentid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, payment.getPaymentID());
            st1.executeUpdate();
            st1.close();
            payment.setPaymentID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Payment getPayment(int PaymentID){
        int i = 0;
        Payment payment = null;
        try
        {
            String sql1 = "SELECT * FROM Payment WHERE paymentid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, PaymentID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                payment = new Payment(rs1.getString("paymentname"),rs1.getDouble("paymentAmount"),
                        rs1.getDate("dateOfPayment"), PaymentType.values()[rs1.getInt("paymentType")]); //paymentType needs work or delete
                payment.setAccountID(rs1.getInt("AccountID"));
                payment.setPaymentID(rs1.getInt("PaymentID"));
                payment.setProjectID(rs1.getInt("ProjectID"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found: " + PaymentID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return payment;
    }

    public Payment getPayment(Payment payment){
        int i = 0;
        Payment payment1 = null;
        try
        {
            String sql1 = "SELECT * FROM Payment WHERE paymentid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, payment.getPaymentID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                payment1 = new Payment(rs1.getString("paymentname"),rs1.getDouble("paymentAmount"),
                        rs1.getDate("dateOfPayment"), PaymentType.values()[rs1.getInt("paymentType")]); //paymentType needs work or delete
                payment1.setAccountID(rs1.getInt("AccountID"));
                payment1.setPaymentID(rs1.getInt("PaymentID"));
                payment1.setProjectID(rs1.getInt("ProjectID"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found: " + payment1.getPaymentID());
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return payment1;
    }

    public Vector<Payment> getAllPaymentsForAccount(int AccountID){
        int i = 0;
        Vector<Payment> paymentVector = new Vector<>();
        Payment payment = null;
        try
        {
            String sql1 = "SELECT * FROM Payment WHERE AccountID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, AccountID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                payment = new Payment(rs1.getString("paymentname"),rs1.getDouble("paymentAmount"),
                        rs1.getDate("dateOfPayment"), PaymentType.values()[rs1.getInt("paymentType")]); //paymentType needs work or delete
                payment.setAccountID(rs1.getInt("AccountID"));
                payment.setPaymentID(rs1.getInt("PaymentID"));
                payment.setProjectID(rs1.getInt("ProjectID"));

                paymentVector.add(payment);
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found for AccountID: " + AccountID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return paymentVector;
    }

    public Vector<Payment> getAllPaymentsForProject(int ProjectID){
        int i = 0;
        Vector<Payment> paymentVector = new Vector<>();
        Payment payment = null;
        try
        {
            String sql1 = "SELECT * FROM Payment WHERE ProjectID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, ProjectID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                payment = new Payment(rs1.getString("paymentname"),rs1.getDouble("paymentAmount"),
                        rs1.getDate("dateOfPayment"), PaymentType.values()[rs1.getInt("paymentType")]); //paymentType needs work or delete
                payment.setAccountID(rs1.getInt("AccountID"));
                payment.setPaymentID(rs1.getInt("PaymentID"));
                payment.setProjectID(rs1.getInt("ProjectID"));

                paymentVector.add(payment);
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Contact not found for ProjectID: " + ProjectID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return paymentVector;
    }

    public void putEmployee(Employee employee){
        try
        {
            String sql = "INSERT INTO Employees (UserID, fname, lname, role) VALUES (?, ?, ?, ?) RETURNING employeeid;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setInt(1, employee.getUserID());
            st.setString(2, employee.getFName());
            st.setString(3, employee.getLName());
            st.setString(4, employee.getRole());

            ResultSet res = st.executeQuery();
            if (res.next())
                employee.setEmployeeID(res.getInt("employeeid"));
            res.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee){
        try
        {
            String sql = "UPDATE Employees \n" +
                    "SET UserID = ?, fname = ?, lname = ?, role = ? " +
                    "WHERE employeeid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setInt(1, employee.getUserID());
            st.setString(2, employee.getFName());
            st.setString(3, employee.getLName());
            st.setString(4, employee.getRole());
            st.setInt(5, employee.getEmployeeID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Employee getEmployee(String fName, String lName){
        int i = 0;
        Employee employee = null;
        try
        {
            String sql1 = "SELECT * FROM Employees WHERE fname = ? AND lname = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setString(1, fName);
            st1.setString(2, lName);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                employee = new Employee(rs1.getString("fname"), rs1.getString("lname"),
                        new Date(1,1,1), rs1.getString("role")); // uses fake date
                employee.setUserID(rs1.getInt("userid"));
                employee.setEmployeeID(rs1.getInt("employeeid"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("User not found: " + fName);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return employee;
    } // invalid

    public Employee getEmployee(Employee employee){
        int i = 0;
        Employee employee1 = null;
        try
        {
            String sql1 = "SELECT * FROM Employees WHERE employeeid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, employee.getEmployeeID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                employee1 = new Employee(rs1.getString("fname"), rs1.getString("lname"),
                        new Date(1,1,1), rs1.getString("role")); // uses fake date *****
                employee1.setUserID(rs1.getInt("userid"));
                employee1.setEmployeeID(rs1.getInt("employeeid"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("User not found: " + employee.getEmployeeID());
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return employee1;
    }

    public Vector<Employee> getAllEmployees(){
        int i = 0;
        Vector<Employee> employeeVector = new Vector<Employee>();
        try
        {
            String sql1 = "SELECT * FROM Employees;\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                Employee employee = new Employee(rs1.getString("fname"), rs1.getString("lname"),
                        new Date(1,1,1),rs1.getString("lname"));
                employee.setEmployeeID(rs1.getInt("employeeid"));
                employee.setUserID(rs1.getInt("userid"));
                employeeVector.add(employee);

                i += 1;
            }

            if(i == 0){
                System.out.println("No Employees Found, Returning Null!");
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return employeeVector;
    }

    public Vector<Employee> getAllEmployeesForUser(User user){
        int i = 0;
        Vector<Employee> employeeVector = new Vector<Employee>();
        try
        {
            String sql1 = "SELECT * FROM Employees WHERE userid = " + user.getUserID() + "\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                Employee employee = new Employee(rs1.getString("fname"), rs1.getString("lname"),
                        new Date(1,1,1),rs1.getString("lname"));
                employee.setEmployeeID(rs1.getInt("employeeid"));
                employee.setUserID(rs1.getInt("userid"));
                employeeVector.add(employee);

                i += 1;
            }

            if(i == 0){
                System.out.println("No Employees Found, Returning Null!");
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return employeeVector;
    }

    public void removeEmployee(Employee employee){
        try
        {
            String sql = "DELETE FROM Employees\n" +
                    "WHERE EmployeeID = \n" +
                    "'" + employee.getEmployeeID() + "';\n";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
            employee.setEmployeeID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void putAccount(Account account){
        try
        {
            String sql = "INSERT INTO Account\n" +
                    "(bankName, bankAddress, amount, UserID) VALUES (?, ?, ?, ?) RETURNING accountid;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, account.getBankName());
            st.setString(2, account.getBankAddress());
            st.setDouble(3, account.getAmount());
            st.setInt(4, account.getUserID());
            ResultSet res = st.executeQuery();
            if (res.next())
                account.setAccountID(res.getInt("accountid"));
            res.close();
            st.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateAccount(Account account){
        try
        {
            String sql = "UPDATE Account \n" +
                    "SET bankName = ?, bankAddress = ?, amount = ?, UserID = ? " +
                    "WHERE accountid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, account.getBankName());
            st.setString(2, account.getBankAddress());
            st.setDouble(3, account.getAmount());
            st.setInt(4, account.getUserID());
            st.setInt(5, account.getAccountID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeAccount(Account account){
        try
        {
            String sql = "DELETE FROM Account\n" +
                    "WHERE AccountID = \n" +
                    "'" + account.getAccountID() +
                    "';\n";
            Statement st = db.createStatement();
            st.execute(sql);
            st.close();
            account.setAccountID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Account getAccount(int AccountID){
        int i = 0;
        Account account = null;
        try
        {
            String sql1 = "SELECT * FROM Account WHERE AccountID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, AccountID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                account = new Account(rs1.getDouble("amount"), rs1.getString("bankname"),
                        rs1.getString("bankaddress"));
                account.setUserID(rs1.getInt("userid"));
                account.setAccountID(rs1.getInt("accountid"));
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Account not found: " + AccountID);
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return account;
    }

    public Account getAccount(Account account){
        int i = 0;
        Account account1 = null;
        try
        {
            String sql1 = "SELECT * FROM Account WHERE AccountID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, account.getAccountID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                account1 = new Account(rs1.getDouble("amount"), rs1.getString("bankname"),
                        rs1.getString("bankaddress"));
                account1.setUserID(rs1.getInt("userid"));
                account1.setAccountID(rs1.getInt("accountid"));
                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Account not found: " + account.getBankName());
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return account;
    }

    public Vector<Account> getAllAccounts(){
        int i = 0;
        Vector<Account> accountVector = new Vector<Account>();
        Account account;
        try
        {
            String sql1 = "SELECT * FROM Account;\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                account = new Account(rs1.getDouble("amount"), rs1.getString("bankname"),
                        rs1.getString("bankaddress"));
                account.setUserID(rs1.getInt("userid"));
                account.setAccountID(rs1.getInt("accountid"));

                accountVector.add(account);
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accountVector;
    }

    public Vector<Account> getAllAccountsFromUser(int UserID){
        int i = 0;
        Vector<Account> accountVector = new Vector<Account>();
        Account account;
        try
        {
            String sql1 = "SELECT * FROM Account WHERE userid = " + UserID + ";\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                account = new Account(rs1.getDouble("amount"), rs1.getString("bankname"),
                        rs1.getString("bankaddress"));
                account.setUserID(rs1.getInt("userid"));
                account.setAccountID(rs1.getInt("accountid"));

                accountVector.add(account);
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accountVector;
    }

    public Vector<Account> getAllAccountsFromUser(User user){
        int i = 0;
        Vector<Account> accountVector = new Vector<Account>();
        Account account;
        try
        {
            String sql1 = "SELECT * FROM Account WHERE userid = " + user.getUserID() + ";\n";
            Statement st1 = db.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()){
                account = new Account(rs1.getDouble("amount"), rs1.getString("bankname"),
                        rs1.getString("bankaddress"));
                account.setUserID(user.getUserID());
                account.setAccountID(rs1.getInt("accountid"));

                accountVector.add(account);
                i += 1;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return accountVector;
    }

    public void putEvent(Event event){
        try
        {
            String sql1 = "INSERT INTO Event (name, eventDate, eventTime, " +
                    "eventDuration, EmployeeID, ProjectID, ContactID) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING eventid;\n";
            PreparedStatement st = db.prepareStatement(sql1);
            st.setString(1, event.getName());
            st.setDate(2, new java.sql.Date(event.getDate().getTime())); // ******------*******
            st.setInt(3, event.getTime());
            st.setInt(4, event.getDuration());
            st.setInt(5, event.getEmployeeID());
            st.setInt(6, event.getProjectID());
            st.setInt(7, event.getContactID());
            ResultSet res = st.executeQuery();
            if (res.next())
                event.setEventID(res.getInt("eventid"));
            res.close();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateEvent(Event event){
        try
        {
            String sql = "UPDATE Event \n" +
                    "SET name = ?, eventDate = ?, eventTime = ?, eventDuration = ?, " +
                    "EmployeeID = ?, ProjectID = ?, ContactID = ? " +
                    "WHERE eventid = ?;\n";
            PreparedStatement st = db.prepareStatement(sql);
            st.setString(1, event.getName());
            st.setDate(2, new java.sql.Date(event.getDate().getTime()));
            st.setInt(3, event.getTime());
            st.setInt(4, event.getDuration());
            st.setInt(5, event.getEmployeeID());
            st.setInt(6, event.getProjectID());
            st.setInt(7, event.getContactID());
            st.setInt(8, event.getEventID());
            st.execute();
            st.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void removeEvent(Event event){
        try
        {
            String sql1 = "DELETE FROM Event WHERE eventid = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, event.getEventID());
            st1.executeUpdate();
            st1.close();
            event.setEventID(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Event getEvent(Event event){
        int i = 0;
        Event event1 = null;
        try
        {
            String sql1 = "SELECT * FROM Event WHERE EventID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, event.getEventID());
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                event1 = new Event(rs1.getDate("eventDate"), rs1.getInt("eventTime"), rs1.getInt("eventduration"),
                        rs1.getString("name"), rs1.getInt("projectid"), rs1.getInt("employeeid"),
                        rs1.getInt("contactid"));
                event1.setEventID(rs1.getInt("eventid"));

                i += 1;
            }
            if (i == 0)
            {
                System.out.println("Event not found: " + event.getEventID());
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return event1;
    }

    public Vector<Event> getAllEventsForProject(int ProjectID){
        Vector<Event> eventVector = new Vector<Event>();
        Event event = null;
        int i = 0;
        try
        {
            String sql1 = "SELECT * FROM Event WHERE ProjectID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, ProjectID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                event = new Event(rs1.getDate("eventDate"), rs1.getInt("eventTime"), rs1.getInt("eventduration"),
                        rs1.getString("name"), rs1.getInt("projectid"), rs1.getInt("employeeid"),
                        rs1.getInt("contactid"));
                event.setEventID(rs1.getInt("eventid"));

                eventVector.add(event);

                i += 1;
            }
            if (i == 0) {
                System.out.println("Event not found with given ProjectID: " + ProjectID);
                return null;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eventVector;
    }

    public Vector<Event> getAllEventsForContact(int ContactID){
        Vector<Event> eventVector = new Vector<Event>();
        Event event = null;
        int i = 0;
        try
        {
            String sql1 = "SELECT * FROM Event WHERE ContactID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, ContactID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                event = new Event(rs1.getDate("eventDate"), rs1.getInt("eventTime"), rs1.getInt("eventduration"),
                        rs1.getString("name"), rs1.getInt("projectid"), rs1.getInt("employeeid"),
                        rs1.getInt("contactid"));
                event.setEventID(rs1.getInt("eventid"));

                eventVector.add(event);

                i += 1;
            }
            if (i == 0) {
                System.out.println("Event not found with given ContactID: " + ContactID);
                return null;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eventVector;
    }

    public Vector<Event> getAllEventsForEmployee(int EmployeeID){
        Vector<Event> eventVector = new Vector<Event>();
        Event event = null;
        int i = 0;
        try
        {
            String sql1 = "SELECT * FROM Event WHERE EmployeeID = ?;\n";
            PreparedStatement st1 = db.prepareStatement(sql1);
            st1.setInt(1, EmployeeID);
            ResultSet rs1 = st1.executeQuery();

            while (rs1.next()){
                event = new Event(rs1.getDate("eventDate"), rs1.getInt("eventTime"), rs1.getInt("eventduration"),
                        rs1.getString("name"), rs1.getInt("projectid"), rs1.getInt("employeeid"),
                        rs1.getInt("contactid"));
                event.setEventID(rs1.getInt("eventid"));

                eventVector.add(event);

                i += 1;
            }
            if (i == 0) {
                System.out.println("Event not found with given EmployeeID: " + EmployeeID);
                return null;
            }

            rs1.close();
            st1.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eventVector;
    }*/
}