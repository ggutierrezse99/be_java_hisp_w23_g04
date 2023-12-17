package com.sprint.be_java_hisp_w23_g04.service;

import com.sprint.be_java_hisp_w23_g04.dto.response.SimpleMessageDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.UserDTO;
import com.sprint.be_java_hisp_w23_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w23_g04.utils.UserMapper;
import org.springframework.stereotype.Service;
import com.sprint.be_java_hisp_w23_g04.entity.User;
import com.sprint.be_java_hisp_w23_g04.repository.ISocialMediaRepository;
import com.sprint.be_java_hisp_w23_g04.repository.SocialMediaRepositoryImpl;

import java.util.List;
import java.util.Objects;

@Service
public class SocialMediaServiceImpl implements ISocialMediaService {

    private final ISocialMediaRepository socialMediaRepository;

    public SocialMediaServiceImpl(SocialMediaRepositoryImpl socialMediaRepository){
        this.socialMediaRepository = socialMediaRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
       List<User> users = socialMediaRepository.findAllUsers();
       return users.stream().map(UserMapper::mapUser).toList();
    }

    @Override
    public SimpleMessageDTO unfollowUser(int userId, int unfollowId) {
        User user = socialMediaRepository.findUserById(userId);
        if(user == null){
            throw new UserNotFoundException("The user performing this action does not exist");
        }
        User unfollowedUser = socialMediaRepository.findUserById(unfollowId) ;
        if(unfollowedUser == null){
            throw new UserNotFoundException("The user you are trying to unfollow does not exist");
        }

        if(user.getFollowed().stream().filter(followed -> Objects.equals(followed.getId(), unfollowedUser.getId())).findAny().orElse(null) == null){
            throw new UserNotFoundException("The user you are trying to unfollow is not on your followed list");
        };

        if(unfollowedUser.getFollowers().stream().filter(follower -> Objects.equals(follower.getId(), user.getId())).findAny().orElse(null) == null){
            throw new UserNotFoundException("Your user is not on the user you are trying to unfollow's followers list. Please check the consistency of your data");
        };

        socialMediaRepository.unfollowUser(userId, unfollowId);

        return new SimpleMessageDTO("User " + unfollowedUser.getName() + " Id: " + unfollowedUser.getId() + " was succesfully unfollowed by user " + user.getName() + " Id: " + user.getId());
    }

    private boolean isSeller(User user){
        return !user.getPosts().isEmpty();
    }
}
