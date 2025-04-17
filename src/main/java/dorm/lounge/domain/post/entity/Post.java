package dorm.lounge.domain.post.entity;

import dorm.lounge.domain.user.entity.User;
import dorm.lounge.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount;

    private int likeCount;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
