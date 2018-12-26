package com.netcracker.client;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;
import com.netcracker.shared.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BookWatcher implements EntryPoint {
    //define Panels
    private VerticalPanel mainPanel = new VerticalPanel();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private HorizontalPanel editPanel = new HorizontalPanel();

    //define Table
    private CellTable<Book> table = new CellTable<>();

    //define Labels
    private Label authorLabel = new Label("Author");
    private Label titleLabel = new Label("Title");
    private Label pagesLabel = new Label("Pages");
    private Label yearLabel = new Label("Publishing year");

    //define TextBoxes
    private TextBox authorTextBox = new TextBox();
    private TextBox titleTextBox = new TextBox();
    private TextBox pagesTextBox = new TextBox();
    private TextBox yearTextBox = new TextBox();

    //define Buttons
    private Button addButton = new Button("Add book");
    private ToggleButton sortButton = new ToggleButton("Sort by title");
    private Button deleteButton = new Button("Delete book");

    private final BookWorkerServiceAsync bookWorker = GWT.create(BookWorkerService.class);

    private List<Book> bookList = new ArrayList<>();
    private Book clikedBook = null;

    private class Callback implements AsyncCallback<List<Book>> {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Unable to obtain server response: "
                    + caught.getMessage());
        }
        @Override
        public void onSuccess(List<Book> result) {
            bookList = result;
            table.setRowData(bookList);
        }
    }


    @Override
    public void onModuleLoad() {

        initialize();

        bookWorker.getBooks(new Callback());

        sortButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(sortButton.getValue()) {
                    bookWorker.getSortedBooks(new Callback());
                }
                else {
                    bookWorker.getBooks(new Callback());
                }
            }
        });


        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(clikedBook != null) {
                    bookWorker.delBook(clikedBook, new Callback());
                    clikedBook = null;
                }
                else {
                    Window.alert("Please, choose a book, that you want to delete");
                }
            }
        });


        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setDefaultFieldStyle();

                if(validateInputFields()) {
                    Book newBook = createBook();
                    bookWorker.addBook(newBook, new Callback());
                    Window.alert("Book added");
                }
            }
        });


        NoSelectionModel<Book> selectionModelMyObj = new NoSelectionModel<>();
        SelectionChangeEvent.Handler tableHandler = new SelectionChangeEvent.Handler()
        {
            @Override
            public void onSelectionChange(SelectionChangeEvent event)
            {
                Book clickedObj = selectionModelMyObj.getLastSelectedObject();
                clikedBook = clickedObj;
            }
        };
        selectionModelMyObj.addSelectionChangeHandler( tableHandler );
        table.setSelectionModel(selectionModelMyObj);
        table.setStyleName("book-table");
        mainPanel.add(table);

        RootPanel.get("bookList").add(mainPanel);


        //End of onModuleLoad()
    }





    private void initialize() {
        TextColumn<Book> idColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return Integer.toString(book.getId());
            }
        };
        table.addColumn(idColumn, "Id");

        TextColumn<Book> authorColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getAuthor();
            }
        };
        table.addColumn(authorColumn, "Author");

        TextColumn<Book> titleColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getTitle();
            }
        };
        table.addColumn(titleColumn, "Title");

        TextColumn<Book> pagesColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return Integer.toString(book.getPages());
            }
        };
        table.addColumn(pagesColumn, "Pages");

        TextColumn<Book> yearColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return Integer.toString(book.getYear());
            }
        };
        table.addColumn(yearColumn, "Year");

        DateCell dateCell = new DateCell();
        Column<Book, Date> dateColumn = new Column<Book, Date>(dateCell) {
            @Override
            public Date getValue(Book object) {
                return object.getDate();
            }
        };
        table.addColumn(dateColumn, "Adding Date");

        addPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        sortButton.setWidth("69px");


        //Assemble Add Book Panel
        addPanel.add(authorLabel);
        addPanel.add(authorTextBox);

        addPanel.add(titleLabel);
        addPanel.add(titleTextBox);

        addPanel.add(pagesLabel);
        addPanel.add(pagesTextBox);

        addPanel.add(yearLabel);
        addPanel.add(yearTextBox);

        editPanel.add(addButton);
        editPanel.add(sortButton);
        editPanel.add(deleteButton);

        //Assemble Main panel
        mainPanel.add(addPanel);
        mainPanel.add(editPanel);

        //mainPanel.add(table);
    }

    private Book createBook() {
        //id will be set automatically on the server (BookReaderWriter)
        int id = 0;
        String author = authorTextBox.getText();
        String title = titleTextBox.getText();
        int pages = Integer.parseInt(pagesTextBox.getText());
        int year = Integer.parseInt(yearTextBox.getText());
        Date date = new Date();

        Book newBook = new Book(id, author, title, pages, year, date);

        return newBook;

    }

    private boolean isNumber(String str) {
        if (str.matches("[0-9]*")) {
            return true;
        }
        return false;
    }


    private boolean validateInputFields() {
        boolean result = true;
        String alert = "Please fill fields. \n";
        if(authorTextBox.getText().isEmpty()) {
            authorTextBox.setStyleName("field_error");
            result = false;
        }
        if(titleTextBox.getText().isEmpty()) {
            titleTextBox.setStyleName("field_error");
            result = false;
        }
        if(pagesTextBox.getText().isEmpty()|!isNumber(pagesTextBox.getText())) {
            pagesTextBox.setStyleName("field_error");
            alert += "Please, enter a number at Pages Field.\n";
            result = false;
        }
        if(yearTextBox.getText().isEmpty()|!isNumber(yearTextBox.getText())) {
            yearTextBox.setStyleName("field_error");
            alert += "Please, enter a number at Year Field.\n";
            result = false;
        }
        if(result == false) {
            Window.alert(alert);
        }

        return result;

    }

/*
    private boolean isEmpty() {
        boolean result = false;
        if(authorTextBox.getText().isEmpty()) {
            authorTextBox.setStyleName("field_error");
            result = true;
        }
        if(titleTextBox.getText().isEmpty()) {
            titleTextBox.setStyleName("field_error");
            result = true;
        }
        if(pagesTextBox.getText().isEmpty()|!isNumber(pagesTextBox.getText())) {
            pagesTextBox.setStyleName("field_error");
            //Window.alert("Please, enter a number at Pages Field");

            result = true;
        }
        if(yearTextBox.getText().isEmpty()|!isNumber(yearTextBox.getText())) {
            yearTextBox.setStyleName("field_error");
            //Window.alert("Please, enter a number at Year Field");
            result = true;
        }

        return result;

    }
*/
    private void setDefaultFieldStyle() {
        authorTextBox.setStyleName("field_ok");
        titleTextBox.setStyleName("field_ok");
        pagesTextBox.setStyleName("field_ok");
        yearTextBox.setStyleName("field_ok");
    }


}
