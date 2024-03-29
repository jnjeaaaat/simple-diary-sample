package com.example.demo.src.block;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.model.GetBlockRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BlockProvider {

    public BlockDao blockDao;
    public JwtService jwtService;

    @Autowired
    public BlockProvider (BlockDao blockDao, JwtService jwtService) {
        this.blockDao = blockDao;
        this.jwtService = jwtService;
    }

    // 차단 유저 조회
    public List<GetBlockRes> getAllBlockUser(int userId) throws BaseException {
        try {
            List<GetBlockRes> getBlockRes = blockDao.getAllBlockUser(userId);
            return getBlockRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 이미 차단 했는지
    public int isBlocked(int userId, int blockUserId) throws BaseException {
        try {
            int result = blockDao.isBlocked(userId, blockUserId);
            return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
