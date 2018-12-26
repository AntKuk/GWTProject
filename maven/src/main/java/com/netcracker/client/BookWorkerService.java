package com.netcracker.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.netcracker.shared.Book;


import java.util.List;

@RemoteServiceRelativePath("book")
public interface BookWorkerService extends RemoteService {
    List<Book> getBooks() throws IllegalArgumentException;
    List<Book> getSortedBooks() throws IllegalArgumentException;
    List<Book> addBook(Book book) throws IllegalArgumentException;
    List<Book> delBook(Book book) throws IllegalArgumentException;
}
