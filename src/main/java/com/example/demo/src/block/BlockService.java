package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.model.DeleteBlockReq;
import com.example.demo.src.block.model.PostBlockReq;
import com.example.demo.src.block.model.PostBlockRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BlockService {

    private final BlockProvider blockProvider;
    private final BlockDao blockDao;
    private final JwtService jwtService;

    @Autowired
    public BlockService (BlockProvider blockProvider, BlockDao blockDao, JwtService jwtService) {
        this.blockProvider = blockProvider;
        this.blockDao = blockDao;
        this.jwtService = jwtService;
    }

    // 유저 차단
    public PostBlockRes blockUser(PostBlockReq postBlockReq) throws BaseException {
        // 이미 차단한 유저
        if (blockProvider.isBlocked(postBlockReq.getUserId(), postBlockReq.getBlockUserId()) == 1) {
            throw new BaseException(ALREADY_BLOCKED_USER);
        }
        try {
            PostBlockRes postBlockRes = new PostBlockRes(blockDao.blockUser(postBlockReq));
            return postBlockRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유저 차단 해제
    public void unBlockUser(DeleteBlockReq deleteBlockReq) throws BaseException {
        if (blockProvider.isBlocked(deleteBlockReq.getUserId(), deleteBlockReq.getBlockUserId()) == 0) {
            throw new BaseException(NOT_BLOCKED_USER);
        }
        try {
            int result = blockDao.unBlockUser(deleteBlockReq);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
