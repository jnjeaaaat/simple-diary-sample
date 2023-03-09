package com.example.demo.src.post;

import com.example.demo.src.post.model.PostDiaryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DiaryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost (PostDiaryReq postDiaryReq) {
        String createDiaryQuery = "insert into diary (userId, title, contents, feel, consumption, importation, isOpen, diaryDate) values (?,?,?,?,?,?,?,?)";
        Object[] createDiaryParams =
                        new Object[]{postDiaryReq.getUserId(), postDiaryReq.getTitle(),
                        postDiaryReq.getContents(), postDiaryReq.getFeel(), postDiaryReq.getConsumption(),
                        postDiaryReq.getImportation(), postDiaryReq.getIsOpen(), postDiaryReq.getDiaryDate()};

        this.jdbcTemplate.update(createDiaryQuery, createDiaryParams);

        String countDiaryOfUserQuery = "select count(*) from diary where userId = ? and isDeleted = false";
        int countDiaryOfUserParam = postDiaryReq.getUserId();

        return this.jdbcTemplate.queryForObject(countDiaryOfUserQuery, int.class, countDiaryOfUserParam);
    }
}
