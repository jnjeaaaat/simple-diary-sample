package com.example.demo.src.block;

import com.example.demo.src.block.model.DeleteBlockReq;
import com.example.demo.src.block.model.GetBlockRes;
import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BlockDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 유저 차단
    public int blockUser(PostBlockReq postBlockReq) {
        // 친구목록에서 삭제부터
        String deleteFriendQuery = "delete from friend where (giveUserId=? and takeUserId=?) or (giveUserId=? and takeUserId=?)";
        Object[] deleteFriendParams = new Object[]{postBlockReq.getUserId(), postBlockReq.getBlockUserId()};

        this.jdbcTemplate.update(deleteFriendQuery, deleteFriendParams);

        // 차단 테이블에 추가
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

    // 유저 차단 해제
    public int unBlockUser(DeleteBlockReq deleteBlockReq) {
        String unBlockUserQuery = "delete from block where userId=? and blockUserId=?";
        Object[] unBlockUserParams = new Object[]{deleteBlockReq.getUserId(), deleteBlockReq.getBlockUserId()};

        return this.jdbcTemplate.update(unBlockUserQuery, unBlockUserParams);
    }

    // 차단 유저 조회
    public List<GetBlockRes> getAllBlockUser(int userId) {
        String getAllBlockUserQuery = "select block.userId, block.blockUserId, user.profileImgUrl, user.nickName from block left join user on block.blockUserId = user.userId where block.userId=?";
        int getAllBlockUserParam = userId;

        return this.jdbcTemplate.query(getAllBlockUserQuery,
                (rs, rowNum) -> new GetBlockRes(
                        rs.getInt("userId"),
                        rs.getInt("blockUserId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName"))
                ,getAllBlockUserParam);
    }
}
