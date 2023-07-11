package com.bit.studypage.controller;

import com.bit.studypage.entity.QuaPlace;
import com.bit.studypage.repository.QuaPlaceRepository;
import org.json.simple.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestKakao {
    private QuaPlaceRepository quaPlaceRepository;
    TestKakao(QuaPlaceRepository quaPlaceRepository){
        this.quaPlaceRepository = quaPlaceRepository;
    }

    private static final String KAKAO_REST_API_KEY = "b28f8bf3673a26eeec85c66abbdb7bfc"; // 발급받은 API KEY

    @GetMapping("/testmap")
    public ModelAndView convertAddressToCoordinates(String address) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/maptest.html");
        System.out.println("주소나오나"+address);

        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + "역삼동640-5";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody());
                JSONArray documentsArray = (JSONArray) jsonObject.get("documents");

                if (!documentsArray.isEmpty()) {
                    JSONObject documents = (JSONObject) documentsArray.get(0);
                    JSONObject addressObject = (JSONObject) documents.get("address");

                    if(addressObject != null && addressObject.containsKey("x") && addressObject.containsKey("y")) {
                        double lng = Double.parseDouble((String) addressObject.get("x"));
                        double lat = Double.parseDouble((String) addressObject.get("y"));
                        List<QuaPlace> quaPlaceList = quaPlaceRepository.findNearByLocations(lat,lng);
                        mv.addObject("quaPlaces",quaPlaceList);
                        mv.addObject("y",lat);
                        mv.addObject("x",lng);
                    } else {
                        mv.addObject("xys",null);
                    }
                } else {
                    mv.addObject("xys",null);
                }
            } catch (ParseException e) {
                mv.addObject("xys",null);
            }
        } else {
            mv.addObject("xys",null);
        }
        return mv;
    }

}
