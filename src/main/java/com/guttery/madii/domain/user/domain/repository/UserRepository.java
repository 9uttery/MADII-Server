package com.guttery.madii.domain.user.domain.repository;

import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryDslRepository {
}
