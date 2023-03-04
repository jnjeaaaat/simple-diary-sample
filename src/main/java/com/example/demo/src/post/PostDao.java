package com.example.demo.src.post;

import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PostDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost (PostPostReq postPostReq) {
        String createPostQuery = "insert into post (userId, title, contents, feel, consumption, importation, isOpen, postDate) values (?,?,?,?,?,?,?,?)";
        Object[] createPostParams =
                        new Object[]{postPostReq.getUserId(), postPostReq.getTitle(),
                        postPostReq.getContents(), postPostReq.getFeel(), postPostReq.getConsumption(),
                        postPostReq.getImportation(), postPostReq.getIsOpen(), postPostReq.getPostDate()};

        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String countPostOfUserQuery = "select count(*) from post where post.userId = ?";
        int countPostOfUserParam = postPostReq.getUserId();

        return this.jdbcTemplate.queryForObject(countPostOfUserQuery, int.class, countPostOfUserParam);
    }
}
