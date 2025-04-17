package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostSearchResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostListResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.UpdatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.CreatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
public interface PostService {
    GetPostResponse createPost(String userId, CreatePostRequest request);
    void updatePost(String userId, Long postId, UpdatePostRequest request);
    void deletePost(String userId, Long postId);
    GetPostListResponse getAllPosts(String sortType);
    GetPostResponse getPostDetail(String userId, Long postId);
    GetPostSearchResponse searchPosts(String userId, String keyword);

}
