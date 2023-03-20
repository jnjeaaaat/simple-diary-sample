package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class DiaryProvider {
    @Autowired
    private final DiaryDao diaryDao;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public DiaryProvider(DiaryDao diaryDao, JwtService jwtService) {
        this.diaryDao = diaryDao;
        this.jwtService = jwtService;
    }

    // 전체 일기 조회(특정유저x)
    public List<GetDiaryRes> getAllDiary() throws BaseException {
        try {
            List<GetDiaryRes> getDiaryRes = diaryDao.getAllDiary();
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 유저 일기 조회
    public List<GetDiaryRes> getUserDiary(int userId) throws BaseException {
        try {
            List<GetDiaryRes> getDiaryRes = diaryDao.getUserDiary(userId);
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 특정 하나의 일기 조회
    public GetDiaryRes getDiary(int diaryId) throws BaseException {
        try {
            GetDiaryRes getDiaryRes = diaryDao.getDiary(diaryId);
            return getDiaryRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // diaryId 로 userId 받아오기
    public int getUserIdByDiary(int diaryId) throws BaseException {
        try {
            int userId = diaryDao.getUserIdByDiary(diaryId);
            System.out.println(userId);
            return userId;
        } catch (Exception exception){
            throw new BaseException(DELETED_DIARY);
        }
    }
}
