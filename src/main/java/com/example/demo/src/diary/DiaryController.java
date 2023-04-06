package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.diary.model.PatchDiaryReq;
import com.example.demo.src.diary.model.PostDiaryReq;
import com.example.demo.src.diary.model.PostDiaryRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexBirth;

@RestController
@RequestMapping("/app/diaries")
public class DiaryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final DiaryProvider diaryProvider;
    @Autowired
    private final DiaryService diaryService;
    @Autowired
    private final JwtService jwtService;

    public DiaryController(DiaryProvider diaryProvider, DiaryService diaryService, JwtService jwtService) {
        this.diaryProvider = diaryProvider;
        this.diaryService = diaryService;
        this.jwtService = jwtService;
    }

    // 일기 작성
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostDiaryRes> createDiary(@RequestBody PostDiaryReq postDiaryReq) {
        // userId null
        if (postDiaryReq.getUserId() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_USER_ID);
        }
        // 사진 9장 이상
        if (postDiaryReq.getDiaryImg().getDiaryImgUrls().size() > 9) {
            return new BaseResponse<>(POST_DIARY_IMG_NUM_OVER);
        }
        // 제목 null
        if (postDiaryReq.getTitle() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_TITLE);
        }
        // 내용 null
        if (postDiaryReq.getContents() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_CONTENTS);
        }
        // 기분 null
        if (postDiaryReq.getEmotion() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_EMOTION);
        }
        // 소비 null
        if (postDiaryReq.getConsumption() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_CONSUMPTION);
        }
        // 수입 null
        if (postDiaryReq.getImportation() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_IMPORTATION);
        }
        // 오픈 여부 null
        if (postDiaryReq.getIsOpen() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_IS_OPEN);
        }
        // 작성 날짜 null
        if (postDiaryReq.getDiaryDate() == null) {
            return new BaseResponse<>(POST_DIARY_EMPTY_DIARY_DATE);
        }
        // 날짜 형식 확인
        if (!isRegexBirth(postDiaryReq.getDiaryDate())) {
            return new BaseResponse<>(POST_NOT_DATE_TYPE);
        }
        try {
            int userIdByJwt = jwtService.getUserId();
            if (postDiaryReq.getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostDiaryRes postDiaryRes = diaryService.createPost(postDiaryReq);
            return new BaseResponse<>(SUCCESS_CREATE_NEW_DIARY, postDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //TODO: 일기 조회(본인꺼, 친구꺼(오픈된것만)), 가계부, 일기 조회 페이징, 유저의 feel 에 따른 일기 조회

    // 전체 일기 조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetDiaryRes>> getAllDiary() {
        try {
            List<GetDiaryRes> getDiaryRes = diaryProvider.getAllDiary();
            return new BaseResponse<>(FIND_ALL_DIARIES, getDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 특정 유저 일기들 조회
    @ResponseBody
    @GetMapping("/users/{userId}")
    public BaseResponse<List<GetDiaryRes>> getUserDiary(@PathVariable("userId") int userId,
                                                        @RequestParam(required = false) String emotion) {
        try {
            if (emotion == null) {
                List<GetDiaryRes> getDiaryRes = diaryProvider.getUserDiary(userId);
                return new BaseResponse<>(FIND_USER_DIARIES, getDiaryRes);
            }
            List<GetDiaryRes> getDiaryRes = diaryProvider.getUserDiaryByEmotion(userId, emotion);
            return new BaseResponse<>(FIND_USER_EMOTION_DIARIES, getDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 특정 일기 조회
    @ResponseBody
    @GetMapping("/{diaryId}")
    public BaseResponse<GetDiaryRes> getDiary(@PathVariable("diaryId") int diaryId) {
        try {
            // TODO: 친구테이블 만들면 친구테이블에 있는지 없는지 확인부터 하기
            //  -> 이것도 jwt 확인 밑에 if에 쓰고 본인일기인지 확인은 elif로 하면될듯
            int userId = diaryProvider.getUserIdFromDiary(diaryId);

            // isDeleted=true 인 일기
            if (userId == 0) {
                return new BaseResponse<>(DELETED_DIARY);
            }
            if (userId != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetDiaryRes getDiaryRes = diaryProvider.getDiary(diaryId);
            return new BaseResponse<>(FIND_ONE_DIARY, getDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 일기 수정
    @ResponseBody
    @PatchMapping("/{diaryId}")
    public BaseResponse<String> modifyDiary(@PathVariable("diaryId") int diaryId,
                                               @RequestBody PatchDiaryReq patchDiaryReq) {
        try {
            int userId = diaryProvider.getUserIdFromDiary(diaryId);
            if (userId == 0) {
                return new BaseResponse<>(DELETED_DIARY);
            }
            //userId와 접근한 유저가 같은지 확인
            if(userId != jwtService.getUserId()){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            diaryService.modifyDiary(diaryId, patchDiaryReq);

            return new BaseResponse<>(MODIFY_DIARY);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
