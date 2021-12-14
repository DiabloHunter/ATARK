package com.my.atark.service.implementation;

import com.my.atark.dao.DaoFactory;
import com.my.atark.dao.DataBaseSelector;
import com.my.atark.dao.IUserDao;
import com.my.atark.domain.User;
import com.my.atark.domain.UserRole;

import com.my.atark.exceptions.*;
import com.my.atark.service.Button;
import com.my.atark.service.IUserServ;
import org.apache.log4j.Logger;

import java.util.List;

public class UserService implements IUserServ {

    private static final DataBaseSelector source = DataBaseSelector.MY_SQL;
    private static final Logger log = Logger.getLogger(UserService.class);
    private static DaoFactory daoFactory;
    private static IUserDao userDao;

    static {
        try {
            daoFactory = DaoFactory.getDaoFactory(source);
            userDao = daoFactory.getUserDao();
        } catch (IncorrectPropertyException | DataBaseConnectionException | DataBaseNotSupportedException ex) {
            log.error(ex);
        }
    }

    @Button
    @Override
    public User findUser(String name, String password) throws UnknownUserException {
        User user;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserByName(name);
            daoFactory.close();
            if (!user.getPassword().equals(password))
                throw new UnknownUserException();
            return user;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }
    @Button
    @Override
    public User findUserById(Integer id) throws UnknownUserException{
        User user;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            user = userDao.findUserById(id);
            daoFactory.close();
            return user;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    @Button
    @Override
    public List<User> findAllUsers() throws UnknownUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findAllUsersInDB();
            daoFactory.close();
            return users;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    @Button
    @Override
    public List<User> findUsers(Integer from, Integer offset) throws UnknownUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findUsers(from, offset);
            daoFactory.close();
            return users;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    @Button
    @Override
    public List<User> findUsersByRole(UserRole userRole) throws UnknownUserException {
        List<User> users;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            users = userDao.findUserByRole(userRole);
            daoFactory.close();
            return users;
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
            throw new UnknownUserException();
        }
    }

    /** User validation method to check user before storing in DB */

    private boolean validateUserData(User user) {
        return !(user.getName() == null
                || user.getName().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()
                || user.getUserRole() == null);
    }


    /** Data access and storing methods */

    @Override
    public Integer calculateUsersNumber() {
        Integer result = 0;
        try {
            daoFactory.beginTransaction();
            userDao = daoFactory.getUserDao();
            result = userDao.calculateUsersNumber();
            daoFactory.commitTransaction();
        } catch (DataBaseConnectionException | DataNotFoundException ex) {
            log.error(ex);
        }
        return result;
    }

    @Button
    @Override
    public synchronized boolean addUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.addUserToDB(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    @Button
    @Override
    public synchronized boolean updateUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.updateUserInDB(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    @Button
    @Override
    public synchronized boolean deleteUser(User user) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = validateUserData(user) && userDao.deleteUserFromDB(user);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }

    @Button
    @Override
    public synchronized boolean deleteUserById(Integer id) {
        boolean result;
        try {
            daoFactory.open();
            userDao = daoFactory.getUserDao();
            result = userDao.deleteUserFromDBById(id);
            daoFactory.close();
        } catch (DataBaseConnectionException ex) {
            log.error(ex);
            return false;
        }
        return result;
    }
}