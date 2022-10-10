package com.example.blog_api.service;


import com.example.blog_api.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface BlogService {
    public List<Blog> getAll();


    public Optional<Blog> findById(int id);

    public Page<Blog> listAllFilter(int id, Pageable pageable);

    Blog insert(Blog blog);

    void update(int id, Blog blog);

    void delete(int id);



    List<Blog> findByAuthorName(String authorName);

    Page<Blog> findBlogBySearchPage(String key, Pageable pageable);



}


