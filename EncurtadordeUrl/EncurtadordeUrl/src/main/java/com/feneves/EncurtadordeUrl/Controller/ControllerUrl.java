package com.feneves.EncurtadordeUrl.Controller;

import com.feneves.EncurtadordeUrl.Dto.RequestDtoUrl;
import com.feneves.EncurtadordeUrl.Dto.ResponseDtoUrl;
import com.feneves.EncurtadordeUrl.Entity.Url;
import com.feneves.EncurtadordeUrl.Service.ServiceUrl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControllerUrl {

    @Autowired
    private ServiceUrl serviceUrl;

    @PostMapping("/short")
    public ResponseEntity<ResponseDtoUrl> shortUrl(@RequestBody @Valid RequestDtoUrl url) {
        ResponseDtoUrl responseDtoUrl = serviceUrl.createdUrl(url);
        return ResponseEntity.ok(responseDtoUrl);
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        // Chama o decode e busca via Service
        Long id = serviceUrl.decodeBase62(shortCode);
        Optional<Url> optionalUrl = serviceUrl.getUrlById(id);

        if (optionalUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Url url = optionalUrl.get();

        if (url.getExpirationDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).build(); // Expirada
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getLongUrl()))
                .build();
    }

}
