package com.my.atark.dao.implementation;

import com.my.atark.dao.GenericAbstractDao;
import com.my.atark.dao.IUserDao;
import com.my.atark.dao.Mapper;
import com.my.atark.domain.User;
import com.my.atark.domain.UserRole;
import com.my.atark.exceptions.DataNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl extends GenericAbstractDao<User> implements IUserDao {
    private Connection connection;
    private static String SQL_select_base = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id ";
    private static String SQL_selectAll = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "ORDER BY user_id;";
    private static String SQL_selectById = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_id=?;";
    private static String SQL_selectByName = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_name=?;";
    private static String SQL_selectByRole = "SELECT * FROM users JOIN user_roles ON users.role_id=user_roles.role_id " +
            "WHERE user_roles.role_description=?;";
    private static String SQL_addNew = "INSERT INTO project.users " +
            "(user_name, user_password, user_phone, user_email, user_address, role_id, user_notes) " +
            "VALUES (?,?,?,?,?,?,?)";
    private static String SQL_updateByName = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_name=?;";
    private static String SQL_updateById = "UPDATE project.users SET " +
            "user_name=?, user_password=?, user_phone=?, user_email=?, user_address=?, role_id=?, user_notes=? " +
            "WHERE user_id=?;";
    private static String SQL_deleteUser = "DELETE FROM project.users WHERE user_name=?;";

    private static String SQL_deleteUserById = "DELETE FROM project.users WHERE user_id=?;";

    private Mapper<User, PreparedStatement> mapperToDB = (User user, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getPhoneNumber());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setInt(6, user.getUserRole().ordinal());
        preparedStatement.setString(7, user.getNotes());
    };

    private Mapper<ResultSet, User> mapperFromDB = (ResultSet resultSet, User user) -> {
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("user_password"));
        user.setPhoneNumber(resultSet.getString("user_phone"));
        user.setEmail(resultSet.getString("user_email"));
        user.setAddress(resultSet.getString("user_address"));
        user.setUserRole(UserRole.valueOf(resultSet.getString("role_description")));
        user.setNotes(resultSet.getString("user_notes"));
    };

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
        super.setMapperToDB(mapperToDB);
        super.setMapperFromDB(mapperFromDB);
    }

    @Override
    public Integer calculateUsersNumber() throws DataNotFoundException {
        return calculateRowCounts(connection, "users");
    }

    @Override
    public List<User> findAllUsersInDB() throws DataNotFoundException {
        return findAll(connection, User.class, SQL_selectAll);
    }

    @Override
    public List<User> findUsers(Integer first, Integer offset) throws DataNotFoundException {
        return findAllFromTo(connection, User.class, first, offset, SQL_select_base);
    }

    @Override
    public List<User> findUserByRole(UserRole role) throws DataNotFoundException {
        return findAsListBy(connection, User.class, SQL_selectByRole, role.toString());
    }

    @Override
    public User findUserById(Integer id) throws DataNotFoundException {
        return findBy(connection, User.class, SQL_selectById, id);
    }





    @Override
    public User findUserByName(String name) throws DataNotFoundException {
        return findBy(connection, User.class, SQL_selectByName, name);
    }

    @Override
    public boolean addUserToDB(User user) {
        return addToDB(connection, user, SQL_addNew);
    }

    @Override
    public boolean updateUserInDB(User user) {
        return updateInDB(connection, user, SQL_updateById, 8, user.getId());
    }

    @Override
    public boolean deleteUserFromDB(User user) {
        return deleteFromDB(connection, SQL_deleteUser, user.getName());
    }

    @Override
    public boolean deleteUserFromDBById(Integer id) {
        return deleteFromDB(connection, SQL_deleteUserById, id);
    }
}