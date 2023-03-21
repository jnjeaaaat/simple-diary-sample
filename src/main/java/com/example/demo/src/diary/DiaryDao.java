package com.example.demo.src.diary;

import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.diary.model.PostDiaryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

    // 일기 쓰기
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

    // 전체 일기 조회
    public List<GetDiaryRes> getAllDiary() {
        String getAllDiaryQuery =
                "select diary.diaryId, user.userId, user.profileImgUrl, user.nickName, diary.title, diary.contents, diary.feel," +
                        " diary.consumption, diary.importation, diary.isOpen, diary.isDeleted, diary.diaryDate, diary.createdAt, diary.updatedAt" +
                        " from diary" +
                        " left join user on diary.userId = user.userId" +
                        " where diary.isDeleted=false";

        String getDiaryImgsQuery =
                "select diaryImgUrl" +
                        " from diaryImg" +
                        " left join diary on diary.diaryId = diaryImg.diaryId";
        ArrayList<String> diaryImgs = this.jdbcTemplate.queryForObject(getDiaryImgsQuery, ArrayList.class);

        return this.jdbcTemplate.query(getAllDiaryQuery,
                (rs, rowNum) -> new GetDiaryRes(
                        rs.getInt("diaryId"),
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName"),
                        diaryImgs,
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

    // 특정 유저 일기 조회
    public List<GetDiaryRes> getUserDiary(int userId) {
        String getUserDiaryQuery =
                "select diary.diaryId, user.userId, user.profileImgUrl, user.nickName, diary.title, diary.contents, diary.feel," +
                        " diary.consumption, diary.importation, diary.isOpen, diary.isDeleted, diary.diaryDate, diary.createdAt, diary.updatedAt" +
                    " from diary" +
                    " left join user on diary.userId = user.userId" +
                    " where diary.userId=? and diary.isDeleted=false";
        int getUserDiaryParam = userId;

//        List<Integer> listDiaryId = this.jdbcTemplate.queryForList()
        String getDiaryImgsQuery =
                "select diaryImgUrl" +
                        " from diaryImg" +
                        " left join diary on diary.diaryId = diaryImg.diaryId" +
                        " where diary.diaryId=diaryImg.diaryId";
        List<String> diaryImgs = this.jdbcTemplate.queryForList(getDiaryImgsQuery, String.class, null);

        return this.jdbcTemplate.query(getUserDiaryQuery,
                (rs, rowNum) -> new GetDiaryRes(
                        rs.getInt("diaryId"),
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("nickName"),
                        diaryImgs,
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("feel"),
                        rs.getInt("consumption"),
                        rs.getInt("importation"),
                        rs.getBoolean("isOpen"),
                        rs.getBoolean("isDeleted"),
                        rs.getDate("diaryDate"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()),
                getUserDiaryParam);
    }

    // 특정 일기 조회
    public GetDiaryRes getDiary(int diaryId) {
        String getDiaryQuery =
                "select diary.diaryId, user.userId, user.profileImgUrl, user.nickName, diary.title, diary.contents, diary.feel," +
                        " diary.consumption, diary.importation, diary.isOpen, diary.isDeleted, diary.diaryDate, diary.createdAt, diary.updatedAt" +
                        " from diary" +
                        " left join user on diary.userId = user.userId" +
                        " where diary.diaryId=? and isDeleted=false";
        int getDiaryParam = diaryId;

        String getDiaryImgsQuery =
                "select diaryImgUrl" +
                        " from diaryImg" +
                        " left join diary on diary.diaryId = diaryImg.diaryId" +
                        " where diary.diaryId = ?";
        int getDiaryImgsParam = diaryId;
        List<String> diaryImgs = this.jdbcTemplate.queryForList(getDiaryImgsQuery, String.class, getDiaryImgsParam);

        return this.jdbcTemplate.queryForObject(getDiaryQuery,
                    (rs, rowNum) -> new GetDiaryRes(
                            rs.getInt("diaryId"),
                            rs.getInt("userId"),
                            rs.getString("profileImgUrl"),
                            rs.getString("nickName"),
                            diaryImgs,
                            rs.getString("title"),
                            rs.getString("contents"),
                            rs.getString("feel"),
                            rs.getInt("consumption"),
                            rs.getInt("importation"),
                            rs.getBoolean("isOpen"),
                            rs.getBoolean("isDeleted"),
                            rs.getDate("diaryDate"),
                            rs.getTimestamp("createdAt").toLocalDateTime(),
                            rs.getTimestamp("updatedAt").toLocalDateTime()),
                    getDiaryParam);
    }

    // 다이어리 번호로 유저번호 받아오기
    public int getUserIdByDiary(int diaryId) {
        String getUserIdByDiaryQuery = "select userId from diary where diaryId=? and isDeleted=false";
        int getUserIdByDiaryParam = diaryId;

        return this.jdbcTemplate.queryForObject(getUserIdByDiaryQuery, int.class, getUserIdByDiaryParam);
    }

    // isDeleted=false 인 diary 갯수 받아오기
    public int countAllDiary() {
        String countAllDiary = "select count(*) from diary where isDeleted=false";

        return this.jdbcTemplate.queryForObject(countAllDiary, int.class);
    }
}
