package com.netcracker.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.netcracker.shared.Book;
import com.netcracker.shared.FetchEntriesResult;


import java.util.ArrayList;
import java.util.List;

public interface BookWorkerServiceAsync {

    void getBooks(AsyncCallback<List<Book>> async);
    void getSortedBooks(AsyncCallback<List<Book>> async);
    void addBook(Book book, AsyncCallback<List<Book>> async);
    void delBook(Book book, AsyncCallback<List<Book>> async);
}
