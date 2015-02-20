/**
 * Plays random moves.
 * 
 * @author Norman Pino
 * @version 17/02/2014
 */
public class RandomAgent extends Agent
{
    /**
     * Constructs a new agent, giving it the game, a name, and telling it 
     * whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     * @param theName The name of the agent.
     */
    public RandomAgent(Connect4Game game, boolean iAmRed, String theName)
    {
        super(game, iAmRed, theName);
    }

    /**
     * Makes a random move.
     */
    public void move()
    {
        moveOnColumn(randomMove());   
    }
}