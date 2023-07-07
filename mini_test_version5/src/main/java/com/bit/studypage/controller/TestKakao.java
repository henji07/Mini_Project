package com.bit.studypage.controller;

import org.json.simple.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestKakao {

    private static final String KAKAO_REST_API_KEY = "b28f8bf3673a26eeec85c66abbdb7bfc"; // 발급받은 API KEY

    @GetMapping("/testmap")
    public ModelAndView convertAddressToCoordinates(String address) {
        ModelAndView mv = new ModelAndView();

        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + "강원도 삼척시 중앙로 346 (교동)";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JSONParser jsonParser = new JSONParser();
            try {
                System.out.println(responseEntity+"되나?");
                JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody());
                JSONArray documentsArray = (JSONArray) jsonObject.get("documents");

                if (!documentsArray.isEmpty()) {
                    JSONObject documents = (JSONObject) documentsArray.get(0);
                    JSONObject addressObject = (JSONObject) documents.get("address");

                    double lng = Double.parseDouble((String) addressObject.get("x"));
                    double lat = Double.parseDouble((String) addressObject.get("y"));
                    System.out.println(lng+"aaaaaaaaaaaaaaaaaaaa"+lat);
                    mv.setViewName("view/maptest.html");
                    mv.addObject("x", lng);
                    mv.addObject("y", lat);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return mv;
    }

}
