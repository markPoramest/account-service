package com.example.accountservice.services;

import com.example.accountservice.dto.IdCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CryptoClientServiceTest {

    @InjectMocks
    private CryptoClientService cryptoClientService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        cryptoClientService = new CryptoClientService();
        restTemplate = Mockito.mock(RestTemplate.class);

        ReflectionTestUtils.setField(cryptoClientService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(cryptoClientService, "baseUrl", "http://mock-crypto-service");
    }


    @Test
    void testEncryptIdCard_whenSuccess_shouldReturnEncryptedId() throws Exception {
        IdCard idCard = new IdCard("1234567890");
        IdCard expectedResponse = new IdCard("encryptedId");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<IdCard> entity = new HttpEntity<>(idCard, headers);

        when(restTemplate.exchange("http://mock-crypto-service/id-card/encrypt", HttpMethod.POST, entity, IdCard.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        String encryptedId = cryptoClientService.encryptIdCard(idCard);

        assert encryptedId.equals(expectedResponse.getIdCardNo());
    }

    @Test
    void testDecryptIdCard_whenSuccess_shouldReturnDecryptedId() throws Exception {
        IdCard idCard = new IdCard("encryptedId");
        IdCard expectedResponse = new IdCard("1234567890");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<IdCard> entity = new HttpEntity<>(idCard, headers);

        when(restTemplate.exchange("http://mock-crypto-service/id-card/decrypt", HttpMethod.POST, entity, IdCard.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        String decryptedId = cryptoClientService.decryptIdCard(idCard);

        assert decryptedId.equals(expectedResponse.getIdCardNo());
    }
}
