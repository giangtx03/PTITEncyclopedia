package com.project.ptittoanthu.favorite.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.favorite.model.Bookmark;

import java.util.List;

public interface BookmarkService {
    void toggleBookmark(Integer documentId);
    boolean isBookmark(Integer documentId);
    PageResult<List<Bookmark>> getMyBookmarks(SearchRequest request);
}
