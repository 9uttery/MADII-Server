package com.guttery.madii.common.serializer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserPrincipalSerializer {

    private final ObjectMapper objectMapper;

    public String serialize(final UserPrincipal userPrincipal) {
        try {
            return objectMapper.writeValueAsString(userPrincipal);
        } catch (final JsonProcessingException e) {
            throw CustomException.of(ErrorDetails.JSON_PROCESSING_ERROR);
        }
    }

    public UserPrincipal deserialize(final String authenticationInfoString) {
        try {
            return objectMapper.readValue(authenticationInfoString, UserPrincipal.class);
        } catch (final JsonProcessingException e) {
            throw CustomException.of(ErrorDetails.JSON_PROCESSING_ERROR);
        }
    }
}

