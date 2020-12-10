public interface Modifiable {
    /**
     update the current correct question answered streak for
     user: name to be streaks

     @param name: the user to update the streak for
     @param streak: the amount of the user's current streak

     */
    public void update (String name, int streak);

    /**
     Remove a user from the LeaderBoard
     @param name: the user to remove
     **/
    public void delete (String name);

    /**
     Retrieve the active streak for a give user
     @param name: the user for which to retrieve the streak
     or 0 if no such user exists
     @return the current active streak for the given user or 0 if
     no such user exists
     **/
    public int get(String name);

    /**
     Convert the Leaderboard into an aesthetically pleasing (as per your own taste) String containing the Top 3 users with their active streaks
     @return a text version of the top 3 streaks
     **/
    public String prettyPrintTop3();
}
