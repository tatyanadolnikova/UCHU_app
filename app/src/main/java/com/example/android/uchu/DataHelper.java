package com.example.android.uchu;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataHelper {

    private Connection connection;
    public static int USER_ID = -1;

    public boolean getConnection() {
        String url = "jdbc:postgresql://kvm3.79831382332.10371.vps.myjino.ru:49295/uchu"
                + "?sslfactory=org.postgresql.ssl.NonValidatingFactory"
                + "&ssl=true";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, "tdolnikova", "12129595Vv");
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            Log.i("myinfo", String.format("SQL State: %s\nSQL Message: %s\nSQL Error: %s", e.getSQLState(), e.getMessage(), e.getErrorCode()));
        } catch (ClassNotFoundException e) {
            Log.i("myinfo", String.format("Class not found message: %s", e.getMessage()));
        }
        return false;
    }

    public int findUserId(String email, String password) {
        return 0;
    }

    public User getUser(int id) {
        return new User();
    }

    public int addUserToDB(User user) {
        String insertTableSQL = "INSERT INTO USERS"
                + "(email, password, name, surname, birthday, city, personal_info) "
                + "VALUES('"
                + user.getEmail() + "', "
                + user.getPassword() + "', '"
                + user.getName() + "', '"
                + user.getSurname() + "', '"
                + user.getBirthday() + "', '"
                + user.getCity() + "', "
                + user.getInfo() + "')";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertTableSQL);
            while (resultSet.next()) {
                String string = resultSet.getString(0);
                /* Do something with the string */
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateUser(int id) {
        return false;
    }

    public boolean deleteUser(int id) {
        return false;
    }

    public boolean updateSkills(int[] skills) {
        return false;
    }

    public ArrayList<Integer> getUsersBySkill(int skillId) {
        return new ArrayList<>();
    }
}
