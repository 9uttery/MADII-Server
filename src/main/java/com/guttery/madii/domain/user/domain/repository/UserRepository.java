package com.guttery.madii.domain.user.domain.repository;

import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryDslRepository {
    Optional<User> findByLoginInfo_LoginId(String email);
}
