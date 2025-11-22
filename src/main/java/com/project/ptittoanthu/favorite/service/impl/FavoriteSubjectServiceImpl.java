package com.project.ptittoanthu.favorite.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.favorite.model.FavoriteSubject;
import com.project.ptittoanthu.favorite.repository.FavoriteSubjectRepository;
import com.project.ptittoanthu.favorite.service.FavoriteSubjectService;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteSubjectServiceImpl implements FavoriteSubjectService {

    private final FavoriteSubjectRepository favoriteRepo;
    private final UserRepository userRepo;
    private final SubjectRepository subjectRepo;

    @Override
    public void toggleFavorite(Integer subjectId) {
        String email = SecurityUtils.getUserEmailFromSecurity();

        if (favoriteRepo.existsByUserEmailAndSubjectId(email, subjectId)) {
            FavoriteSubject fav = favoriteRepo
                    .findByUserEmailAndSubjectId(email, subjectId)
                    .orElseThrow();
            favoriteRepo.delete(fav);
            return;
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));

        FavoriteSubject fav = FavoriteSubject.builder()
                .user(user)
                .subject(subject)
                .build();

        favoriteRepo.save(fav);
    }

    @Override
    public boolean isFavorite(Integer subjectId) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        return favoriteRepo.existsByUserEmailAndSubjectId(email, subjectId);
    }

    @Override
    public PageResult<List<FavoriteSubject>> getMyFavorites(SearchRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        Sort sort = SortHelper.buildSort("f." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize(), sort);

        Page<FavoriteSubject> page = favoriteRepo.findAllBySearchAndUserEmail(request.getKeyword(), email, pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        return PageResult.<List<FavoriteSubject>>builder()
                .metaDataResponse(metaDataResponse)
                .data(page.getContent())
                .build();
    }
}
