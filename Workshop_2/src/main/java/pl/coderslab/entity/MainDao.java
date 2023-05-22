package pl.coderslab.entity;

import pl.coderslab.entity.User;

public class MainDao {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        User user3 = new User();
        user3.setUserName("arek");
        user3.setEmail("arkadius.jozwiak@coderslab.pl");
        user3.setPassword("pass");
        userDao.create(user3);

        User read = userDao.read(1);
        System.out.println(read.printUser());

        User userToUpdate = userDao.read(1);
        userToUpdate.setUserName("zmianaNazwy");
        userToUpdate.setEmail("nowyemail");
        userToUpdate.setPassword("nowe hasło");

        //      userDao.update(userToUpdate);

        UserDao userDao1 = new UserDao();
        userDao1.findAll();

    }
}