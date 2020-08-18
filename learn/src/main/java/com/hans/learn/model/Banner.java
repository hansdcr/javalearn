package com.hans.learn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private int id;
    private String name;
    private String description;
    private String title;
    private String img;
}
