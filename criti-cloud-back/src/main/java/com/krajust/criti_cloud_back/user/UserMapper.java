package com.krajust.criti_cloud_back.user;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getNickname());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.id, userDTO.nickname);
    }
}
