package com.bit.studypage.controller;
/* Java 1.8 샘플 코드 */
import com.bit.studypage.dto.ResponseDTO;
import com.bit.studypage.entity.SearchBoard;
import com.bit.studypage.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bit.studypage.entity.QuaPlace;
import com.bit.studypage.entity.Qualification;
import com.bit.studypage.repository.QuaPlaceRepository;
import com.bit.studypage.repository.QualificationRepository;
import com.bit.studypage.service.QualificationService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
    private QualificationService qualificationService;
    private ObjectMapper objectMapper;
    private QuaPlaceRepository quaPlaceRepository;

    public ApiController(ObjectMapper objectMapper, QualificationService qualificationService, QuaPlaceRepository quaPlaceRepository) {
        this.objectMapper = objectMapper;
        this.qualificationService = qualificationService;
        this.quaPlaceRepository = quaPlaceRepository;
    }
    @Autowired
    private SearchService searchService;

    @GetMapping("/apitest")
    public void test(/*@RequestParam("name")String jmcd*/) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(jmcd+"DDDDDDDDDDDDDDDDD");
//        String jmcdNum = qualificationService.findJmfldnm(jmcd);
//        System.out.println(")))))))))))))))))))))))"+jmcdNum);
        List<String> list = new ArrayList<>();
        for (int i = 10; i <= 24; i++) {
            list.add(String.format("%02d", i));
        }
        for (String num : list) {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.q-net.or.kr/api/service/rest/InquiryExamAreaSVC/getList?brchCd=" + num + "&ServiceKey=KPwD4FMIWudb2fU%2Fk5Ebb%2BJTG1kGh8tla0WBx3lMyrdVjhMto7uPQz1x%2BBcnAOm0I5c5POIQFhMGF5NqfDYsMw%3D%3D&_type=json"); /*URL*/
//        urlBuilder.append("&" + URLEncoder.encode("jmCd","UTF-8") +"="+jmcdNum); /*과목코드*/
//        System.out.println(urlBuilder);
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println("wwWwwwwwwwwwww" + sb.toString());

//            String response = sb.toString();
//            // JSON 데이터를 Java Object로 변환
//            JsonNode jsonNode = objectMapper.readTree(response);
//            // 필요한 item들만 추출
//            JsonNode items = jsonNode.path("response").path("body").path("items").path("item");
//            // 각 item을 Entity로 변환 후 저장
//            TestKakao testKakao = new TestKakao();
//            for (JsonNode item : items) {
//                QuaPlace quaPlace = objectMapper.treeToValue(item, QuaPlace.class);
//                quaPlace.setX(testKakao.convertAddressToCoordinates(quaPlace.getAddress())[0]);
//                quaPlace.setY(testKakao.convertAddressToCoordinates(quaPlace.getAddress())[1]);
//                quaPlaceRepository.save(quaPlace);
//                System.out.println("xy포함" + quaPlace.toString());
//            }
        }
//        return sb.toString();
//        String xmlString = sb.toString();

//        JAXBContext jaxbContext = JAXBContext.newInstance(QualificationDTO.class);
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        StringReader reader = new StringReader(xmlString);
//        Qualification response = (Qualification) unmarshaller.unmarshal(reader);
//        qualificationRepository.save(response);
    }
//    @PostMapping("/api/searchMainBox")
//    public String searchPostsMain(@RequestParam("keyword") String keyword, Model model) {
//        System.out.println(keyword);
//        List<SearchBoard> searchResults = searchService.searchBoardByTitleContent(keyword);
//        System.out.println(searchResults);
//        model.addAttribute("searchResults", searchResults);
//        model.addAttribute("keyword", keyword);
//        return "/fullSearch";  // fullSearch 페이지를 반환합니다.
//    }

}