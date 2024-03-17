package com.example.accountservice.services;

import com.example.accountservice.dto.IdCard;
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

    public String encryptIdCard(IdCard idCardData) {
        String url = baseUrl + "/id-card/encrypt";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<IdCard> entity = new HttpEntity<>(idCardData, headers);

        IdCard res =  restTemplate.exchange(
                url, HttpMethod.POST, entity, IdCard.class).getBody();

        return res.getIdCardNo();
    }

    public String decryptIdCard(IdCard idCardData) {
        String url = baseUrl + "/id-card/decrypt";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<IdCard> entity = new HttpEntity<IdCard>(idCardData, headers);

        IdCard res =  restTemplate.exchange(
                url, HttpMethod.POST, entity, IdCard.class).getBody();

        return res.getIdCardNo();
    }
}
