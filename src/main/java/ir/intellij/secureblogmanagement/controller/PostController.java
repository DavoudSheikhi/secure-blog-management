package ir.intellij.secureblogmanagement.controller;

import ir.intellij.secureblogmanagement.AccessDenied;
import ir.intellij.secureblogmanagement.dto.PostDtoRequest;
import ir.intellij.secureblogmanagement.model.Post;
import ir.intellij.secureblogmanagement.model.Role;
import ir.intellij.secureblogmanagement.model.User;
import ir.intellij.secureblogmanagement.repository.PostRepository;
import ir.intellij.secureblogmanagement.repository.UserRepository;
import ir.intellij.secureblogmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/post")
    public String getPost() {
        return "you got a post";
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PostMapping("/post")
    public String postAPost(Principal principal) {
        String name = principal.getName();
        User user = userRepository.findByUsername(name);
        Post post = Post.builder()
                .title("java")
                .author(user)
                .content("java is good")
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();
        user.getPost().add(post);
        userRepository.save(user);
        postRepository.save(post);
        return "you posted";
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR')")
    @PutMapping("/post/{id}")
    public String putAPost(@PathVariable Long id, @RequestBody PostDtoRequest postDtoRequest, Principal principal) {
        String name = principal.getName();
        User user = userRepository.findByUsername(name);
        Post post = postRepository.findById(id).orElseThrow();
        String username = post.getAuthor().getUsername();
        if (name.equalsIgnoreCase(username) || user.getRole().equals(Role.ADMIN)) {
            post.setContent(postDtoRequest.content());
            post.setTitle(postDtoRequest.title());
            post.setUpdatedDate(LocalDate.now());
            postRepository.save(post);
        } else {
            throw new AccessDenied("access denied");
        }
        return "you posted";
    }

    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePost(@PathVariable Long id){
        postRepository.deleteById(id);
        return "you deleted a post ";

    }

}
