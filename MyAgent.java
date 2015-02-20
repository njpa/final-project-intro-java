/**
 * Plays winning, blocking and advanced strategy moves. 
 * 
 * @author Norman Pino 
 * @version 17/02/2015
 */
public class MyAgent extends Agent
{
    /**
     * Constructs a new agent, giving it the game and telling it 
     * whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     * @param theName The name of the agent.
     */
    public MyAgent(Connect4Game game, boolean iAmRed, String theName)
    {
        super(game, iAmRed, theName);
    }

    /**
     * Makes a move after looking for potential wins, blocks,
     * and complex strategies that could lead to future wins.
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
        // else, if the agent can make a move that could lead
        // to a win within two moves, he makes that move
        else  if (iAmRed && worthMoving("R", 2) != -1)                  
            moveOnColumn(worthMoving("R", 2)); 
        else if (!iAmRed && worthMoving("Y", 2) != -1)                  
            moveOnColumn(worthMoving("Y", 2));             
        // else, if the agent can stop the opponent from making
        // a move that would secure him a win within two moves,
        // he takes that move
        else  if (iAmRed && worthMoving("Y", 2) != -1) 
            moveOnColumn(worthMoving("Y", 2));         
        else if (!iAmRed && worthMoving("R", 2) != -1)                  
            moveOnColumn(worthMoving("R", 2));   
        // else, if the agent can make a move that could lead
        // to a win within three moves, he makes that move
        else  if (iAmRed && worthMoving("R", 3) != -1)                  
            moveOnColumn(worthMoving("R", 3)); 
        else if (!iAmRed && worthMoving("Y", 3) != -1)                  
            moveOnColumn(worthMoving("Y", 3));     
        // else, if the agent can stop the opponent from making
        // a move that would secure him a win within three moves,
        // he takes that move
        else  if (iAmRed && worthMoving("Y", 3) != -1)                                              
            moveOnColumn(worthMoving("Y", 3));         
        else if (!iAmRed && worthMoving("R", 3) != -1)                  
            moveOnColumn(worthMoving("R", 3)); 
        // else, if the agent can perform additional strategic moves,
        // he makes those moves
        else if (canMoveOnMiddle() != -1)          
            moveOnColumn(canMoveOnMiddle());        
        else if (canMoveOnEmpty() != -1)        
            moveOnColumn(canMoveOnEmpty()); 
        // else, the agent makes a random move
        else        
            moveOnColumn(randomMove());   
    }
}