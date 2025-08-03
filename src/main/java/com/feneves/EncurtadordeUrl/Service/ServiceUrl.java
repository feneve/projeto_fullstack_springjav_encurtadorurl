package com.feneves.EncurtadordeUrl.Service;

import com.feneves.EncurtadordeUrl.Dto.RequestDtoUrl;
import com.feneves.EncurtadordeUrl.Dto.ResponseDtoUrl;
import com.feneves.EncurtadordeUrl.Entity.Url;
import com.feneves.EncurtadordeUrl.Repository.RepositoryUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServiceUrl {

    @Autowired
    private RepositoryUrl repositoryUrl;

    @Value("${app.base-url}")
    private String baseUrl;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // Cria e salva a URL encurtada
    public ResponseDtoUrl createdUrl(RequestDtoUrl dto) {
        Url url = new Url();
        url.setCreatedData(LocalDateTime.now());
        url.setLongUrl(dto.getLongUrl());

        LocalDateTime expiration = dto.getExpiration() != null
                ? dto.getExpiration()
                : LocalDateTime.now().plusDays(15);
        url.setExpirationDate(expiration);

        // Primeiro salva pra gerar o ID
        url = repositoryUrl.save(url);

        String encoded = encodeBase62(url.getId());
        String shortUrl = baseUrl + "/api/" + encoded;

        return new ResponseDtoUrl(shortUrl);
    }
    public Optional<Url> getUrlById(Long id) {
        return repositoryUrl.findById(id);
    }

    // Decodifica de volta o Base62 para o ID numérico
    public Long decodeBase62(String shortCode) {
        long id = 0;
        for (int i = 0; i < shortCode.length(); i++) {
            id = id * 62 + BASE62.indexOf(shortCode.charAt(i));
        }
        return id;
    }


    // Busca a URL original usando o shortCode
    public Optional<Url> findByShortCode(String shortCode) {
        Long id = decodeBase62(shortCode);
        return repositoryUrl.findById(id);
    }

    // Codifica ID para base62
    public String encodeBase62(Long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(BASE62.charAt(remainder));
            id /= 62;
        }
        return sb.reverse().toString();
    }

}
