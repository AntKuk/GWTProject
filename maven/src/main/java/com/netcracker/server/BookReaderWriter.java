package com.netcracker.server;

import com.netcracker.shared.Book;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BookReaderWriter  {

    private String filePath;
    private List<Book> bookList = new ArrayList<>();

    public BookReaderWriter() {}

    public BookReaderWriter(String filePath) {
        this.filePath = filePath;
    }


    public List<Book> readFile() {

        URL file = this.getClass().getClassLoader().getResource("/books");
        JSONParser parser = new JSONParser();
        try(FileReader in = new FileReader(file.getFile()))
        {
            Object obj = parser.parse(in);
            JSONArray books = (JSONArray)obj;
            Iterator bookIterator = books.iterator();
            while(bookIterator.hasNext()) {
                JSONObject book = (JSONObject)bookIterator.next();
                int id = Integer.parseInt(book.get("id").toString());
                String author = book.get("author").toString();
                String title = book.get("title").toString();
                int pages = Integer.parseInt(book.get("pages").toString());
                int year = Integer.parseInt(book.get("year").toString());

                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = format.parse(book.get("date").toString());

                Book current = new Book(id, author, title, pages, year, date);

                this.bookList.add(current);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return bookList;
    }


    public void writeToFile(Book newBook) {
        readFile();
        newBook.setId(bookList.get(bookList.size()-1).getId()+1);
        this.bookList.add(newBook);
        writeListToFile(this.bookList);
    }

    private void writeListToFile(List<Book> list) {

        JSONArray mainList = new JSONArray();
        for(Book book : list) {
            JSONObject bookObject = new JSONObject();
            bookObject.put("id", Integer.toString(book.getId()));
            bookObject.put("author", book.getAuthor());
            bookObject.put("title", book.getTitle());
            bookObject.put("pages", Integer.toString(book.getPages()));
            bookObject.put("year", Integer.toString(book.getYear()));

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = book.getDate();
            String dateStr = format.format(date);
            bookObject.put("date", dateStr);

            mainList.add(bookObject);
        }
        URL file = this.getClass().getClassLoader().getResource("/books");

        try (FileWriter out = new FileWriter(file.getFile(), false)) {
            out.write(mainList.toJSONString());
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delBookFromFile(Book book) {

        readFile();
        int id = bookList.indexOf(book);
        this.bookList.remove(id);
        writeListToFile(this.bookList);

    }


}
