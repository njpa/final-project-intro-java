/**
 * The main driver of the program. This file will create the game, create the two agents,
 * and create the window for the game. After that, Connect4Frame runs everything.
 */
public class Main
{
    public static void main(String[] args)
    {
        Connect4Game game = new Connect4Game(7, 6); 
   
        //Agent redPlayer = new MyAgent(game, true, "Norman Natural");       
        //Agent yellowPlayer = new MyAgent(game, false, "Norman Natural");       
        
        Agent redPlayer = new IntermediateAgent(game, true, "Irvine Intermediate"); 
        //Agent yellowPlayer = new IntermediateAgent(game, false, "Irvine Intermediate"); 
        
        //Agent redPlayer = new BeginnerAgent(game, true, "Benjamin Beginner"); 
        //Agent yellowPlayer = new BeginnerAgent(game, false, "Benjamin Beginner"); 
        
        //Agent redPlayer = new RandomAgent(game, true, "Ronaldo Random");
        Agent yellowPlayer = new RandomAgent(game, false, "Ronaldo Random");
        
        Connect4Frame mainframe = new Connect4Frame(game, redPlayer, yellowPlayer);  
    }
}