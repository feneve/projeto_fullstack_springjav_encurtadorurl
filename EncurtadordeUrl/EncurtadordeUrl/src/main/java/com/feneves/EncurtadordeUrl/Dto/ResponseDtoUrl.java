package com.feneves.EncurtadordeUrl.Dto;


import lombok.Data;

@Data
public class ResponseDtoUrl {
    private String shortUrl;

    public ResponseDtoUrl(String shortUrl){
        this.shortUrl= shortUrl;
    }
}
