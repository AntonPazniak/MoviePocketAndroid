package com.example.moviepocketandroid.api.models.post;

import com.example.moviepocketandroid.api.models.user.User;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private String content;
    private int[] likeOrDis;
    private User user;
    private Date create;
    private Date update;
    private Long idMovie;
    private Long idPerson;
    private Long idList;

    public Post(Long id, String title, String content, int[] likeOrDis, User user, Date create, Date update) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likeOrDis = likeOrDis;
        this.user = user;
        this.create = create;
        this.update = update;
    }
}
