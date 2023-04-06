package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.userComment.model.PostUserCommentReq;
import com.example.demo.src.userComment.model.PostUserCommentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/users/comments")
public class UserCommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserCommentProvider userCommentProvider;
    @Autowired
    private UserCommentService userCommentService;
    @Autowired
    private JwtService jwtService;

    public UserCommentController (UserCommentProvider userCommentProvider, UserCommentService userCommentService, JwtService jwtService) {
        this.userCommentProvider = userCommentProvider;
        this.userCommentService = userCommentService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserCommentRes> writeComment(@RequestBody PostUserCommentReq postUserCommentReq) {
        try {
            PostUserCommentRes postUserCommentRes = userCommentService.writeComment(postUserCommentReq);
            return new BaseResponse<>(postUserCommentRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
