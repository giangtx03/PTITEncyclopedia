package com.project.ptittoanthu.favorite.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.favorite.model.FavoriteSubject;

import java.util.List;

public interface FavoriteSubjectService {
    void toggleFavorite(Integer subjectId);
    boolean isFavorite(Integer subjectId);
    PageResult<List<FavoriteSubject>> getMyFavorites(SearchRequest request);
}
