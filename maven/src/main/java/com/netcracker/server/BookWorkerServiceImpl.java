package com.netcracker.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netcracker.client.BookWorkerService;
import com.netcracker.shared.Book;
import com.netcracker.shared.FetchEntriesResult;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookWorkerServiceImpl extends RemoteServiceServlet implements BookWorkerService {
    private String filePath;

    @Override
    public void init() throws ServletException {
        this.filePath = getServletConfig().getServletContext().getRealPath("/books");
    }


    @Override
    public List<Book> getBooks() throws IllegalArgumentException {
        BookReaderWriter reader = new BookReaderWriter();
        List<Book> listOfBooks = reader.readFile();

        return listOfBooks;
    }

    @Override
    public List<Book> getSortedBooks() throws IllegalArgumentException {
        BookReaderWriter reader = new BookReaderWriter();
        List<Book> list = reader.readFile();
        Collections.sort(list);
        return list;
    }

    @Override
    public List<Book> addBook(Book book) throws IllegalArgumentException {
        BookReaderWriter reader = new BookReaderWriter();
        reader.writeToFile(book);
        return new BookReaderWriter().readFile();
    }

    @Override
    public List<Book> delBook(Book book) throws IllegalArgumentException {
        BookReaderWriter reader = new BookReaderWriter();
        reader.delBookFromFile(book);
        return new BookReaderWriter().readFile();
    }



}
