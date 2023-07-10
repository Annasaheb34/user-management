package com.usermanagementsystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.usermanagementsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u WHERE u.emailId=?1 AND u.password=?2")
	Optional<User> findByEmailId(String emailId, String password);

	Optional<User> findByEmailId(String emailId);

	@Query("select u from User u WHERE lower(u.firstName)like lower(concat('%',?1,'%'))")
	List<User> findByName(String firstName);
}
