package com.example.demo.src.test.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "JpaTest")
@Table
public class JpaTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    @Builder
    public JpaTest (String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
