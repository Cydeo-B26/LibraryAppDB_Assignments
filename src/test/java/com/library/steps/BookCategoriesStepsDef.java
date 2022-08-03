package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BookCategoriesStepsDef {
    String actualGenre;
    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        String query="select bc.name,count(*) from book_borrow bb\n" +
                "        inner join books b on bb.book_id = b.id\n" +
                "        inner join book_categories bc on b.book_category_id = bc.id\n" +
                "group by bc.name\n" +
                "order by 2 desc";

        DB_Util.runQuery(query);

         actualGenre = DB_Util.getFirstRowFirstColumn();
        System.out.println(DB_Util.getCellValue(1, 2));

    }
    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedGenre) {
        Assert.assertEquals(expectedGenre,actualGenre);

    }
}
