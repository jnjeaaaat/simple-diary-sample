package com.example.demo.src.diary;

import com.example.demo.src.diary.model.PostDiaryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;

@Repository
public class DiaryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost (PostDiaryReq postDiaryReq) {

        // diary 작성 쿼리
        String createDiaryQuery = "insert into diary (userId, title, contents, feel, consumption, importation, isOpen, diaryDate) values (?,?,?,?,?,?,?,?)";
        Object[] createDiaryParams =
                        new Object[]{postDiaryReq.getUserId(), postDiaryReq.getTitle(),
                        postDiaryReq.getContents(), postDiaryReq.getFeel(), postDiaryReq.getConsumption(),
                        postDiaryReq.getImportation(), postDiaryReq.getIsOpen(), postDiaryReq.getDiaryDate()};
        this.jdbcTemplate.update(createDiaryQuery, createDiaryParams);

        // 마지막으로 들어간 diaryId
        String lastInsertIdQuery = "select last_insert_id()";
        int lastInsertDiaryId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        System.out.println(lastInsertDiaryId);

        String inputDiaryImgQuery = "insert into diaryImg (diaryId, diaryImgUrl) values (?,?)";
        for (int i = 0; i < postDiaryReq.getDiaryImgs().size(); i++) {
            Object[] inputDiaryImgParams = new Object[]{lastInsertDiaryId, postDiaryReq.getDiaryImgs().get(i)};
            this.jdbcTemplate.update(inputDiaryImgQuery, inputDiaryImgParams);
        }

        String countDiaryOfUserQuery = "select count(*) from diary where userId = ? and isDeleted = false";
        int countDiaryOfUserParam = postDiaryReq.getUserId();

        return this.jdbcTemplate.queryForObject(countDiaryOfUserQuery, int.class, countDiaryOfUserParam);
    }
}
