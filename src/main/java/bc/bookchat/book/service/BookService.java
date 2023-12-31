package bc.bookchat.book.service;

import bc.bookchat.book.controller.dto.BookInfo;
import bc.bookchat.book.controller.dto.GetBookQuery;
import bc.bookchat.book.controller.dto.KakaoResponse;
import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.book.repository.BookRepository;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.response.CommonPageInfo;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.common.type.SearchField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final RestTemplate restTemplate;

    private final BookRepository bookRepository;

    @Value("${kakao.baseurl}")
    private String baseUrl;

    @Value("${kakao.secret}")
    private String kakaoSecretKey;

    public PageResponse<BookInfo> getBooksPageResponse(GetBookQuery query) {
        KakaoResponse response= requestToApi(query);
        return convertToCommonPageResponse(query,response);
    }
    @Transactional
    public Optional<MajorBook> findMajorBookByIsbn(Long isbn){
       return bookRepository.findById(isbn);
    }

    @Transactional
    public BookInfo createBook(Long isbn) {
        BookInfo bookInfo=getBookByIsbn(isbn);
        bookRepository.save(bookInfo.toEntity());
        return bookInfo;
    }

    private BookInfo getBookByIsbn(Long isbn) {
        GetBookQuery query=new GetBookQuery(String.valueOf(isbn), SearchField.ISBN);
        KakaoResponse response=requestToApi(query);

        if(response.getDocuments().length<1){
            throw new CustomException(ErrorCode.INVALID_ISBN);
        }

        return BookInfo.toBookInfo(response.getDocuments()[0]);
    }

    private KakaoResponse requestToApi(GetBookQuery query) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoSecretKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity request = new HttpEntity(headers);

        URI url = query.toURI(baseUrl);

        log.info("query: "+query.getQuery());
        log.info("kakao api request url: "+url);
        try{
            return restTemplate.exchange(url, HttpMethod.GET, request, KakaoResponse.class).getBody();
        }catch(Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    private PageResponse<BookInfo> convertToCommonPageResponse(GetBookQuery query,KakaoResponse kakaoResponse){

        List<BookInfo> bookInfoList= Arrays.stream(kakaoResponse.getDocuments()).map(BookInfo::toBookInfo).toList();
        CommonPageInfo pageInfo=kakaoResponse.getMeta()
                .toCommonPageInfo(query.getPage(),query.getSize());

        return new PageResponse(bookInfoList,pageInfo);

    }



}
