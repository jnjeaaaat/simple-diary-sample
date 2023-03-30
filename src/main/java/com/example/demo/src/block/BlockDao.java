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

    // 유저 차단
    public int blockUser(PostBlockReq postBlockReq) {
        String blockUserQuery = "insert into block (userId, blockUserId) values (?,?)";
        Object[] blockUserParams = new Object[]{postBlockReq.getUserId(), postBlockReq.getBlockUserId()};

        this.jdbcTemplate.update(blockUserQuery, blockUserParams);

        return postBlockReq.getBlockUserId();
    }

    // 이미 차단 했는지
    public int isBlocked(int userId, int blockUserId) {
        String isBlockedQuery = "select exists(select * from block where userId=? and blockUserId=?)";
        Object[] isBlockedParams = new Object[]{userId, blockUserId};

        return this.jdbcTemplate.queryForObject(isBlockedQuery, int.class, isBlockedParams);
    }
}
