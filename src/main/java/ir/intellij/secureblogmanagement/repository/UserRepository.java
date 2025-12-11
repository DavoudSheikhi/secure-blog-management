package ir.intellij.secureblogmanagement.repository;

import ir.intellij.secureblogmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
