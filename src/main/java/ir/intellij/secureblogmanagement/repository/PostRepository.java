package ir.intellij.secureblogmanagement.repository;

import ir.intellij.secureblogmanagement.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
