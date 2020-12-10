import QuizQuestion.JeopardyCategory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit-tests for the LeaderBoard class
 */

//Basic unit-tests
public class LeaderBoardTests {
    @Test
    public void UpdateTest() {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.update("__Jazz__", 4);
        assertEquals(4, leaderBoard.get("__Jazz__"));
    }

    @Test
    public void prettyPrintTop3Test() {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.update("__Jazz__", 4);
        leaderBoard.update("kwiver", 3);
        leaderBoard.update("Tweakd", 2);
        leaderBoard.update("kingslayer", 1);
        assertEquals("The top three user streaks are:\n" + "\n1: " + "__Jazz__" + "  " + 4
                + "\n2: " + "kwiver" + "  " + 3
                + "\n3: " + "Tweakd" + "  " + 2, leaderBoard.prettyPrintTop3());

    }

    @Test
    public void deleteTest() {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.update("__Jazz__", 4);
        leaderBoard.update("kwiver", 3);
        leaderBoard.update("Tweakd", 2);
        leaderBoard.update("kingslayer", 1);
        leaderBoard.delete("Tweakd");
        assertEquals("The top three user streaks are:\n" + "\n1: " + "__Jazz__" + "  " + 4
                + "\n2: " + "kwiver" + "  " + 3
                + "\n3: " + "kingslayer" + "  " + 1, leaderBoard.prettyPrintTop3());
    }

    @Test
    public void LessThan3UsersTest() {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.update("__Jazz__", 4);
        leaderBoard.update("kwiver", 3);
        assertEquals("The top three user streaks are:\n" + "\n1: " + "__Jazz__" + "  " + 4
                + "\n2: " + "kwiver" + "  " + 3, leaderBoard.prettyPrintTop3());
    }

    //Thread-safe unit-tests

}
