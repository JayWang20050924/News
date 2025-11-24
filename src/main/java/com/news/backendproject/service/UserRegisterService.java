package com.news.backendproject.service;

import com.news.backendproject.dao.Imp.UserDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {
    @Autowired
    private UserDaoImp userDaoImp;
}
