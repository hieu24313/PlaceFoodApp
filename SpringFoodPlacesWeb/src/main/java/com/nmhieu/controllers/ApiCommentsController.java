/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.controllers;

import com.nmhieu.pojo.Comments;
import com.nmhieu.pojo.Users;
import com.nmhieu.service.CommentsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */

@RestController
@RequestMapping("/api")

public class ApiCommentsController {
    
    @Autowired
    private CommentsService commentsService;
    
    @Autowired
    private Environment environment;
    
    @GetMapping(path = "/foodItems/{foodId}/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<Comments>> listComments(@PathVariable(value = "foodId") int foodId, @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.commentsService.getComments(foodId, params), HttpStatus.OK);
    }
    
    @GetMapping(path = "/foodItems/{foodId}/countFoodItems/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Map<String, String>> countFoodItems(@PathVariable(value = "foodId") int foodId) {
        Map<String, String> params = new HashMap<>();
        int pageSizeComments = Integer.parseInt(this.environment.getProperty("PAGE_SIZE_COMMENTS"));
        int countCommentsByFoodId = this.commentsService.countComments(foodId);
        params.put("page-size-comments", this.environment.getProperty("PAGE_SIZE_COMMENTS"));
        params.put("count-comments-by-foodId", String.valueOf(this.commentsService.countComments(foodId)));
        int pages = (int) Math.ceil((countCommentsByFoodId * 1.0) / pageSizeComments);
        
        params.put("pages", String.valueOf(pages));
        return new ResponseEntity<>(params, HttpStatus.OK);
    }
    
    @PostMapping(path = "/add-comment/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Comments> addComment(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        Comments comment = this.commentsService.addComment(params, avatar);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    
}
