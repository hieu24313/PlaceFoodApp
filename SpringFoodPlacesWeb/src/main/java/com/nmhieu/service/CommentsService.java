/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.service;

import com.nmhieu.pojo.Comments;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */

public interface CommentsService {
    List<Comments> getComments(int foodId, Map<String, String> params);
    Comments addComment(Map<String, String> params, MultipartFile avatar);
    int countComments(int foodId);
    int checkComment(int foodId, Map<String, String> params);
}
