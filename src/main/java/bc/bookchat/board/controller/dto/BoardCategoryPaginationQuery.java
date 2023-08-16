package bc.bookchat.board.controller.dto;

import bc.bookchat.common.annotation.ValidEnum;
import bc.bookchat.common.type.BoardCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCategoryPaginationQuery extends BoardPaginationQuery{
    Long isbn;

    @ValidEnum
    BoardCategory category;

    @Override
    public void setKeyword(String keyword) {
        super.setKeyword(keyword);
    }

    @Override
    public String getKeyword() {
        return super.getKeyword();
    }

    @Override
    public void setPage(int page) {
        super.setPage(page);
    }

    @Override
    public int getPage() {
        return super.getPage();
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
    }

    @Override
    public int getSize() {
        return super.getSize();
    }
}
