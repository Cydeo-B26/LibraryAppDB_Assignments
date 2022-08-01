package com.library.steps;

import com.library.pages.*;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class BooksStepDefs {
    List<String> actualCategoryList;
    BookPage bookPage=new BookPage();



    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashBoardPage().navigateModule(moduleName);


    }

    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
        actualCategoryList= BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("actualCategoryList = " + actualCategoryList);
    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        String query="select name from book_categories";

        DB_Util.runQuery(query);

        //store data
        List<String> expectedCategoryList = DB_Util.getColumnDataAsList(1);



        //Assertions
        Assert.assertEquals(expectedCategoryList,actualCategoryList);
    }

    String bookName;

    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String book) {
        bookName = book;
        BrowserUtil.waitForClickablility(bookPage.search, 5).sendKeys(bookName);
        BrowserUtil.waitFor(3);
    }

    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();
    }

    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {

        BrowserUtil.waitFor(3);

        //get data from UI

        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthorName = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualDesc = bookPage.description.getAttribute("value");

        //get related book info from DB
        String query="select name,author,isbn,description,year from books where name ='"+bookName+"'";

        DB_Util.runQuery(query);

        Map<String, String> rowMap = DB_Util.getRowMap(1);

        String expectedBookName = rowMap.get("name");
        String expectedAuthorName = rowMap.get("author");
        String expectedISBN = rowMap.get("isbn");
        String expectedDesc = rowMap.get("description");
        String expectedYear = rowMap.get("year");

        //Assertion

        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedAuthorName,actualAuthorName);
        Assert.assertEquals(expectedISBN,actualISBN);
        Assert.assertEquals(expectedDesc,actualDesc);
        Assert.assertEquals(expectedYear,actualYear);

    }
    String actualCategory;
    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        String query = "select bc.name,count(*) from book_borrow bb inner join books b on bb.book_id = b.id\n" +
                "inner join book_categories bc on b.book_category_id = bc.id\n" +
                "group by bc.name\n" +
                "order by 2 desc ";

        DB_Util.runQuery(query);
        actualCategory = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualCategory = " + actualCategory);

    }
    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedCategory) {
        Assert.assertEquals(expectedCategory, actualCategory);


    }




    //us06 steps

    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        bookPage.addBook.click();


    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String name) {

        bookPage.bookName.sendKeys(name);
    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        bookPage.isbn.sendKeys(isbn);

    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        bookPage.year.sendKeys(year);

    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        bookPage.author.sendKeys(author);

    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String category) {
        BrowserUtil.selectOptionDropdown(bookPage.categoryDropdown,category);
    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
       bookPage.saveChanges.click();
    }

    @Then("verify “The book has been created\" message is displayed")
    public void verify_the_book_has_been_created_message_is_displayed() {
        BrowserUtil.waitFor(1);
        System.out.println(bookPage.toastMessage.getText());
        Assert.assertTrue(bookPage.toastMessage.isDisplayed());
    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String expectedBookName) {

        BrowserUtil.waitFor(3);

        //get related book info from DB
        String query="select name,author,isbn from books where name ='"+expectedBookName+"'";

        DB_Util.runQuery(query);

        Map<String, String> rowMap = DB_Util.getRowMap(1);

        String actualBookName = rowMap.get("name");
        String actualAuthorName = rowMap.get("author");
        String actualISBN = rowMap.get("isbn");

        System.out.println(actualBookName);
        System.out.println(actualAuthorName);
        System.out.println(actualISBN);


        //Assertion

        Assert.assertEquals(expectedBookName,actualBookName);


    }


    //us07
    @When("the user clicks Borrow Book")
    public void the_user_clicks_borrow_book() {
        bookPage.borrowBook(bookName);
    }


    @Then("verify that book is shown in \"Borrowing Books” page")
    public void verify_that_book_is_shown_in_borrowing_books_page() {
        BorrowedBooksPage borrowedBooksPage=new BorrowedBooksPage();
        new DashBoardPage().navigateModule("Borrowing Books");
        BrowserUtil.waitFor(2);
        System.out.println(BrowserUtil.getElementsText(borrowedBooksPage.allBorrowedBooksName));
        Assert.assertTrue(BrowserUtil.getElementsText(borrowedBooksPage.allBorrowedBooksName).contains(bookName));
    }
    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {
            String query="select b.name from users u\n" +
                    "inner join book_borrow bb on u.id = bb.user_id\n" +
                    "inner join books b on bb.book_id = b.id\n" +
                    "where full_name='Test Student 1' and name='"+bookName+"'";
        System.out.println(bookName);

        DB_Util.runQuery(query);

        List<String> actualList = DB_Util.getColumnDataAsList(1);
        Assert.assertTrue(actualList.contains(bookName));
    }











}
