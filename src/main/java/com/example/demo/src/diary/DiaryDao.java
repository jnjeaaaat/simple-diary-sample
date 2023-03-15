package com.example.demo.src.diary;

import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.diary.model.PostDiaryReq;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        // diaryImg insert
        String inputDiaryImgQuery = "insert into diaryImg (diaryId, diaryImgUrl) values (?,?)";
        for (int i = 0; i < postDiaryReq.getDiaryImg().getDiaryImgUrls().size(); i++) {
            Object[] inputDiaryImgParams = new Object[]{lastInsertDiaryId, postDiaryReq.getDiaryImg().getDiaryImgUrls().get(i)};
            this.jdbcTemplate.update(inputDiaryImgQuery, inputDiaryImgParams);
        }

        String countDiaryOfUserQuery = "select count(*) from diary where userId = ? and isDeleted = false";
        int countDiaryOfUserParam = postDiaryReq.getUserId();

        return this.jdbcTemplate.queryForObject(countDiaryOfUserQuery, int.class, countDiaryOfUserParam);
    }

    public List<GetDiaryRes> getAllDiary() {
        String getAllDiaryQuery = "select * from diary";
        return this.jdbcTemplate.query(getAllDiaryQuery,
                (rs, rowNum) -> new GetDiaryRes(
                        rs.getInt("diaryId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("feel"),
                        rs.getInt("consumption"),
                        rs.getInt("importation"),
                        rs.getBoolean("isOpen"),
                        rs.getBoolean("isDeleted"),
                        rs.getDate("diaryDate"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime())
        );
    }
}
