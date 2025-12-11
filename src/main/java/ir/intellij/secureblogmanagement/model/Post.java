package ir.intellij.secureblogmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    private LocalDate createdDate;
    private LocalDate updatedDate;
    private boolean published;
}
