package bc.bookchat.common.type;

import bc.bookchat.common.response.CommonPageInfo;
import lombok.Data;

@Data
public class Meta{
    public Integer total_count;
    public Integer pageable_count;
    public Boolean is_end;

    public CommonPageInfo toCommonPageInfo(int page,int size){
        return new CommonPageInfo(page,size,Math.min(total_count,50*size),
                Math.min(pageable_count / size, 49)+1);
    }
}
