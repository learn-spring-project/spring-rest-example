package com.example.demo.jersey2.async.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/21.
 */

@XmlRootElement
public class Book implements Serializable {
    private Long bookId;
    private String bookName;
    private String publisher;

    public Book() {
    }

    public Book(Long bookId, String bookName, String publisher) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publisher = publisher;
    }

    @XmlAttribute(name = "bookId")
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @XmlAttribute(name = "bookName")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @XmlAttribute(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
