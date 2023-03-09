package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDiaryReq {
    private int userId; // 글 작성 유저
    private String title; // 글 제목
    private String contents; // 글 내용
    private String feel; // 그날의 기분 default == happy [happy, angry, depressed, heart]
    private int consumption; // 그날의 지출 default == 0
    private int importation; // 그날의 수입 default == 0
    private Boolean isOpen; // 누구나 볼수있는지 default == true
    private Date diaryDate; // 등록할 날짜 선택 default == current_date
}
