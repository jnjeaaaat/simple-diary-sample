package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.friend.model.PostFriendReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/friends")
public class FriendController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final FriendProvider friendProvider;
    @Autowired
    private final FriendService friendService;
    @Autowired
    private final JwtService jwtService;

    public FriendController(FriendProvider friendProvider, FriendService friendService, JwtService jwtService) {
        this.friendProvider = friendProvider;
        this.friendService = friendService;
        this.jwtService = jwtService;
    }

    // 친구 요청
    @ResponseBody
    @PostMapping("/request")
    public BaseResponse<String> requestFriend(@RequestBody PostFriendReq postFriendReq) {
        try {
            int userIdByJWT = jwtService.getUserId();
            if (postFriendReq.getGiveUserId() != userIdByJWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            friendService.requestFriend(postFriendReq);

            return new BaseResponse<>("친구요청 하였습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
