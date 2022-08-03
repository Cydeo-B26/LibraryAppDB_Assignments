package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashboardStepsDef {
    LoginPage loginPage;
    DashBoardPage dashBoardPage;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String user) {
        loginPage=new LoginPage();
        loginPage.login(user);
        BrowserUtil.waitFor(3);

    }

    String actualNumber;
    @When("the librarian gets borrowed books number")
    public void the_librarian_gets_borrowed_books_number() {
        dashBoardPage=new DashBoardPage();
        actualNumber = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualNumber = " + actualNumber);
    }
    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        String query="select count(*) from book_borrow\n" +
                "where is_returned=0";
        DB_Util.runQuery(query);

        String expectedNumber = DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals(expectedNumber,actualNumber);

    }
}
