package pl.coderslab.entity;

import org.example.DbUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    public static void main(String[] args) {

//        User user9 = new User("9mail@gmail.com", "user9", "n9owyPass");
//        UserDao.create(user9);
//
//        System.out.println("user został utworzony");
        // System.out.println(read(3));
//        UserDao userDao = new UserDao();
//        User userToUpdate = userDao.read(3);
//        userToUpdate.setEmail("2updated@mail.com");
//        userToUpdate.setUsername("updateUserName");
//        userToUpdate.setPassword("updateHaslo");
//        userDao.update(userToUpdate);
//
//        userDao.delete(3);

        UserDao userDao1 = new UserDao();
        User[] all = userDao1.findAll();
        for (User elem :all) {
            System.out.println(elem.printUser());

        }

    }


    private static final String CREATE_USER_QUERY = "INSERT INTO users ( username, email, password) VALUES (?,?,?)";


    private static final String UPDATE_USER = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";


    private static final String SELECT_USER_ID = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_USER_ID = "DELETE FROM users WHERE id = ?";
    private static final String DOWNLOAD_USERS = "SELECT * FROM users";

    public User create(User user) {

        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userId) {

        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement statement = conn.prepareStatement(SELECT_USER_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
//                int id = resultSet.getInt("id");
//                String email = resultSet.getString("email");
//                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                System.out.println("ID: " + id + ", email:  " + email + ", username: " + username);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {

        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.connectWorkshop2()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_ID);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] addToArray(User u, User[] users) {

        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.connectWorkshop2()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(DOWNLOAD_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}







//    public static String getUserAsString(int userId) {
//        User user = read(userId);
//        if (user != null) {
//            return user.printUser();
//        } else {
//            return "User not found.";
//        }



