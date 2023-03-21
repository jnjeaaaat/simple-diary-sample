package com.example.demo.src.friend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private int friendId;
    private int giveUserId;
    private int takeUserId;
    private int isFriends;
    private LocalDateTime createdAt;
}
