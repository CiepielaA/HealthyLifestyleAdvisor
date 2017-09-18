package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}
