package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.dto.PostDTO.PostRequest.UpdatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.CreatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
public interface PostService {
    GetPostResponse createPost(String userId, CreatePostRequest request);
    void updatePost(String userId, Long postId, UpdatePostRequest request);
    void deletePost(String userId, Long postId);
}
