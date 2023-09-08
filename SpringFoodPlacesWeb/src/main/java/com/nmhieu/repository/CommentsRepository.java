/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.repository;

import com.nmhieu.pojo.Comments;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public interface CommentsRepository {
    List<Comments> getComments(int foodId, Map<String, String> params);
    Comments addComment(Comments comment);
    int countComments(int foodId);
}
