package com.sprint.be_java_hisp_w23_g04.utils;

import com.sprint.be_java_hisp_w23_g04.dto.DBUserDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.PostDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.ProductDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.UserDTO;
import com.sprint.be_java_hisp_w23_g04.dto.response.UserFollowDTO;
import com.sprint.be_java_hisp_w23_g04.entity.Post;
import com.sprint.be_java_hisp_w23_g04.entity.Product;
import com.sprint.be_java_hisp_w23_g04.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User mapUser(DBUserDTO userDto) {
        List<PostDTO> postsDto = userDto.getPosts();

        List<Post> posts = postsDto.stream().map(p -> new Post(p.getPostId(), p.getDate(), mapProduct(p.getProduct()), p.getCategory(), p.getPrice())).toList();
        List<User> followed;
        List<User> followers;

        if(userDto.getFollowed() == null){
            followed = new ArrayList<>();
        }
        else{
            followed = userDto.getFollowed().stream().map(p -> new User(p.getId(), p.getName())).toList();
        }

        if(userDto.getFollowers() == null){
            followers = new ArrayList<>();
        }
        else{
            followers = userDto.getFollowers().stream().map(p -> new User(p.getId(), p.getName())).toList();
        }
        return new User(userDto.getUser_id(), userDto.getName(), posts,followed, followers);
    }

    public static UserDTO mapUser(User user) {
        List<PostDTO> postDTOS = user.getPosts().stream().map(p -> new PostDTO(user.getId(), p.getId(), p.getDate(),
                mapProduct(p.getProduct()), p.getCategory(), p.getPrice())).toList();
        List<UserFollowDTO> followedDTOS = user.getFollowed().stream().map(
                p -> new UserFollowDTO(p.getId(),p.getName())).toList();
        List<UserFollowDTO> followersDTOS = user.getFollowers().stream().map(
                p -> new UserFollowDTO(p.getId(),p.getName())).toList();
        return new UserDTO(user.getId(), user.getName(), postDTOS, followedDTOS, followersDTOS);
    }

    public static Product mapProduct(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getType(),
                productDTO.getBrand(), productDTO.getColor(), productDTO.getNotes());
    }

    public static ProductDTO mapProduct(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getType(),
                product.getBrand(), product.getColor(), product.getNotes());
    }

    private static LocalDate convertDateFromString(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(stringDate, formatter);
    }
}
