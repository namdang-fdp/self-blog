package com.namdang.blog.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name = "blogs")
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {
    @Id
    @GeneratedValue
    private UUID blogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_user_id", nullable = false)
    private UserEntity postedUser;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String coverPath;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @ManyToMany
    @JoinTable(
            name = "blog_genres",
            joinColumns = @JoinColumn(name = "blogId"),
            inverseJoinColumns = @JoinColumn(name = "genresId")
    )
    public List<GenresEntity> genres;

    private int view;

    private int totalReact;

}
