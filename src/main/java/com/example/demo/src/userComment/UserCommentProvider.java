package com.example.demo.src.userComment;

import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommentProvider {
    private final UserCommentDao userCommentDao;
    private final JwtService jwtService;

    @Autowired
    public UserCommentProvider()
}
