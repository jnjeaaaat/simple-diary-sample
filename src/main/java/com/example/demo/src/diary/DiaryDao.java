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
//    public List<GetDiaryRes> getAllDiary() {
//        String getAllDiaryQuery =
//                "select diary.diaryId, user.userId, user.profileImgUrl, user.nickName, diary.title, diary.contents, diary.feel," +
//                        " diary.consumption, diary.importation, diary.isOpen, diary.isDeleted, diary.diaryDate, diary.createdAt, diary.updatedAt" +
//                        " from diary" +
//                        " left join user on diary.userId = user.userId" +
//                        " where diary.isDeleted=false";
//
//        String getDiaryImgsQuery =
//                "select diaryImgUrl" +
//                        " from diaryImg" +
//                        " left join diary on diary.diaryId = diaryImg.diaryId";
//        ArrayList<String> diaryImgs = this.jdbcTemplate.queryForObject(getDiaryImgsQuery, ArrayList.class);
//
//        return this.jdbcTemplate.query(getAllDiaryQuery,
//                (rs, rowNum) -> new GetDiaryRes(
//                        rs.getInt("diaryId"),
//                        rs.getInt("userId"),
//                        rs.getString("profileImgUrl"),
//                        rs.getString("nickName"),
//                        diaryImgs,
//                        rs.getString("title"),
//                        rs.getString("contents"),
//                        rs.getString("feel"),
//                        rs.getInt("consumption"),
//                        rs.getInt("importation"),
//                        rs.getBoolean("isOpen"),
//                        rs.getBoolean("isDeleted"),
//                        rs.getDate("diaryDate"),
//                        rs.getTimestamp("createdAt").toLocalDateTime(),
//                        rs.getTimestamp("updatedAt").toLocalDateTime())
//        );
//    }

    // 특정 유저 일기 조회
//    public List<GetDiaryRes> getUserDiary(int userId) {
//        String getUserDiaryQuery =
//                "select diary.diaryId, user.userId, user.profileImgUrl, user.nickName, diary.title, diary.contents, diary.feel," +
//                        " diary.consumption, diary.importation, diary.isOpen, diary.isDeleted, diary.diaryDate, diary.createdAt, diary.updatedAt" +
//                    " from diary" +
//                    " left join user on diary.userId = user.userId" +
//                    " where diary.userId=? and diary.isDeleted=false";
//        int getUserDiaryParam = userId;
//
////        List<Integer> listDiaryId = this.jdbcTemplate.queryForList()
//        String getDiaryImgsQuery =
//                "select diaryImgUrl" +
//                        " from diaryImg" +
//                        " left join diary on diary.diaryId = diaryImg.diaryId" +
//                        " where diary.diaryId=diaryImg.diaryId";
//        List<String> diaryImgs = this.jdbcTemplate.queryForList(getDiaryImgsQuery, String.class, null);
//
//        return this.jdbcTemplate.query(getUserDiaryQuery,
//                (rs, rowNum) -> new GetDiaryRes(
//                        rs.getInt("diaryId"),
//                        rs.getInt("userId"),
//                        rs.getString("profileImgUrl"),
//                        rs.getString("nickName"),
//                        diaryImgs,
//                        rs.getString("title"),
//                        rs.getString("contents"),
//                        rs.getString("feel"),
//                        rs.getInt("consumption"),
//                        rs.getInt("importation"),
//                        rs.getBoolean("isOpen"),
//                        rs.getBoolean("isDeleted"),
//                        rs.getDate("diaryDate"),
//                        rs.getTimestamp("createdAt").toLocalDateTime(),
//                        rs.getTimestamp("updatedAt").toLocalDateTime()),
//                getUserDiaryParam);
//    }

    // 특정 일기 조회
    public GetDiaryRes getDiary(int diaryId, int userId) {
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

        String getNumDiaryFromUserQuery =
                "select count(*)" +
                        " from diary" +
                        " where diaryId <= (select diaryId" +
                                            " from diary" +
                                            " where diaryId=?)" +
                                " and userId=? and isDeleted=false";
        Object[] getNumDiaryFromUserParams = new Object[]{diaryId, userId};
        int numDiary = this.jdbcTemplate.queryForObject(getNumDiaryFromUserQuery, int.class, getNumDiaryFromUserParams);

        return this.jdbcTemplate.queryForObject(getDiaryQuery,
                    (rs, rowNum) -> new GetDiaryRes(
                            rs.getInt("diaryId"),
                            numDiary,
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
                            rs.getString("diaryDate"),
                            rs.getTimestamp("createdAt").toLocalDateTime(),
                            rs.getTimestamp("updatedAt").toLocalDateTime()),
                    getDiaryParam);
    }

    // 다이어리 번호로 유저번호 받아오기
    public int getUserIdByDiary(int diaryId) {
        try {
            String getUserIdByDiaryQuery = "select userId from diary where diaryId=? and isDeleted=false";
            int getUserIdByDiaryParam = diaryId;

            return this.jdbcTemplate.queryForObject(getUserIdByDiaryQuery, int.class, getUserIdByDiaryParam);

        } catch (IncorrectResultSizeDataAccessException error) { // 일치하는 userId가 없을 때
            return 0;
        }

    }

    // isDeleted=false 인 diary 갯수 받아오기
    public int countAllDiary() {
        String countAllDiary = "select count(*) from diary where isDeleted=false";

        return this.jdbcTemplate.queryForObject(countAllDiary, int.class);
    }

    /**
     * 일기 정보 수정
     */
    // 제목 변경
    public int modifyTitle(int diaryId, String title) {
        String  modifyTitleQuery = "update diary set title = ? where diaryId = ? ";
        Object[] modifyTitleParams = new Object[]{title, diaryId};

        return this.jdbcTemplate.update(modifyTitleQuery, modifyTitleParams);
    }
    // 내용 변경
    public int modifyContents(int diaryId, String contents) {
        String  modifyContentsQuery = "update diary set contents = ? where diaryId = ? ";
        Object[] modifyContentsParams = new Object[]{contents, diaryId};

        return this.jdbcTemplate.update(modifyContentsQuery, modifyContentsParams);
    }
    // 기분 변경
    public int modifyFeel(int diaryId, String feel) {
        String  modifyFeelQuery = "update diary set feel = ? where diaryId = ? ";
        Object[] modifyFeelParams = new Object[]{feel, diaryId};

        return this.jdbcTemplate.update(modifyFeelQuery, modifyFeelParams);
    }
    // 지출 변경
    public int modifyConsumption(int diaryId, int consumption) {
        String  modifyConsumptionQuery = "update diary set consumption = ? where diaryId = ? ";
        Object[] modifyConsumptionParams = new Object[]{consumption, diaryId};

        return this.jdbcTemplate.update(modifyConsumptionQuery, modifyConsumptionParams);
    }
    // 수입 변경
    public int modifyImportation(int diaryId, int importation) {
        String  modifyImportationQuery = "update diary set importation = ? where diaryId = ? ";
        Object[] modifyImportationParams = new Object[]{importation, diaryId};

        return this.jdbcTemplate.update(modifyImportationQuery, modifyImportationParams);
    }
    // 일기 오픈 변경
    public int modifyIsOpen(int diaryId, Boolean isOpen) {
        String  modifyIsOpenQuery = "update diary set isOpen = ? where diaryId = ? ";
        Object[] modifyIsOpenParams = new Object[]{isOpen, diaryId};

        return this.jdbcTemplate.update(modifyIsOpenQuery, modifyIsOpenParams);
    }
    // 일기 상태 변경
    public int modifyIsDeleted(int diaryId, Boolean isDeleted) {
        String  modifyIsDeletedQuery = "update diary set isDeleted = ? where diaryId = ? ";
        Object[] modifyIsDeletedParams = new Object[]{isDeleted, diaryId};

        return this.jdbcTemplate.update(modifyIsDeletedQuery, modifyIsDeletedParams);
    }
    // 일기 작성날짜 변경
    public int modifyDiaryDate(int diaryId, String diaryDate) {
        String  modifyDiaryDateQuery = "update diary set diaryDate = ? where diaryId = ? ";
        Object[] modifyDiaryDateParams = new Object[]{diaryDate, diaryId};

        return this.jdbcTemplate.update(modifyDiaryDateQuery, modifyDiaryDateParams);
    }
}
