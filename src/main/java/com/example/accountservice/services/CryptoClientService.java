package com.example.accountservice.services;

import com.example.accountservice.dto.IdCardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CryptoClientService {
    @Value("${crypto.service.baseurl}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String encryptIdCard(IdCardDTO idCardData) {
        String url = baseUrl + "/id-card/encrypt";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<IdCardDTO> entity = new HttpEntity<>(idCardData, headers);

        IdCardDTO res =  restTemplate.exchange(
                url, HttpMethod.POST, entity, IdCardDTO.class).getBody();

        return res.getIdCardNo();
    }

    public String decryptIdCard(IdCardDTO idCardData) {
        String url = baseUrl + "/id-card/decrypt";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<IdCardDTO> entity = new HttpEntity<IdCardDTO>(idCardData, headers);

        IdCardDTO res =  restTemplate.exchange(
                url, HttpMethod.POST, entity, IdCardDTO.class).getBody();

        return res.getIdCardNo();
    }
}
