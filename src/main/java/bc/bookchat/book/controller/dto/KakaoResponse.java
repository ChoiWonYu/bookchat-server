package bc.bookchat.book.controller.dto;

import bc.bookchat.common.type.Document;
import bc.bookchat.common.type.Meta;
import lombok.Data;

@Data
public class KakaoResponse {
    public  Document[] documents;
    public Meta meta;
}
