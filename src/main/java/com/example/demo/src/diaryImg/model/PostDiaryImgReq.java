package com.example.demo.src.diaryImg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDiaryImgReq {
    private ArrayList<String> diaryImgUrls; // List 로 한번에 받기
}
