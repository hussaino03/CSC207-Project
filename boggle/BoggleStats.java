package boggle;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class contains statistics related to BoggleGame
 */

public class BoggleStats implements Serializable {
    /**
     * instance attribute of BoggleStats as per the Singleton Design Pattern
     */
    private static BoggleStats instance = null;
    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();
    /**
     * the player's score for the current round
     */  
    private int pScore;
    /**
     * the computer's score for the current round
     */  
    private int cScore;
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal;
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal;
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round;
    /**
     * the player's total score across all games
     */
    private double pScoreAllTime;
    /**
     * the average number of words, per round, found by the computer across all games
     */
    private double pAverageWordsAllTime;
    /**
     * the number of rounds across all games
     */
    private double totalRounds;
    /**
     * enumerable types of players (human or computer)
     */  
    public enum Player {
        /**
         * Represents a Human Player
         */
        Human("Human"),
        /**
         * Represents the Computer
         */
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /** BoggleStats constructor NEEDS TO BE UPDATED
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    private BoggleStats() {
        round = 0;
        pScoreTotal = 0;
        pScoreAllTime = 0;
        cScoreTotal = 0;
        pAverageWords = 0;
        pAverageWordsAllTime = 0;
        cAverageWords = 0;
        playerWords = new HashSet<String>();
        computerWords = new HashSet<String>();

        // can make the below a separate method. Tries to read from previously saved stats
        // If there is no such file, it starts from default values.
        try {
            FileInputStream file = new FileInputStream("boggle/SavedStats.ser");
            ObjectInputStream in = new ObjectInputStream(file);
            BoggleStats savedStats = (BoggleStats) in.readObject();
            pScoreAllTime = savedStats.pScoreAllTime;
            pAverageWordsAllTime = savedStats.pAverageWordsAllTime;
            totalRounds = savedStats.totalRounds;
        }
        catch (IOException e) {
            pScoreAllTime = 0;
            pAverageWordsAllTime = 0;
            totalRounds = 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            round = 0;
            pScoreTotal = 0;
            cScoreTotal = 0;
            pAverageWords = 0;
            cAverageWords = 0;
            playerWords = new HashSet<String>();
            computerWords = new HashSet<String>();
        }
    }

    /**
    * Singleton Design Implementation:
    * A public method to return the instance of BoggleStats
    * @return BoggleStats instance
     */
    public static synchronized  BoggleStats getInstance(){
        if (instance == null){
            instance = new BoggleStats();
        }
        return instance;
    }

    /**
     * Add a word to a given player's word list for the current round.
     * The player's score is incremented, as words are added.
     * @param word The word to be added to the list
     * @param player The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        word = word.toLowerCase();
        if (player == Player.Human){
            playerWords.add(word);
            if (word.length() >= 4){
                String spliced = word.substring(4);
                pScore += spliced.length() + 1;
            }
        } else if (player == Player.Computer){
            computerWords.add(word.toLowerCase());
            if (word.length() >= 4){
                String spliced = word.substring(4);
                cScore += spliced.length() + 1;
            }
        }
    }

    /**
     * End a given round. This function will update each player's total scores, average scores,
     * and increment the current round number by 1.
     */
    public void endRound() {

        pScoreTotal += pScore;
        cScoreTotal += cScore;

        pScoreAllTime += pScore;

        totalRounds += 1;

        round += 1;


        pAverageWords = (pAverageWords * (round - 1)) / round + playerWords.size() / (double) round;

        pAverageWordsAllTime = (pAverageWordsAllTime * (totalRounds - 1))
                / totalRounds + playerWords.size() / (double) totalRounds;

        cAverageWords = (cAverageWords * (round - 1)) / round + computerWords.size() / (double) round;

    }

    /**
     * Generates a hashmap containing BoggleStats attributes
     * @return HashMap a HashMap of BoggleStats attributes
     */
    public HashMap<String, Object> getStatsMap() {
        HashMap<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("Player Words", playerWords);
        scoreMap.put("Computer Words", computerWords);
        scoreMap.put("Player Score", pScore);
        scoreMap.put("Computer Score", cScore);
        scoreMap.put("Player Average Words", pAverageWords);
        scoreMap.put("Computer Average Words", cAverageWords);
        scoreMap.put("Player Score Total", pScoreTotal);
        scoreMap.put("Computer Score Total", cScoreTotal);
        scoreMap.put("Round", round);
        return scoreMap;
    }

    /**
     * Getter for playerWords
     * @return playerWords
     */
    public String playerwords(){
        return "Human Words: "+playerWords;
    }
    /**
     * Getter for computerWords
     * @return computerWords
     */
    public String computerwords(){
        return "Computer Words: "+computerWords;
    }
    /**
     * Getter for playerWords.size
     * @return playerWords.size
     */
    public String playerwordsSize(){
        return "Number of Human Average Words: " + playerWords.size();
    }
    /**
     * Getter for computerWords.size
     * @return computerWords.size
     */
    public String computerwordsSize(){
        return "Number of Computer Average Words: "+computerWords.size();
    }
    /**
     * Getter for pScore
     * @return pScore
     */
    public int getPScore(){
        return pScore;
    }

    /**
     * Getter for cScore
     * @return cScore
     */
    public int getCScore(){
        return cScore;
    }
    /**
     * Setter for pScore
     * @param score the score to set pScore to
     */
    public void setPScore(int score){
        this.pScore = score;
    }

    /**
     * Setter for round
     * @param num the number to set this.round to
     */
    public void setRound(int num){this.round = num;}

    /**
     * Clears this.playerWords and this.computerWords
     */
    public void clearWords() {
        this.playerWords.clear();
        this.computerWords.clear();
    }
    /**
     * Stores the stats of a given game in the boggle/SavedStats.ser file
     */
    public void storeStats() {

        try {
            FileOutputStream file = new FileOutputStream("boggle/SavedStats.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter for player average words
     * @return pAverageWords
     */
    public double getPAverageWords(){
        return this.pAverageWords;
    }
    /**
     * Getter for computer average words
     * @return cAverageWords
     */
    public double getCAverageWords(){
        return this.cAverageWords;
    }

    /**
     * Getter for player words
     * @return Set The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }
    /**
     * Getter for this.computerWords
     * @return Set The player's word list
     */
    public Set<String> getComputerWords() {
        return this.computerWords;
    }

    /**
     * Getter for this.round
     * @return int The number of rounds played this game
     */
    public int getRound() { return this.round; }

    /**
     * Getter for this.totalRounds
     * @return int The number of rounds played across all games
     */
    public int getTotalRounds() { return (int) this.totalRounds; }

    /**
     * Setter for this.totalRounds
     * @param totalRounds the number of totalRounds to be set
     */
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    /**
     * Getter for this.pScoreTotal
     * @return int this.pScoreTotal
     */
    public int getPScoreTotal(){return this.pScoreTotal;}
    /**
     * Getter for this.cScoreTotal
     * @return int this.cScoreTotal
     */
    public int getCScoreTotal(){return this.cScoreTotal;}
    /**
     * Getter for this.pAverageWordsAllTime
     * @return int this.pAverageWordsAllTime
     */
    public double getPAvgWordsAllTime(){return this.pAverageWordsAllTime;}
    /**
     * Setter for this.pAverageWordsAllTime
     * @param avgWords the average number of words to set pAverageWordsAllTime to
     */
    public void setPAvgWordsAllTime(double avgWords){this.pAverageWordsAllTime = avgWords;}
    /**
     * Getter for this.pScoreAllTime
     * @return double this.PScoreAllTime
     */
    public double getPScoreAllTime(){return this.pScoreAllTime;}
    /**
     * Setter for this.cScore
     * @param score the score which this.cScore should be set to
     */
    public void setCScore(int score){this.cScore = score;}
    /**
     * Setter for this.pScoreAllTime
     * @param score the score which this.pScoreAllTime should be set to
     */
    public void setPScoreAllTime(int score){this.pScoreAllTime = score;}
}
