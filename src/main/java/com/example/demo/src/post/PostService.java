package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostService {
    @Autowired
    private final PostDao postDao;
    private final PostProvider postProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public PostService (PostDao postDao, PostProvider postProvider, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
        this.postProvider = postProvider;
    }

    /**
     * [post] createPost
     */
    public PostPostRes createPost(PostPostReq postPostReq) throws BaseException {
        try {
            PostPostRes postPostRes = new PostPostRes(postDao.createPost(postPostReq)+" 번째 일기입니다.");
            return postPostRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
