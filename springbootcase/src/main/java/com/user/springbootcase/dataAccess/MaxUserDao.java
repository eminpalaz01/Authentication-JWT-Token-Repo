package com.user.springbootcase.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.user.springbootcase.entities.MaxUser;

@Repository
public interface MaxUserDao extends JpaRepository<MaxUser,Integer> {

	MaxUser findByUserName(String username);

}
