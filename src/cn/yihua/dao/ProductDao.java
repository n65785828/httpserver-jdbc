package cn.yihua.dao;

import cn.yihua.entity.Product;
import cn.yihua.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductDao {
    public int save(Product product)  {
        try{
            Connection connection = DbUtil.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into product (id,name,price,username) value (?,?,?,?)");
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getPrice());
            preparedStatement.setString(4, product.getUsername());
            int updatedColumn = preparedStatement.executeUpdate();
            connection.commit();
            return updatedColumn;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
}
