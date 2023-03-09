package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDiaryRes {
    private String countDiary; // TODO: 그 유저의 몇번 째 글인지 count() 해서 보여주기 가능하면
}
