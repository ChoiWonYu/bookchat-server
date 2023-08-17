package bc.bookchat.common.type;

import bc.bookchat.book.controller.dto.GetBookQuery;
import bc.bookchat.common.response.CommonPageInfo;
import lombok.Data;

@Data
public class Meta{
    public Integer total_count;
    public Integer pageable_count;
    public Boolean is_end;

    public CommonPageInfo toCommonPageInfo(int page,int size){
        return new CommonPageInfo(page,size,total_count,pageable_count);
    }
}
