package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserInfoStepDef {


    Set<String> expectedList=new LinkedHashSet<>();
    List<String> actualList=new ArrayList<>();

    @Given("Establish the database connection")
    public void establish_the_database_connection() {

        System.out.println("*******************************************");
        System.out.println("***** Connection is DONE via HOOKS ********");
        System.out.println("*******************************************");

    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        String query="select id from users";
        DB_Util.runQuery(query);
        actualList = DB_Util.getColumnDataAsList(1);

        expectedList.addAll(actualList);
        System.out.println("expectedList = " + expectedList);
        System.out.println("actualList = " + actualList);

    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        Assert.assertEquals(expectedList.size(), actualList.size());
    }

    List<String> actualColumnNameList=new ArrayList<>();
    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        String query="select * from users";
        DB_Util.runQuery(query);

        actualColumnNameList = DB_Util.getAllColumnNamesAsList();
    }

    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> expectedColumnNameList) {
        System.out.println("actualColumnNameList = " + actualColumnNameList);
        System.out.println("expectedColumnNameList = " + expectedColumnNameList);
        Assert.assertEquals(expectedColumnNameList, actualColumnNameList);
    }
}
