package com.example.demo.src.friend;

import com.example.demo.src.friend.model.DeleteFriendReq;
import com.example.demo.src.friend.model.PostFriendReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FriendDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 친구 요청
    public int requestFriend(PostFriendReq postFriendReq) {
        String requestFriendQuery = "insert into friend (giveUserId, takeUserId) values (?,?)";
        Object[] requestFriendParams = new Object[]{postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()};

        return this.jdbcTemplate.update(requestFriendQuery, requestFriendParams);
    }

    // 친구 삭제
    public int deleteFriend(DeleteFriendReq deleteFriendReq) {
        String deleteFriendQuery = "delete from friend where (giveUserId=? and takeUserId=?) or (giveUserId=? and takeUserId=?)";
        Object[] deleteFriendParams = new Object[]{deleteFriendReq.getUserId(), deleteFriendReq.getAntiUserId(), deleteFriendReq.getAntiUserId(), deleteFriendReq.getUserId()};

        return this.jdbcTemplate.update(deleteFriendQuery, deleteFriendParams);
    }
}
