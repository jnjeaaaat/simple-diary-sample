package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/blocks")
public class BlockController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BlockProvider blockProvider;
    @Autowired
    private final BlockService blockService;
    @Autowired
    private final JwtService jwtService;

    public BlockController (BlockProvider blockProvider, BlockService blockService, JwtService jwtService) {
        this.blockProvider = blockProvider;
        this.blockService = blockService;
        this.jwtService = jwtService;
    }

    // 유저 차단
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostBlockRes> blockUser(@RequestBody PostBlockReq postBlockReq) {
        try {
            if (postBlockReq.getUserId() != jwtService.getUserId()) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostBlockRes postBlockRes = blockService.blockUser(postBlockReq);
            return new BaseResponse<>(BLOCK_THE_USER, postBlockRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
