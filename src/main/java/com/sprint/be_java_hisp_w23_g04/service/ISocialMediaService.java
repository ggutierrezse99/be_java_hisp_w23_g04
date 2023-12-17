package com.sprint.be_java_hisp_w23_g04.service;

import com.sprint.be_java_hisp_w23_g04.dto.response.SimpleMessageDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.UserDTO;

import java.util.List;

public interface ISocialMediaService {
    List<UserDTO> getAllUsers();
    SimpleMessageDTO unfollowUser(int userId, int unfollowId);
}
