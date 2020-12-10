import java.util.*;

/**
 * A leaderboard of user streaks from the quiz game server
 */
public class LeaderBoard implements Modifiable{

    //A map of user names and streaks
    private Map<String, Integer> streaks;
    //A list to keep track of user rankings
    private ArrayList<User> rankings;

    public LeaderBoard() {
        streaks = new HashMap<>();
        rankings = new ArrayList<>();
    }

    /**
     update the current correct question answered streak for user.
     @param name: the user to update the streak for
     @param streak: the amount of the user's current streak
     */
    public void update (String name, int streak){
        //Needs lock to be thread-safe
        //Check for name
        if(streaks.containsKey(name)) {
            //Update streaks
            streaks.replace(name, streak);
        }
        else{
            //Add new user to streaks and rankings
            streaks.put(name, streak);
            rankings.add(new User(name));
        }
        //Update rankings
        Collections.sort(rankings, Collections.reverseOrder());

    }

    /**
     Remove a user from the LeaderBoard
     @param name: the user to remove
     **/
    public void delete (String name){
        //Needs lock to be thread-safe
        //Check for name
        if(streaks.containsKey(name)){
            //remove from streaks
            streaks.remove(name);
            //Remove from rankings
            for(User user : rankings){
                if (user.getName().equals(name)){
                    rankings.remove(user);
                }
            }
        }

    }

    /**
     Retrieve the active streak for a give user
     @param name: the user for which to retrieve the streak
     or 0 if no such user exists
     @return the current active streak for the given user or 0 if
     no such user exists
     **/
    public int get(String name){
        return streaks.getOrDefault(name, 0);
    }

    /**
     Convert the Leaderboard into an aesthetically pleasing (as per your own taste) String containing the Top 3 users with their active streaks
     @return a text version of the top 3 streaks
     **/
    public String prettyPrintTop3(){
        String top3 = "The top three user streaks are:\n";
        for(int i = 0; i < rankings.size() && i < 3; i++){
            top3 += "\n" + (i + 1) + ": " + rankings.get(i).getName() + "  " + streaks.get(rankings.get(i).getName());
        }
        return top3;
    }

    /**
     * Class to allow comparison between user streaks
     */
    private class User implements Comparable<User>{
        String name;

        /**
         * User constructor
         * @param name user name
         */
        User(String name){
            this.name = name;
        }
        String getName(){
            return name;
        }
        @Override
        public int compareTo(User user) {
            return streaks.get(name) - streaks.get(user.getName());
        }
    }
}
