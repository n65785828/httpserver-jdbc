package cn.yihua.dao;

import cn.yihua.entity.UserEntity;
import cn.yihua.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public UserEntity findByUsernameAndPassword(String username,String password) {
        try{
            Connection connection = DbUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username=? and password = ?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                UserEntity userEntity = new UserEntity(resultSet.getString("id"),resultSet.getString("username"),resultSet.getString("password"));
                return userEntity;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
