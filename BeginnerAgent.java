/**
 * Plays winning and blocking moves.
 * 
 * @author Norman Pino 
 * @version 17/02/2015
 */
public class BeginnerAgent extends Agent
{
    /**
     * Constructs a new agent, giving it the game and telling it 
     * whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     * @param theName The name of the agent.
     */
    public BeginnerAgent(Connect4Game game, boolean iAmRed, String theName)
    {
        super(game, iAmRed, theName);
    }

    /**
     * Makes a move after looking for potential wins and blocks.
     */
    public void move()
    {     
        // if the agent can make a move that requires 
        // no other move to win, he takes it
        if (iAmRed && worthMoving("R", 1) != -1)                  
            moveOnColumn(worthMoving("R", 1)); 
        else if (!iAmRed && worthMoving("Y", 1) != -1)                  
            moveOnColumn(worthMoving("Y", 1)); 
        // else, if the agent can take a move that blocks
        // the opponent's win, he takes it
        else if (iAmRed && worthMoving("Y", 1) != -1)                  
            moveOnColumn(worthMoving("Y", 1)); 
        else if (!iAmRed && worthMoving("R", 1) != -1)                  
            moveOnColumn(worthMoving("R", 1));
        // else, the agent makes a random move
        else        
            moveOnColumn(randomMove());             
    }
}