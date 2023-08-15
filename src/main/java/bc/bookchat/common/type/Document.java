package bc.bookchat.common.type;

import lombok.Data;

@Data
public class Document{
    public String title;
    public String isbn;
    public String[] authors;
    public String thumbnail;
}