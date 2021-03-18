package sk.kosickaakademia.fishcompetition.database;

import sk.kosickaakademia.fishcompetition.entity.Fishman;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    private final String INSERTQUERY = "INSERT INTO fishman (fname, lname, age, nameOfTeam) " +
            "VALUES (?, ?, ?, ?)";

    //trying main
    public static void main(String[] args) {
        //new Database().insertNewFishMan(new Fishman("Brano","Novak",18,"rybarik"));
       // List<Fishman> list = new Database().getAllFishman();
        //System.out.println(list.size());
        //new Database().updateNameOfTeam("Chytim_A_Pustim", "ChytimAPustim");
        //System.out.println(new Database().deleteFishman(7));
    }

    //creating an connection
    public Connection getConnection(){
        Connection con;
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String passwordOfDatabase = properties.getProperty("password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, passwordOfDatabase);
            if(con != null){
                System.out.println("Connection is successfully");
                return con;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //closing an connection
    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                System.out.println("Connection has been closed");
            }catch (SQLException e){
                //e.printStackTrace();
                System.out.println("something wrong");
            }
        }
    }

    //inserting a fishman
    public boolean insertNewFishMan(Fishman fishman){
        if(fishman == null){
            return false;
        }
        Connection con = getConnection();
        if(con != null){
            try {
                PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                ps.setString(1, fishman.getFname());
                ps.setString(2, fishman.getLname());
                ps.setInt(3, fishman.getAge());
                ps.setString(4, fishman.getNameOfTeam());
                int result = ps.executeUpdate();
                closeConnection(con);
                System.out.println("NEW USER HAS BEEN ADDED");
                return result==1;
            }catch (SQLException e){
                System.out.println("Something wrong!!!");
            }
        }
        return false;
    }

    //getting all fishman
    public List<Fishman> getAllFishman(){
        List<Fishman> list = new ArrayList<>();
        String query = "SELECT * from fishman";
        Connection con = getConnection();
        if(con == null){
            return null;
        }
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int age = rs.getInt("age");
                String nameOfTeam = rs.getString("nameOfTeam");
                int id = rs.getInt("id");
                Fishman fishman = new Fishman(id, fname, lname, age, nameOfTeam);
                list.add(fishman);
            }
            closeConnection(con);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //updating name of team according to nameOfTeam
    //required an old name and an new name
    public boolean updateNameOfTeam(String oldName, String newName){
        String query = "UPDATE fishman SET nameOfTeam = ? WHERE nameOfTeam = ?";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newName);
            ps.setString(2, oldName);
            int result = ps.executeUpdate();
            closeConnection(con);
            if(result==1){
                return true;
            }
        }catch (Exception e){
            System.out.println("Something wrong!!!");
        }
        return false;

    }

    //deleting fishman according to the nameOfTeam
    public boolean deleteFishman(int deleteId){
        String query = "DELETE FROM fishman WHERE id = ?";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, deleteId);
            int result = ps.executeUpdate();
            System.out.println(result);
            closeConnection(con);
            if(result==1){
                return true;
            }
        }catch (Exception e){
            System.out.println("Something wrong!!!");
        }
        return false;
    }

}
