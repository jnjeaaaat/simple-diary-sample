package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.src.diary.model.PatchDiaryReq;
import com.example.demo.src.diary.model.PostDiaryReq;
import com.example.demo.src.diary.model.PostDiaryRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class DiaryService {

    private final DiaryDao diaryDao;
    private final DiaryProvider diaryProvider;
    private final UserProvider userProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    @Autowired
    public DiaryService(DiaryDao diaryDao, DiaryProvider diaryProvider, UserProvider userProvider, JwtService jwtService) {
        this.diaryDao = diaryDao;
        this.jwtService = jwtService;
        this.diaryProvider = diaryProvider;
        this.userProvider = userProvider;
    }

    /**
     * [post] createPost
     */
    public PostDiaryRes createDiary(PostDiaryReq postDiaryReq) throws BaseException {
        if (userProvider.isExistUserByUserId(postDiaryReq.getUserId())) {
            throw new BaseException(INACTIVE_USER);
        }
        try {
            PostDiaryRes postDiaryRes = new PostDiaryRes(diaryDao.createDiary(postDiaryReq)+" 번째 일기입니다.");
            return postDiaryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
//    public void modifyUser(int userId, PatchUserReq patchUserReq) throws BaseException {
//        if (userProvider.isExistUserByUserId(userId)) {
//            throw new BaseException(INACTIVE_USER);
//        }
//        try {
//            int result = 0;
//            // 수정하고자 하는 정보만 입력할 수 있음.
//            if(patchUserReq.getProfileImgUrl() != null) result = userDao.modifyProfileImgUrl(userId, patchUserReq.getProfileImgUrl());
//            if(patchUserReq.getNickName() != null) result = userDao.modifyNickName(userId, patchUserReq.getNickName());
//            if(patchUserReq.getBirth() != null) result = userDao.modifyBirth(userId, patchUserReq.getBirth());
//            if(patchUserReq.getStatus() != null) result = userDao.modifyStatus(userId, patchUserReq.getStatus());
//            if(patchUserReq.getBirthOpen() != null) result = userDao.modifyBirthOpen(userId, patchUserReq.getBirthOpen());
//
////            result = userDao.modifyUserName(patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
//            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
//                throw new BaseException(MODIFY_FAIL_USER_INFORM);
//            }
//        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
    public void modifyDiary(int diaryId, PatchDiaryReq patchDiaryReq) throws BaseException {
        try {
            int result = 0;
            // 수정하고자 하는 정보만 입력할 수 있음.
            if(patchDiaryReq.getDiaryImg() != null) result = diaryDao.modifyDiaryImgs(diaryId, patchDiaryReq.getDiaryImg().getDiaryImgUrls());
            if(patchDiaryReq.getTitle() != null) result = diaryDao.modifyTitle(diaryId, patchDiaryReq.getTitle());
            if(patchDiaryReq.getContents() != null) result = diaryDao.modifyContents(diaryId, patchDiaryReq.getContents());
            if(patchDiaryReq.getEmotion() != null) result = diaryDao.modifyEmotion(diaryId, patchDiaryReq.getEmotion());
            if(patchDiaryReq.getConsumption() != null) result = diaryDao.modifyConsumption(diaryId, patchDiaryReq.getConsumption());
            if(patchDiaryReq.getImportation() != null) result = diaryDao.modifyImportation(diaryId, patchDiaryReq.getImportation());
            if(patchDiaryReq.getIsOpen() != null) result = diaryDao.modifyIsOpen(diaryId, patchDiaryReq.getIsOpen());
            if(patchDiaryReq.getDiaryDate() != null) result = diaryDao.modifyDiaryDate(diaryId, patchDiaryReq.getDiaryDate());
//            result = userDao.modifyUserName(patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_DIARY);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 일기 삭제
     * @param diaryId
     * @return Boolean
     * @throws BaseException
     */
    public Boolean modifyIsDeleted(int diaryId) throws BaseException {
        if (diaryProvider.checkIsDeletedDiary(diaryId)) {
            throw new BaseException(ALREADY_DELETED_DIARY);
        }
        try {
            Boolean isDeleted = diaryDao.modifyIsDeleted(diaryId);
            return isDeleted;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
