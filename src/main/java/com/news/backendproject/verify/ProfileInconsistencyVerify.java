package com.news.backendproject.verify;

import com.news.backendproject.dto.user.UserProfileDto;
import com.news.backendproject.po.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileInconsistencyVerify {
    public boolean inconsistencyVerify(UserProfileDto dto, User currentUser) {
        return isProfileModified(dto, currentUser);
    }
    private boolean isProfileModified(UserProfileDto dto, User currentUser) {
        if (dto.getGender().equals(currentUser.getGender())&&
        dto.getBirthday().equals(currentUser.getBirthday())&&
        dto.getAddress().equals(currentUser.getAddress())) {
            //如果全都与修改前相同返回false
            return false;
        }
       return true;
    }
}
