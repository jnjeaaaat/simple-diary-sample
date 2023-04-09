package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class DiaryProvider {

    private final DiaryDao diaryDao;
    private final UserProvider userProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    @Autowired
    public DiaryProvider(DiaryDao diaryDao, UserProvider userProvider, JwtService jwtService) {
        this.diaryDao = diaryDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    // 전체 일기 조회(특정유저x)
    public List<GetDiaryRes> getAllDiary() throws BaseException {
        try {
//            List<GetDiaryRes> getDiaryRes = diaryDao.getAllDiary();
            List<GetDiaryRes> getDiaryRes = new ArrayList<>();
            for (int i = 1; i <= lastIdOfDiary(); i++) {
                // 탈퇴한 유저의 일기 거르기
                if (!userProvider.isExistUserByUserId(getUserIdFromDiary(i))) {
                    if (getUserIdFromDiary(i) != 0) {
                        getDiaryRes.add(diaryDao.getDiary(i,getUserIdFromDiary(i)));
                    }
                }
            }
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 유저 일기들 조회
    public List<GetDiaryRes> getUserDiary(int userId) throws BaseException {
        if (userProvider.isExistUserByUserId(userId)) {
            throw new BaseException(INACTIVE_USER);
        }
        try {
//            List<GetDiaryRes> getDiaryRes = diaryDao.getUserDiary(userId);
            List<GetDiaryRes> getDiaryRes = new ArrayList<>();
            for (int i = 1; i <= lastIdOfDiary(); i++) {
                if (userId == getUserIdFromDiary(i)) {
                    if (getUserIdFromDiary(i) != 0) {
                        getDiaryRes.add(diaryDao.getDiary(i,getUserIdFromDiary(i)));
                    }
                }
            }
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 해당 감정 일기 조회
    public List<GetDiaryRes> getUserDiaryByEmotion(int userId, String emotion) throws BaseException {
        try {
            List<GetDiaryRes> getDiaryRes = new ArrayList<>();
            for (int i = 1; i <= lastIdOfDiary(); i++) {
                if (userId == getUserIdFromDiary(i)) {
                    if (getUserIdFromDiary(i) != 0) {
                        if(getEmotionFromDiary(i).equals(emotion)) {
                            getDiaryRes.add(diaryDao.getDiary(i,getUserIdFromDiary(i)));
                        }
                    }
                }
            }
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 하나의 일기 조회
    public GetDiaryRes getDiary(int diaryId) throws BaseException {
        // 탈퇴한 유저의 일기
        if (userProvider.isExistUserByUserId(getUserIdFromDiary(diaryId))) {
            throw new BaseException(INACTIVE_USER_DIARY);
        }
        // 비밀일기 일 때
        if (jwtService.getUserId() != getUserIdFromDiary(diaryId)) {
            if (!checkIsOpenDiary(diaryId)) {
                throw new BaseException(SECRET_DIARY);
            }
        }
        try {
            GetDiaryRes getDiaryRes = diaryDao.getDiary(diaryId,getUserIdFromDiary(diaryId));
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // diaryId 로 userId 받아오기
    public int getUserIdFromDiary(int diaryId) throws BaseException {
        try {
            int userId = diaryDao.getUserIdFromDiary(diaryId);
            return userId;
        } catch (Exception exception){
            throw new BaseException(DELETED_DIARY);
        }
    }

    // isDeleted=false 인 diary 갯수 받아오기
    public int lastIdOfDiary() throws BaseException {
        try {
            return diaryDao.lastIdOfDiary();
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // diaryId로 해당 일기 emotion 받아오기
    public String getEmotionFromDiary(int diaryId) throws BaseException {
        try {
            String emotion = diaryDao.getEmotionFromDiary(diaryId);
            return emotion;
        } catch (Exception exception){
            throw new BaseException(DELETED_DIARY);
        }
    }

    /**
     * isOpen 확인
     * @param diaryId
     * @return Boolean
     * @throws BaseException
     */
    public Boolean checkIsOpenDiary(int diaryId) throws BaseException {
        try {
            Boolean isOpenDiary = diaryDao.checkIsOpenDiary(diaryId);
            return isOpenDiary;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
