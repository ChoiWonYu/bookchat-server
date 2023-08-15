package bc.bookchat.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> results;

    private CommonPageInfo pageInfo;
}
