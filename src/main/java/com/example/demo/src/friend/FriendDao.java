package com.example.demo.src.friend;

import com.example.demo.src.friend.model.DeleteFriendReq;
import com.example.demo.src.friend.model.GetFriendRes;
import com.example.demo.src.friend.model.PostFriendReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FriendDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 친구 요청
    public int requestFriend(int giveUserId, int takeUserId) {
        String requestFriendQuery = "insert into friend (giveUserId, takeUserId) values (?,?)";
        Object[] requestFriendParams = new Object[]{giveUserId, takeUserId};

        return this.jdbcTemplate.update(requestFriendQuery, requestFriendParams);
    }

    // 이미 친구 요청 했는지
    public int isExistRequestFriend(int giveUserId, int takeUserId) {
        String isExistRequestFriendQuery = "select exists(select * from friend where giveUserId=? and takeUserId=?)";
        Object[] isExistRequestFriendParams = new Object[]{giveUserId, takeUserId};

        return this.jdbcTemplate.queryForObject(isExistRequestFriendQuery, int.class, isExistRequestFriendParams);
    }

    // 이미 친구 사이 인지
    public int isFriends(int giveUserId, int takeUserId) {
        String isFriendsQuery = "select exists(select * from friend where ((giveUserId=? and takeUserId=?) or (giveUserId=? and takeUserId=?)) and isFriends=true)";
        Object[] isFriendsParams = new Object[]{giveUserId, takeUserId, takeUserId, giveUserId};

        return this.jdbcTemplate.queryForObject(isFriendsQuery, int.class, isFriendsParams);
    }

    // 친구 삭제
    public int deleteFriend(int userId, int antiUserId) {
        String deleteFriendQuery = "delete from friend where (giveUserId=? and takeUserId=?) or (giveUserId=? and takeUserId=?)";
        Object[] deleteFriendParams = new Object[]{userId, antiUserId, antiUserId, userId};

        return this.jdbcTemplate.update(deleteFriendQuery, deleteFriendParams);
    }

    // 친구 요청 수락
    public int acceptFriend(int giveUserId, int takeUserId) {
        String acceptFriendQuery = "update friend set isFriends=true where giveUserId=? and takeUserId=?";
        Object[] acceptFriendParams = new Object[]{giveUserId, takeUserId};

        return this.jdbcTemplate.update(acceptFriendQuery, acceptFriendParams);
    }

    // 친구 목록 조회
    public List<GetFriendRes> getMyFriends(int userId) {
        String getMyFriendsQuery = "select friend.friendId, user.userId, user.profileImgUrl, user.nickName " +
                "from friend " +
                "left join user on user.userId=friend.takeUserId or user.userId=friend.giveUserId " +
                "where (friend.giveUserId=? or friend.takeUserId=?) and isFriends=true and user.userId!=? and user.status='ACTIVE'";
        Object[] getMyFriendsParams = new Object[]{userId, userId, userId};

        return this.jdbcTemplate.query(getMyFriendsQuery,
                (rs, rowNum) -> new GetFriendRes(
                        rs.getInt("friendId"),
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName")),
                getMyFriendsParams);
    }
}
