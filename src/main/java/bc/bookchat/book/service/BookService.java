package bc.bookchat.book.service;

import bc.bookchat.book.controller.dto.BookInfo;
import bc.bookchat.book.controller.dto.GetBookQuery;
import bc.bookchat.book.controller.dto.KakaoResponse;
import bc.bookchat.common.response.CommonPageInfo;
import bc.bookchat.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final RestTemplate restTemplate;

    @Value("${kakao.baseurl}")
    private String baseUrl;

    @Value("${kakao.secret}")
    private String kakaoSecretKey;

    public PageResponse<BookInfo> getBooksPageResponse(GetBookQuery query) {
        KakaoResponse response= requestToApi(query);
        return convertToCommonPageResponse(query,response);
    }

    private KakaoResponse requestToApi(GetBookQuery query) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(headers);

        String url = query.toUrl(baseUrl);


        return restTemplate.exchange(url, HttpMethod.GET, request, KakaoResponse.class).getBody();
    }

    private PageResponse<BookInfo> convertToCommonPageResponse(GetBookQuery query,KakaoResponse kakaoResponse){

        List<BookInfo> bookInfoList= Arrays.stream(kakaoResponse.getDocuments()).map(BookInfo::toBookInfo).toList();
        CommonPageInfo pageInfo=kakaoResponse.getMeta()
                .toCommonPageInfo(query.getPage(),query.getSize());

        return new PageResponse(bookInfoList,pageInfo);

    }


}
