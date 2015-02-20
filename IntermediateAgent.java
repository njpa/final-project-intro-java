/**
 * Plays winning, blocking and intermediate strategy moves. 
 * 
 * @author Norman Pino 
 * @version 17/02/2015
 */
public class IntermediateAgent extends Agent
{
    /**
     * Constructs a new agent, giving it the game and telling it 
     * whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     * @param theName The name of the agent.
     */
    public IntermediateAgent(Connect4Game game, boolean iAmRed, String theName)
    {
        super(game, iAmRed, theName);
    }

    /**
     * Makes a move after looking for potential wins, blocks,
     * and basic strategies that can lead to future wins.
     */
    public void move()
    {
        // if the agent can make a move that requires 
        // no other move to win (a win), he takes it
        if (iAmRed && worthMoving("R", 1) != -1)                  
            moveOnColumn(worthMoving("R", 1)); 
        else if (!iAmRed && worthMoving("Y", 1) != -1)                  
            moveOnColumn(worthMoving("Y", 1));
        // else, if the agent can make a move that blocks
        // the opponent's win, he takes it
        else  if (iAmRed && worthMoving("Y", 1) != -1)                  
            moveOnColumn(worthMoving("Y", 1)); 
        else if (!iAmRed && worthMoving("R", 1) != -1)                  
            moveOnColumn(worthMoving("R", 1));                  
        // else, if the agent can make a move that can lead
        // to a win within two moves, he makes that move
        else  if (iAmRed && worthMoving("R", 2) != -1)                  
            moveOnColumn(worthMoving("R", 2)); 
        else if (!iAmRed && worthMoving("Y", 2) != -1)                  
            moveOnColumn(worthMoving("Y", 2));             
        // else, the agent makes a random move
        else        
            moveOnColumn(randomMove());   
    }
}