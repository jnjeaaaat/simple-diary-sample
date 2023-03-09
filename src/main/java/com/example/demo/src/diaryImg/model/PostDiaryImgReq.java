package com.example.demo.src.diaryImg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class PostDiaryImgReq {
    private ArrayList<String> diaryImgUrl; // List 로 한번에 받기
}
