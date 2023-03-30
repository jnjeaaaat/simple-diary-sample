package com.example.demo.src.block;

import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class BlockDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int blockUser(PostBlockReq postBlockReq) {
        String blockUserQuery = "insert into block (userId, blockUserId) values (?,?)";
        Object[] blockUserParams = new Object[]{postBlockReq.getUserId(), postBlockReq.getBlockUserId()};

        this.jdbcTemplate.update(blockUserQuery, blockUserParams);

        return postBlockReq.getBlockUserId();
    }
}
