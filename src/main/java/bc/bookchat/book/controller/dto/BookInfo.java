package bc.bookchat.book.controller.dto;

import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.common.type.Document;
import lombok.Data;

@Data
public class BookInfo {
    private Long isbn;
    private String title;
    private String[] authors;
    private String thumbnail;

    public static BookInfo toBookInfo(Document document){

        BookInfo bookInfo=new BookInfo();
        bookInfo.authors=document.getAuthors();
        bookInfo.thumbnail= document.getThumbnail();
        bookInfo.title= document.getTitle();

        String documentIsbn=document.getIsbn();
        String[] isbnArr=documentIsbn.split(" ");

        if(isbnArr.length==1){
            bookInfo.isbn=Long.parseLong(isbnArr[0]);
        }

        else{
            String isbn10=isbnArr[0].length()==10?isbnArr[0]:isbnArr[1];
            bookInfo.isbn=Long.parseLong(isbn10);
        }

        return bookInfo;
    }

    public MajorBook toEntity(){
        return new MajorBook(isbn,title,authors[0],thumbnail);
    }
}
