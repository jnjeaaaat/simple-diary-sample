package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

/**
 * DAO란?
 * 데이터베이스 관련 작업을 전담하는 클래스
 * 데이터베이스에 연결하여, 입력 , 수정, 삭제, 조회 등의 작업을 수행
 */
public class UserDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    /**
     * DAO관련 함수코드의 전반부는 크게 String ~~~Query와 Object[] ~~~~Params, jdbcTemplate함수로 구성되어 있습니다.(보통은 동적 쿼리문이지만, 동적쿼리가 아닐 경우, Params부분은 없어도 됩니다.)
     * Query부분은 DB에 SQL요청을 할 쿼리문을 의미하는데, 대부분의 경우 동적 쿼리(실행할 때 값이 주입되어야 하는 쿼리) 형태입니다.
     * 그래서 Query의 동적 쿼리에 입력되어야 할 값들이 필요한데 그것이 Params부분입니다.
     * Params부분은 클라이언트의 요청에서 제공하는 정보(~~~~Req.java에 있는 정보)로 부터 getXXX를 통해 값을 가져옵니다. ex) getEmail -> email값을 가져옵니다.
     *      Notice! get과 get의 대상은 카멜케이스로 작성됩니다. ex) item -> getItem, password -> getPassword, email -> getEmail, userIdx -> getUserIdx
     * 그 다음 GET, POST, PATCH 메소드에 따라 jabcTemplate의 적절한 함수(queryForObject, query, update)를 실행시킵니다(DB요청이 일어납니다.).
     *      Notice!
     *      POST, PATCH의 경우 jdbcTemplate.update
     *      GET은 대상이 하나일 경우 jdbcTemplate.queryForObject, 대상이 복수일 경우, jdbcTemplate.query 함수를 사용합니다.
     * jdbcTeplate이 실행시킬 때 Query 부분과 Params 부분은 대응(값을 주입)시켜서 DB에 요청합니다.
     * <p>
     * 정리하자면 < 동적 쿼리문 설정(Query) -> 주입될 값 설정(Params) -> jdbcTemplate함수(Query, Params)를 통해 Query, Params를 대응시켜 DB에 요청 > 입니다.
     * <p>
     * <p>
     * DAO관련 함수코드의 후반부는 전반부 코드를 실행시킨 후 어떤 결과값을 반환(return)할 것인지를 결정합니다.
     * 어떠한 값을 반환할 것인지 정의한 후, return문에 전달하면 됩니다.
     * ex) return this.jdbcTemplate.query( ~~~~ ) -> ~~~~쿼리문을 통해 얻은 결과를 반환합니다.
     */

    /**
     * 참고 링크
     * https://jaehoney.tistory.com/34 -> JdbcTemplate 관련 함수에 대한 설명
     * https://velog.io/@seculoper235/RowMapper%EC%97%90-%EB%8C%80%ED%95%B4 -> RowMapper에 대한 설명
     */

//    String dateFormat = "select to_date(createdAt, 'DD-MM-YY') as createdAt from user";
//    Date createdAt = this.jdbcTemplate.queryForObject(dateFormat, Date.class);

    // 회원가입
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into user (email, password, nickName, birth) values (?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(), postUserReq.getNickName(), postUserReq.getBirth()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        // email -> postUserReq.getEmail(), password -> postUserReq.getPassword(), nickname -> postUserReq.getNickname() 로 매핑(대응)시킨다음 쿼리문을 실행한다.
        // 즉 DB의 User Table에 (email, password, nickname)값을 가지는 유저 데이터를 삽입(생성)한다.

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
//        String lastInsertIdQuery = "select count(*) from user"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    // 이메일 확인
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from user where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        String checkEmailParams = email; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 회원정보 변경
//    public int modifyUserName(PatchUserReq patchUserReq) {
//        String modifyUserNameQuery = "update user set profileImgUrl = ?, nickName = ? where userId = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getProfileImgUrl(), patchUserReq.getNickName(), patchUserReq.getUserId()}; // 주입될 값들(nickname, userIdx) 순
//
//        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
//    }

    // 회원프로필사진 변경
    public int modifyProfileImgUrl(int userId, String profileImgUrl) {
        String modifyProfileImgUrlQuery = "update user set profileImgUrl = ? where userId = ?";
        Object[] modifyProfileImgUrlParams = new Object[]{profileImgUrl, userId};

        return this.jdbcTemplate.update(modifyProfileImgUrlQuery, modifyProfileImgUrlParams);
    }
    // 회원 닉네임 변경
    public int modifyNickName(int userId, String nickName) {
        String modifyNickNameQuery = "update user set nickName = ? where userId = ?";
        Object[] modifyNickNameParams = new Object[]{nickName, userId};

        return this.jdbcTemplate.update(modifyNickNameQuery, modifyNickNameParams);
    }
    // 회원 생일 변경
    public int modifyBirth(int userId, String birth) {
        String modifyBirthQuery = "update user set birth = ? where userId = ?";
        Object[] modifyBirthParams = new Object[]{birth, userId};

        return this.jdbcTemplate.update(modifyBirthQuery, modifyBirthParams);
    }

    // 회원 생일 오픈 변경
    public int modifyBirthOpen(int userId, Boolean birthOpen) {
        String modifyBirthOpenQuery = "update user set birthOpen = ? where userId = ?";
        Object[] modifyBirthOpenParams = new Object[]{birthOpen, userId};

        return this.jdbcTemplate.update(modifyBirthOpenQuery, modifyBirthOpenParams);
    }

    // 회원 상태 변경
    public String modifyStatus(int userId) {
        String modifyStatusQuery = "update user set status=if(status='ACTIVE', 'INACTIVE', 'ACTIVE') where userId = ?";
        int modifyStatusParam = userId;

        this.jdbcTemplate.update(modifyStatusQuery, modifyStatusParam);

        String getStatusQuery = "select status from user where userId=?";
        int getStatusParam = userId;

        return this.jdbcTemplate.queryForObject(getStatusQuery, String.class, getStatusParam);
    }


    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userId,profileImgUrl,email,password,nickName,birth,status,createdAt,updatedAt, birthOpen from user where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postLoginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickName"),
                        rs.getString("birth"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime(),
                        rs.getBoolean("birthOpen")
                ), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // User 테이블에 존재하는 전체 유저들의 정보 조회
    public List<GetUserRes> getUsers() {
        String getUsersQuery =
                        "select userId, profileImgUrl, email, nickName, birth, status, " +
                        "date_format(createdAt, '%Y년 %m월 %d일') as createdAt, birthOpen " +
                        "from user"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리

        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("email"),
//                        rs.getString("password"),
                        rs.getString("nickName"),
                        rs.getString("birth"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getBoolean("birthOpen")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }

    // 해당 nickName을 갖는 유저들의 정보 조회
    public List<GetUserRes> getUsersByNickname(String nickName) {
        try{
            String getUsersByNicknameQuery =
                    "select distinct user.userId, user.profileImgUrl, user.email, user.nickName, user.birth, user.status, " +
                            "date_format(user.createdAt, '%Y년 %m월 %d일') as createdAt, user.birthOpen " +
                            "from user " +
                            "left join diary on user.userId = diary.userId " +
                            "where user.nickName =? and user.status='ACTIVE' " +
                            "group by user.userId " +
                            "order by max(diary.createdAt) desc;"; // 해당 닉네임을 만족하는 유저 조회, 최근에 일기를 쓴 유저부터 제일 위로

            String getUsersByNicknameParams = nickName;
            return this.jdbcTemplate.query(getUsersByNicknameQuery,
                    (rs, rowNum) -> new GetUserRes(
                            rs.getInt("userId"),
                            rs.getString("profileImgUrl"),
                            rs.getString("email"),
//                        rs.getString("password"),
                            rs.getString("nickName"),
                            rs.getString("birth"),
                            rs.getString("status"),
                            rs.getString("createdAt"),
                            rs.getBoolean("birthOpen")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                    getUsersByNicknameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

            // queryForObject의 결과값이 없거나 2개 이상일때
            // IncorrectResultSizeDataAccessException error 발생
            // 그럴땐 null 값 반환
        } catch (IncorrectResultSizeDataAccessException error) {
            return null;
        }

    }

    // 해당 userId를 갖는 유저조회
    // 친구목록에서 친구 diary 볼때 기능
    public GetSpecificUserRes getUserById(int userIdByJwt, int userId) {
        try {

            // 회원 조회 할 때 오늘 방문한 수 늘리기
            String viewUserQuery = "insert into userView (userId, showingUserId) values (?, ?)";
            Object[] viewUserParams = new Object[]{userIdByJwt, userId};
            if (userIdByJwt != userId) {
                this.jdbcTemplate.update(viewUserQuery, viewUserParams);
            }

//            String getUserQuery =
//                    "select userId, profileImgUrl, email, nickName, birth, status, " +
//                            "date_format(createdAt, '%Y년 %m월 %d일') as createdAt, birthOpen " +
//                            "from user where userId = ? and status = 'ACTIVE'"; // 해당 userId를 만족하는 유저를 조회하는 쿼리문

            // 유저 조회수 따로
            String getUserViewQuery = "select count(userViewId) from userView where showingUserId = ? and date(viewDate) = date(now())";
            int getUserViewParam = userId;
            int view = this.jdbcTemplate.queryForObject(getUserViewQuery, int.class, getUserViewParam);

            // 유저 다이어리 개수
            String getNumDiaryQuery = "select count(*) from diary where userId=?";
            int getNumDiaryParam = userId;
            int numDiary = this.jdbcTemplate.queryForObject(getNumDiaryQuery, int.class, getNumDiaryParam);

            // 유저 조회
            String getUserQuery =
                    "select userId, profileImgUrl, email, nickName, birth, status, date_format(createdAt, '%Y년 %m월 %d일') as createdAt, birthOpen " +
                            "from user " +
                            "where userId=?";
            int getUserParams = userId;

            return this.jdbcTemplate.queryForObject(getUserQuery,
                    (rs, rowNum) -> new GetSpecificUserRes(
                            rs.getInt("userId"),
                            rs.getString("profileImgUrl"),
                            rs.getString("email"),
                            rs.getString("nickName"),
                            numDiary,
                            rs.getString("birth"),
                            rs.getString("status"),
                            rs.getString("createdAt"),
                            rs.getBoolean("birthOpen"),
                            view), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                    getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
        } catch (IncorrectResultSizeDataAccessException error) {
            return null;
        }
    }

    /**
     * 이메일로 존재하는 유저인지 체크
     * @param email
     * @return
     */
    public int isExistUser(String email) {
        String isExistUserQuery = "select exists(select email from user where status='ACTIVE' and email=?)";
        String isExistUserParams = email;

        return this.jdbcTemplate.queryForObject(isExistUserQuery, int.class, isExistUserParams);
    }

    public int countTodayViewUser(int userId) {
        String countTodayViewUserQuery = "select count(userViewId) from userView where showingUserId = ?";
        int countTodayViewUserParams = userId;

        return this.jdbcTemplate.queryForObject(countTodayViewUserQuery, int.class, countTodayViewUserParams);
    }

    // 삭제 안된 유저 인지 확인
    public int isExistUserByUserId(int userId) {
        String isInactiveUserQuery = "select exists(select userId from user where status='ACTIVE' and userId=?)";
        int isInactiveUserParam = userId;

        return this.jdbcTemplate.queryForObject(isInactiveUserQuery, int.class, isInactiveUserParam);
    }
}
