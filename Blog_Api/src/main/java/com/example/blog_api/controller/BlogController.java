package com.example.blog_api.controller;

import com.example.blog_api.Form.BlogForm;
import com.example.blog_api.Repository.BlogRepository;
import com.example.blog_api.model.Blog;

import com.example.blog_api.model.Cover;
import com.example.blog_api.service.BlogService;
import com.example.blog_api.service.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CoverService coverService;

    @Autowired
    private BlogRepository blogRepository;

    @Value("${upload.path}")
    private String fileUpload;

    @Autowired
    Environment environment;
    @GetMapping("/view/blogs")
    public ModelAndView view(){
        ModelAndView model = new ModelAndView("blogs",HttpStatus.OK);
        return model;
    }



    @GetMapping("/blogs")
    public ResponseEntity<Map<String, Object>> listBlogs(
                @RequestParam(name = "cateid", required = false) Integer cateid,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {

        try {
            List<Blog> blogs = new ArrayList<Blog>();
            Pageable paging = PageRequest.of(page, size);

            Page<Blog> pageBlogs = null;
            if (cateid != null && cateid != 0) {
//                pageBlogs = blogRepository.findAll(paging);
                pageBlogs = blogService.listAllFilter(cateid, paging);
                System.out.println("fdafdas");
            } else {
                pageBlogs = blogRepository.findAll(paging);
//                blogs = blogService.getAll();
            }

            blogs = pageBlogs.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("blogs", blogs);
            response.put("currentPage", pageBlogs.getNumber());
            response.put("totalItems", pageBlogs.getTotalElements());
            response.put("totalPages", pageBlogs.getTotalPages());



            if (blogs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable(name = "id") int id) {
        Blog blog = blogService.findById(id).get();
        if (blog == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Blog>(blog, HttpStatus.OK);
    }


    @PostMapping("/blogs")
    public ResponseEntity<Blog> addBLog(@ModelAttribute("blog")  BlogForm blogForm, BindingResult bindingResult) throws IOException {
        System.out.println("create");
//        blogForm.setCategory(blogForm.getCategory().getId());
        if (!bindingResult.hasErrors()) {
            Blog blog = new Blog.BlogBuilder(blogForm.getTitle())
                    .content(blogForm.getContent()).build();
            blog.setCategory(blogForm.getCategory());
            blog.setAuthor(blogForm.getAuthor());
            blogService.insert(blog);
//            System.out.println(blogForm.getCategory().getName());
            for (MultipartFile file : blogForm.getFiles()) {
                String fileName = file.getOriginalFilename();
                var is = file.getInputStream();
                try {
                    Files.copy(is, Paths.get(this.fileUpload + fileName), StandardCopyOption.REPLACE_EXISTING);
//                FileCopyUtils.copy(blogForm.getFiles().get().getBytes(), new File(this.fileUpload + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Cover cover = new Cover(fileName, blog);
                coverService.save(cover);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> update(@PathVariable("id") int id, @ModelAttribute BlogForm blogForm) throws IOException {

        Blog blog = blogService.findById(id).get();
        blog.setCategory(blogForm.getCategory());
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());
        blog.setAuthor(blogForm.getAuthor());
        if (blogForm.getFiles() != null) {
            coverService.deleteByBlogId(id);
            for (MultipartFile file : blogForm.getFiles()) {
                String fileName = file.getOriginalFilename();
                var is = file.getInputStream();
//                String fileUpload = environment.getProperty("upload.path");
                try {
                    Files.copy(is, Paths.get(this.fileUpload + fileName), StandardCopyOption.REPLACE_EXISTING);
//                    FileCopyUtils.copy(fileName.getBytes(), new File(this.fileUpload + fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Cover cover = new Cover(fileName, blog);
                coverService.save(cover);
            }
        } else {
            blog.setCovers(blog.getCovers());
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);

    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable(name = "id") int id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            coverService.deleteByBlogId(id);
            blogService.delete(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/blogs/search")
    public ResponseEntity<Map<String, Object>> searchBlog(@RequestParam(name = "key") String key,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int pageSize) {
        try {
            List<Blog> blogs = new ArrayList<Blog>();
            Pageable paging = PageRequest.of(page, pageSize);

            Page<Blog> pageBlogs = null;
            if(key == null){
                pageBlogs = blogRepository.findAll(paging);
            }

            else {
                System.out.println(key);
                pageBlogs = blogService.findBlogBySearchPage(key, paging);
            }


            blogs = pageBlogs.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("blogs", blogs);
            response.put("currentPage", pageBlogs.getNumber());
            response.put("totalItems", pageBlogs.getTotalElements());
            response.put("totalPages", pageBlogs.getTotalPages());

            if (blogs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


//
//}