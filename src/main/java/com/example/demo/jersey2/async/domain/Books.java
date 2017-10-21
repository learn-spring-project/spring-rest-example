package com.example.demo.jersey2.async.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */
@XmlRootElement(name = "books")
public class Books {
    private List<Book> bookList;

    public Books(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Books() {
    }

    @XmlElement(name = "book")
    @XmlElementWrapper
    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Books{" +
                "bookList=" + bookList +
                '}';
    }
}
