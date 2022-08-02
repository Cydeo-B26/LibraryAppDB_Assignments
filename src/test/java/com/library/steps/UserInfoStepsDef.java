package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class UserInfoStepsDef {
    String actualResult;
    @Given("Establish the database connection")
    public void establish_the_database_connection() {
        DB_Util.createConnection();
    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        String query1="select count(id) from users";  // 566

        DB_Util.runQuery(query1);

        actualResult = DB_Util.getFirstRowFirstColumn();

        System.out.println("actualResult = " + actualResult);


    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        String query2="select count(distinct id) from users"; // 566   500


        DB_Util.runQuery(query2);

        String expectedResult = DB_Util.getCellValue(1, 1);

        System.out.println("expectedResult = " + expectedResult);

        //Make assertions

        Assert.assertEquals(expectedResult,actualResult);



    }
}
