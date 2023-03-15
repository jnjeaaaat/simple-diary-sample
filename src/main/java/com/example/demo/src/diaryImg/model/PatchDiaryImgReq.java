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
public class PatchDiaryImgReq {
    private ArrayList<String> diaryImgUrl; // 수정할 때 diaryImgUrl만 받아서 같은 diaryId의 이미지들 비교해서 수정하기
}
