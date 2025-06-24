package com.krajust.criti_cloud_back.user;

public interface UserTestData {

    String userNickname = "nickname";

    default UserDTO.UserDTOBuilder toCreateUser() {
        return UserDTO.builder()
                .nickname(userNickname);
    }

}
