package com.user.springbootcase.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.user.springbootcase.entities.LoginTracking;

@Repository
public interface LoginTrackingDao extends JpaRepository<LoginTracking,Integer> {

}
