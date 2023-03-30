package com.example.demo.src.block;

import com.example.demo.config.BaseException;
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

    public PostBlockRes blockUser(PostBlockReq postBlockReq) throws BaseException {
        try {
            PostBlockRes postBlockRes = new PostBlockRes(blockDao.blockUser(postBlockReq));
            return postBlockRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
