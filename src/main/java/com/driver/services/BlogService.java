package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    public Blog createAndReturnBlog(Integer userId, String title, String content) {
        //create a blog at the current time
    Blog blog=new Blog();
    blog.setId(userId);
    blog.setTitle(title);
    blog.setContent(content);
    User user=userRepository1.findById(userId).get();
    blog.setUser(user);
//    List<Blog>listOfBlogs=user.getBlogs();
//    listOfBlogs.add(blog);
//    user.setBlogs(listOfBlogs);
        //or
        user.getBlogs().add(blog);

    userRepository1.save(user);
    return blog;
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
    Blog blog=blogRepository1.findById(blogId).get();
    blogRepository1.delete(blog);
    for(User user:userRepository1.findAll()){
        if(user.getBlogs().contains(blog)){
            user.getBlogs().remove(blog);
            userRepository1.save(user);
            break;
        }
    }
    }
}
