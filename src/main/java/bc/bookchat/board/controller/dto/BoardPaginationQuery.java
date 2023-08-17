package bc.bookchat.board.controller.dto;

import bc.bookchat.board.entity.Board;
import bc.bookchat.common.response.CommonPageInfo;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class BoardPaginationQuery {
    @Min(1)
    private int page=1;

    @Min(1)
    private int size=3;

    private String keyword;

    public boolean hasKeyword(){
        return keyword!=null;
    }

    public CommonPageInfo toPageInfo(Page<Board> page){
        return new CommonPageInfo(this.page,size,page.getTotalElements(),page.getTotalPages());
    }
}
