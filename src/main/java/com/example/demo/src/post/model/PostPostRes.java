package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostRes {
    private int postId; // TODO: 그 유저의 몇번 째 글인지 count() 해서 보여주기 가능하면
}
