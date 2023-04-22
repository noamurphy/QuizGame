import QuizQuestion.JeopardyCategory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit-tests for the Jeopardy Category Class
 */
public class JeopardyCategoryTests {


    //TEST the exceptions

    @Test
    //This test should produce an IllegalArgument Exception
    public void notJSONObjectOuterTest( ) {
        String jsonArray = "[{\"object\":1}]";
        try {
            JeopardyCategory cat = new JeopardyCategory(jsonArray);
        }catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), JeopardyCategory.CAST_ERROR_MSG);
        }
    }

    @Test
    //This test should produce an IllegalArgument Exception
    public void missingJSONObjectNameTest( ) {
        String jsonArray = "{\"object\":1}"; //not formatted correctly - missing category for example
        try {
            JeopardyCategory cat = new JeopardyCategory(jsonArray);
        }catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), JeopardyCategory.PARSE_ERROR_MSG);
        }
    }

    @Test
    //This test should produce an IllegalArgument Exception
    public void illFormedJSON( ) {
        String jsonArray = "{\"{object\":1}"; //not formatted correctly - missing category for example
        try {
            JeopardyCategory cat = new JeopardyCategory(jsonArray);
        }catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), JeopardyCategory.PARSE_ERROR_MSG);
        }
    }


    //A FEW TESTS Below for a specific Input example taken from the following URL:
    //http://jservice.io/api/category?id=5666

    @Test
    public void testCategoryNameGrubShrubBeelzebub() {
        String rawResponse = "{\"id\":5666,\"title\":\"grub, shrub or beelzebub\",\"clues_count\":5,\"clues\":[{\"id\":45766,\"answer\":\"Shrub\",\"question\":\"Azalea\",\"value\":200,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45760,\"answer\":\"Grub\",\"question\":\"Antipasto\",\"value\":100,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45772,\"answer\":\"Beelzebub\",\"question\":\"Old Horny\",\"value\":300,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45778,\"answer\":\"Beelzebub (Biblical term)\",\"question\":\"Azalel\",\"value\":400,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45784,\"answer\":\"Grub (rabbit stew)\",\"question\":\"Hasenpfeffer\",\"value\":500,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null}]}";
        JeopardyCategory jCategory = new JeopardyCategory(rawResponse);

        assertEquals("grub, shrub or beelzebub", jCategory.getName());
    }

    @Test
    public void testQuestionCountGrubShrubBeelzebub() {
        String rawResponse = "{\"id\":5666,\"title\":\"grub, shrub or beelzebub\",\"clues_count\":5,\"clues\":[{\"id\":45766,\"answer\":\"Shrub\",\"question\":\"Azalea\",\"value\":200,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45760,\"answer\":\"Grub\",\"question\":\"Antipasto\",\"value\":100,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45772,\"answer\":\"Beelzebub\",\"question\":\"Old Horny\",\"value\":300,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45778,\"answer\":\"Beelzebub (Biblical term)\",\"question\":\"Azalel\",\"value\":400,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45784,\"answer\":\"Grub (rabbit stew)\",\"question\":\"Hasenpfeffer\",\"value\":500,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null}]}";
        JeopardyCategory jCategory = new JeopardyCategory(rawResponse);
        assertEquals(5, jCategory.getQuestions().size());
        assertEquals(5, jCategory.getAnswers().size());
    }

    @Test
    public void testAntipastoAnswerGrubShrubBeelzebub() {
        String rawResponse = "{\"id\":5666,\"title\":\"grub, shrub or beelzebub\",\"clues_count\":5,\"clues\":[{\"id\":45766,\"answer\":\"Shrub\",\"question\":\"Azalea\",\"value\":200,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45760,\"answer\":\"Grub\",\"question\":\"Antipasto\",\"value\":100,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45772,\"answer\":\"Beelzebub\",\"question\":\"Old Horny\",\"value\":300,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45778,\"answer\":\"Beelzebub (Biblical term)\",\"question\":\"Azalel\",\"value\":400,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45784,\"answer\":\"Grub (rabbit stew)\",\"question\":\"Hasenpfeffer\",\"value\":500,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null}]}";
        JeopardyCategory jCategory = new JeopardyCategory(rawResponse);
        assertEquals("Shrub", jCategory.getAnswers().get(0));
    }
}
