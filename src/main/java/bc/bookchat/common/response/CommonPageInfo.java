package bc.bookchat.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonPageInfo {
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private boolean hasNextPage;
    private boolean hasPrevPage;
    private String nextPageUrl;
    private String prevPageUrl;

    public CommonPageInfo(int page,int size,long totalElements,int totalPages){
        this.page=page;
        this.size=size;
        this.totalPages=totalPages;
        this.totalElements=totalElements;
        this.hasNextPage=page<totalPages;
        this.hasPrevPage=page>1&&page<=totalPages;
    }

    public CommonPageInfo(int page,int size,long totalElements,int totalPages,String nextPageUrl, String prevPageUrl){
        this.page=page+1;
        this.size=size;
        this.totalPages=totalPages+1;
        this.totalElements=totalElements;
        this.hasNextPage=page<this.totalPages;
        this.hasPrevPage=page>1;
        this.nextPageUrl=nextPageUrl;
        this.prevPageUrl=prevPageUrl;
    }
}
