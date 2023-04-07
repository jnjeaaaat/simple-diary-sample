package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.userComment.model.PatchUserCommentReq;
import com.example.demo.src.userComment.model.PatchUserCommentRes;
import com.example.demo.src.userComment.model.PostUserCommentReq;
import com.example.demo.src.userComment.model.PostUserCommentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/users/comments")
public class UserCommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserCommentProvider userCommentProvider;
    @Autowired
    private final UserCommentService userCommentService;
    @Autowired
    private final JwtService jwtService;

    public UserCommentController (UserCommentProvider userCommentProvider, UserCommentService userCommentService, JwtService jwtService) {
        this.userCommentProvider = userCommentProvider;
        this.userCommentService = userCommentService;
        this.jwtService = jwtService;
    }

    /**
     * 방명록 작성
     * @param postUserCommentReq
     * @return comment, createdAt
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserCommentRes> writeComment(@RequestBody PostUserCommentReq postUserCommentReq) {
        try {
            if (postUserCommentReq.getUserId() != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostUserCommentRes postUserCommentRes = userCommentService.writeComment(postUserCommentReq);
            return new BaseResponse<>(SUCCESS_WRITE_COMMENT, postUserCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 방명록 수정
     * @param patchUserCommentReq
     * @return comment, updatedAt
     */
    @ResponseBody
    @PatchMapping("/{userCommentId}")
    public BaseResponse<PatchUserCommentRes> modifyComment(@PathVariable("userCommentId") int userCommentId,
                                                           @RequestBody PatchUserCommentReq patchUserCommentReq) {
        try {
            int userIdFromComment = userCommentProvider.getUserIdFromComment(userCommentId);
            if (userIdFromComment != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserCommentRes patchUserCommentRes = userCommentService.modifyComment(userCommentId, patchUserCommentReq);
            return new BaseResponse<>(SUCCESS_MODIFY_COMMENT, patchUserCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 방명록에 하트누르기
     * @param userCommentId
     * @return Boolean
     */
    @ResponseBody
    @PatchMapping("/hearts/{userCommentId}")
    public BaseResponse<Boolean> heartComment(@PathVariable("userCommentId") int userCommentId) {
        try {
            int takeUserIdComment = userCommentProvider.getTakeUserIdComment(userCommentId);
            if (takeUserIdComment != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            Boolean isHearted = userCommentService.heartComment(userCommentId);
            if(isHearted) {
                return new BaseResponse<>(SUCCESS_PRESS_HEART, isHearted);
            } else {
                return new BaseResponse<>(SUCCESS_ANTI_PRESS_HEART, isHearted);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
