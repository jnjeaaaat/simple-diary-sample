package com.example.demo.src.userComment;

import com.example.demo.src.userComment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserCommentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 방명록 작성
     * @param postUserCommentReq
     * @return comment, createdAt
     */
    public PostUserCommentRes writeComment(PostUserCommentReq postUserCommentReq) {
        // 테이블 삽입
        String writeCommentQuery = "insert into userComment (userId, takeUserId, comment) values (?,?,?)";
        Object[] writeCommentParams = new Object[]{postUserCommentReq.getUserId(), postUserCommentReq.getTakeUserId(), postUserCommentReq.getComment()};

        this.jdbcTemplate.update(writeCommentQuery, writeCommentParams);

        // 마지막 id
        String lastInsertIdQuery = "select last_insert_id()";
        int lastInsertId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

        // 데이터 타입 맞추기
        String getNowCommentInformQuery = "select comment, date_format(createdAt, '%Y년 %m월 %d일 %T') as createdAt from userComment where userCommentId=?";
        int getNowCommentInformParam = lastInsertId;

        return this.jdbcTemplate.queryForObject(getNowCommentInformQuery,
                (rs, rowNum) -> new PostUserCommentRes(
                        rs.getString("comment"),
                        rs.getString("createdAt")),
                getNowCommentInformParam);
    }

    /**
     * 방명록 수정
     * @param userCommentId
     * @param patchUserCommentReq
     * @return comment, updatedAt
     */
    public PatchUserCommentRes modifyComment(int userCommentId, PatchUserCommentReq patchUserCommentReq) {
        String modifyCommentQuery = "update userComment set comment=? where userCommentId=?";
        Object[] modifyCommentParams = new Object[]{patchUserCommentReq.getComment(), userCommentId};

        this.jdbcTemplate.update(modifyCommentQuery, modifyCommentParams);

        String getModifiedCommentInformQuery = "select comment, date_format(updatedAt, '%Y년 %m월 %d일 %T') as updatedAt from userComment where userCommentId=?";
        int getModifiedCommentInformParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getModifiedCommentInformQuery,
                (rs, rowNum) -> new PatchUserCommentRes(
                        rs.getString("comment"),
                        rs.getString("updatedAt")),
                getModifiedCommentInformParam);
    }

    /**
     * 방명록 하트누르기
     * @param userCommentId
     * @return Boolean
     */
    public Boolean heartComment(int userCommentId) {
        String switchHeartCommentQuery = "update userComment set heart = if(heart=false, true, false) where userCommentId=?";
        int switcHeartCommentParam = userCommentId;

        this.jdbcTemplate.update(switchHeartCommentQuery, switcHeartCommentParam);

        String getHeartStatusQuery = "select heart from userComment where userCommentId=?";
        int getHeartStatusParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getHeartStatusQuery, Boolean.class, getHeartStatusParam);
    }

    /**
     * 방명록 조회
     * @param userCommentId
     * @return GetUserCommentRes
     */
    public GetUserCommentRes getCommentById(int userCommentId) {
        String getCommentByIdQuery =
                "select userComment.userCommentId, user.userId, user.profileImgUrl, user.nickName, userComment.takeUserId, userComment.comment, " +
                        "userComment.heart, userComment.isDeleted, " +
                        "date_format(userComment.createdAt, '%Y년 %m월 %d일 %T') as createdAt, " +
                        "date_format(userComment.updatedAt, '%Y년 %m월 %d일 %T') as updatedAt " +
                        "from userComment " +
                        "left join user on user.userId = userComment.userId " +
                        "where userComment.userCommentId=? and userComment.isDeleted=false";
        int getCommentByIdParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getCommentByIdQuery,
                (rs, rowNum) -> new GetUserCommentRes(
                        rs.getInt("userCommentId"),
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName"),
                        rs.getInt("takeUserId"),
                        rs.getString("comment"),
                        rs.getBoolean("heart"),
                        rs.getBoolean("isDeleted"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt")),
                getCommentByIdParam);
    }

    /**
     * 유저의 방명록 조회
     * @param takeUserId
     * @return List(GetUserCommentRes)
     */
    public List<GetUserCommentRes> getComments(int takeUserId, int page) {
        String getCommentsQuery =
                "select userComment.userCommentId, user.userId, user.profileImgUrl, user.nickName, userComment.takeUserId, userComment.comment, " +
                        "userComment.heart, userComment.isDeleted, " +
                        "date_format(userComment.createdAt, '%Y년 %m월 %d일 %T') as createdAt, " +
                        "date_format(userComment.updatedAt, '%Y년 %m월 %d일 %T') as updatedAt " +
                        "from userComment " +
                        "left join user on user.userId = userComment.userId " +
                        "where userComment.takeUserId=? and userComment.isDeleted=false and user.status='ACTIVE' " +
                        "limit 10 offset ?";
//                        "and date(userComment.createdAt) = date(now())";
//        int getCommentsParam = takeUserId;
        Object[] getCommentsParams = new Object[]{takeUserId, page*10};

        return this.jdbcTemplate.query(getCommentsQuery,
                (rs, rowNum) -> new GetUserCommentRes(
                        rs.getInt("userCommentId"),
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName"),
                        rs.getInt("takeUserId"),
                        rs.getString("comment"),
                        rs.getBoolean("heart"),
                        rs.getBoolean("isDeleted"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt")),
                getCommentsParams);
    }

    /**
     * 방명록 작성 유저 id
     * @param userCommentId
     * @return int userId
     */
    public int getUserIdFromComment(int userCommentId) {
        String getUserIdFromCommentQuery = "select userId from userComment where userCommentId=?";
        int getUserIdFromCommentParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getUserIdFromCommentQuery, int.class, getUserIdFromCommentParam);
    }

    /**
     * 방명록 받은 유저 id
     * @param userCommentId
     * @return int takeUserId
     */
    public int getTakeUserIdComment(int userCommentId) {
        String getTakeUserIdCommentQuery = "select takeUserId from userComment where userCommentId=?";
        int getTakeUserIdCommentParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getTakeUserIdCommentQuery, int.class, getTakeUserIdCommentParam);
    }

    /**
     * 방명록 삭제
     * @param userCommentId
     * @return Boolean
     */
    public Boolean deleteComment(int userCommentId) {
        String deleteCommentQuery = "update userComment set isDeleted=true where userCommentId=?";
        int deleteCommentParam = userCommentId;

        this.jdbcTemplate.update(deleteCommentQuery, deleteCommentParam);

        String getIsDeletedCommentQuery = "select isDeleted from userComment where userCommentId=?";
        int getIsDeletedCommentParam = userCommentId;

        return this.jdbcTemplate.queryForObject(getIsDeletedCommentQuery, Boolean.class, getIsDeletedCommentParam);
    }

    /**
     * 방명록이 삭제된 상태인지
     * @param userCommentId
     * @return Boolean
     */
    public Boolean checkIsDeletedComment(int userCommentId) {
        String checkIsDeletedCommentQuery = "select isDeleted from userComment where userCommentId=?";
        int checkIsDeletedCommentParam = userCommentId;

        return this.jdbcTemplate.queryForObject(checkIsDeletedCommentQuery, Boolean.class, checkIsDeletedCommentParam);
    }
}
