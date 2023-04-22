package QuizQuestion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * All of the Questions for a Particular Jeopardy Category
 */
public class JeopardyCategory {

    //a few hard coded strings that jservice uses
    private final String CATEGORY = "title";
    private final String QUESTIONS = "clues";
    private final String QUESTION = "question";
    private final String ANSWER = "answer";

    //a few error messages
    public static final String CAST_ERROR_MSG = "cast to JSONObject Fail";
    public static final String PARSE_ERROR_MSG = "json parsing error";

    //The name of the category
    private String categoryName;

    //questions and answers are index aligned
    //questions.get(0) has answer located at answers.get(0)
    private List<String> questions;
    private List<String> answers;

    /**
     * Construct a Jeopardy Category from
     * @param jsonString
     * @throws IllegalArgumentException
     */
    public JeopardyCategory(String jsonString) throws IllegalArgumentException {
        parseJSONResponse(jsonString);
    }


    /**
     * Retrieve the category name
     * @return the category that this question belongs to
     */
    public String getName() {
        return categoryName;
    }

    /**
     * Retrieve the list of questions for this category
     * @return the List of all question in this category
     */
    public List< String > getQuestions() {
        return questions;
    }

    /**
     * retrieve the list of answers for this category
     * @return all of the possible answers for this category (aligned with questions)
     */
    public List< String > getAnswers() {
        return answers;
    }

    /**
     * Populate the Category Name and Questions and Answers from a JService API response to the /category API call
     * for example this method parses a repsonse from this url: http://jservice.io/api/category?id=5666
     * @param jsonString the raw response from the JService /category API call
     * @throws IllegalArgumentException on bad inputs or class cast exceptions (on occasion a jservice gives poorly formatted questions)
     */
    private void parseJSONResponse(String jsonString) throws IllegalArgumentException {

        JSONParser parser = new JSONParser();
        try {
            //possible class cast exception if someone supplies an array instead of object
            JSONObject jObj = (JSONObject) parser.parse(jsonString);
            categoryName = (String) jObj.get(CATEGORY);
            if (jObj.get(CATEGORY) == null) {
                throw new IllegalArgumentException(PARSE_ERROR_MSG) ;
            }
            JSONArray array = (JSONArray) jObj.get(QUESTIONS);
            if (array == null) {
                throw new IllegalArgumentException(PARSE_ERROR_MSG) ;
            }
            populateQuestionsAndAnswers(array);

        }catch(ParseException e) {
            e.printStackTrace();
            //it's possible this happens
            throw new IllegalArgumentException(PARSE_ERROR_MSG);
        } catch(ClassCastException e) {
            throw new IllegalArgumentException(CAST_ERROR_MSG);
        }
    }

    /**
     * Parse the Questions and Answers from a jservice.io jsonArray of questions and populate the Map instance field
     * @param array the Array of jservice json style questions
     *
     * EXAMPLE: jsonArray =  "[{"id":45766,"answer":"Shrub","question":"Azalea","value":200,"airdate":"2000-12-14T12:00:00.000Z","created_at":"2014-02-11T23:13:28.185Z","updated_at":"2014-02-11T23:13:28.185Z","category_id":5666,"game_id":null,"invalid_count":null,"category":{"id":5666,"title":"grub, shrub or beelzebub","created_at":"2014-02-11T23:13:27.932Z","updated_at":"2014-02-11T23:13:27.932Z","clues_count":5}}]"
     *          populates the questionAndAnswer map-> { "Azalea" : "Shrub" }
     */
    private void populateQuestionsAndAnswers(JSONArray array) {

        //erase whatever was here before
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject == null) {
                throw new IllegalArgumentException(PARSE_ERROR_MSG) ;
            }
            String q = (String) jsonObject.get(QUESTION);
            String a = (String) jsonObject.get(ANSWER);
            questions.add(q);
            answers.add(a);
        }
    }

    //simple demo of this class
    public static void main(String [] args ) {
        //for example the raw json response from this url:
        //http://jservice.io/api/category?id=5666
        //is:
        String rawResponse = "{\"id\":5666,\"title\":\"grub, shrub or beelzebub\",\"clues_count\":5,\"clues\":[{\"id\":45766,\"answer\":\"Shrub\",\"question\":\"Azalea\",\"value\":200,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45760,\"answer\":\"Grub\",\"question\":\"Antipasto\",\"value\":100,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45772,\"answer\":\"Beelzebub\",\"question\":\"Old Horny\",\"value\":300,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45778,\"answer\":\"Beelzebub (Biblical term)\",\"question\":\"Azalel\",\"value\":400,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null},{\"id\":45784,\"answer\":\"Grub (rabbit stew)\",\"question\":\"Hasenpfeffer\",\"value\":500,\"airdate\":\"2000-12-14T12:00:00.000Z\",\"category_id\":5666,\"game_id\":null,\"invalid_count\":null}]}";

        JeopardyCategory jCategory = new JeopardyCategory(rawResponse);
        System.out.println("Demo of Questions for Category: " + jCategory.getName());
        List<String> qs = jCategory.getQuestions();
        List<String> as = jCategory.getAnswers();
        for (int i = 0; i < qs.size(); i++) {
            System.out.println("Q: " + qs.get(i) + "? A: " + as.get(i));
        }
    }

}
