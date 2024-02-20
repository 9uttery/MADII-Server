package com.guttery.madii.domain.placeholder.application.service;

import com.guttery.madii.domain.placeholder.application.dto.PlaceholderResponse;
import com.guttery.madii.domain.placeholder.application.dto.UpdatePlaceholderRequest;
import com.guttery.madii.domain.placeholder.domain.model.Placeholders;
import com.guttery.madii.domain.placeholder.domain.repository.PlaceholdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PlaceholderService {
    private final PlaceholdersRepository placeholdersRepository;

    //Redis 캐시 추가할 것
    public PlaceholderResponse getPlaceholder() {
        return placeholdersRepository.findById(Placeholders.FIXED_DOCUMENT_ID)
                .map(placeholder -> new PlaceholderResponse(placeholder.getContents()))
                .orElseGet(() -> new PlaceholderResponse("누워서 빗소리 감상하기"));
    }

    public void updatePlaceholder(final UpdatePlaceholderRequest updatePlaceholderRequest) {
        final Placeholders newPlaceholders = Placeholders.create(updatePlaceholderRequest.contents());
        placeholdersRepository.save(newPlaceholders);
    }
}
