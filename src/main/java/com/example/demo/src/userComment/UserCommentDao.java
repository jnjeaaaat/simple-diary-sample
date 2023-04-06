package com.example.demo.src.userComment;

import com.example.demo.src.userComment.model.PostUserCommentReq;
import com.example.demo.src.userComment.model.PostUserCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
        String writeCommentQuery = "insert into userComment (userId, takeUserId, comment) values (?,?,?)";
        Object[] writeCommentParams = new Object[]{postUserCommentReq.getUserId(), postUserCommentReq.getTakeUserId(), postUserCommentReq.getComment()};

        this.jdbcTemplate.update(writeCommentQuery, writeCommentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int lastInsertId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

        String getNowCommentInformQuery = "select comment, date_format(createdAt, '%Y년 %m월 %d일') as createdAt from userComment where userCommentId=?";
        int getNowCommentInformParam = lastInsertId;

        return this.jdbcTemplate.queryForObject(getNowCommentInformQuery,
                (rs, rowNum) -> new PostUserCommentRes(
                        rs.getString("comment"),
                        rs.getString("createdAt")),
                getNowCommentInformParam);
    }
}
