package com.example.popup.service;

import com.example.popup.domain.Popup;
import com.example.popup.domain.Review;
import com.example.popup.dto.ReqDto.PopupReqDto;
import com.example.popup.dto.ResDto.GlobalResDto;
import com.example.popup.dto.ResDto.PopupOneResDto;
import com.example.popup.exception.CustomException;
import com.example.popup.exception.ErrorCode;
import com.example.popup.repository.PopupRepository;
import com.example.popup.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupRepository popupRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Popup getSave(PopupReqDto popupReqDto) {

        Popup popup = new Popup(popupReqDto);
        popupRepository.save(popup);

        return popup;
    }

    @Transactional

    public GlobalResDto<?> getOnePopup(Long popupId) {

        Popup popup = popupRepository.findById(popupId).orElseThrow(
                () -> new CustomException(ErrorCode.Not_Found_Popup)
        );

        List<Review> reviews = reviewRepository.findAll();

        PopupOneResDto popupOneResDto = new PopupOneResDto(popup, reviews);

        return GlobalResDto.success(popupOneResDto,null);
    }
}
