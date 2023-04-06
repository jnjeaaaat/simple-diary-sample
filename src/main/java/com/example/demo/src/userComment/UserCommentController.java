package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.userComment.model.PatchUserCommentReq;
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
}
