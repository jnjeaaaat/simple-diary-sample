package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.diary.model.PostDiaryReq;
import com.example.demo.src.diary.model.PostDiaryRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

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
    //TODO: 일기 수정, 일기 삭제, 일기 조회(본인꺼, 친구꺼(오픈된것만)), 가계부
    //TODO: 유저 닉네임으로 조회할 때 최근에 일기 쓴 사람 순서대로

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
    public BaseResponse<List<GetDiaryRes>> getUserDiary(@PathVariable("userId") int userId) {
        try {
            List<GetDiaryRes> getDiaryRes = diaryProvider.getUserDiary(userId);
            return new BaseResponse<>(FIND_USER_DIARIES, getDiaryRes);
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
            int userId = diaryProvider.getUserIdByDiary(diaryId);
            if (userId != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetDiaryRes getDiaryRes = diaryProvider.getDiary(diaryId);
            return new BaseResponse<>(FIND_ONE_DIARY, getDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
