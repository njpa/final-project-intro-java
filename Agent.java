import java.util.Random;

/**
 * The abstract class for an Agent that plays Connect 4.
 */
public abstract class Agent
{
    protected Connect4Game myGame;
    protected boolean iAmRed;
    protected String name;
    /**
     * Random number generator used to pick a random column.
     */
    Random r;
    final String VERTICAL_A = "vertical-a";
    final String VERTICAL_B = "vertical-b";
    final String VERTICAL_C = "vertical-c";
    final String VERTICAL_D = "vertical-d";
    final String HORIZONTAL_A = "horizontal-a";
    final String HORIZONTAL_B = "horizontal-b";
    final String HORIZONTAL_C = "horizontal-c";
    final String HORIZONTAL_D = "horizontal-d";
    final String DIAGONAL_DOWN_A = "diagonalDown-a";
    final String DIAGONAL_DOWN_B = "diagonalDown-b";
    final String DIAGONAL_DOWN_C = "diagonalDown-c";
    final String DIAGONAL_DOWN_D = "diagonalDown-d";    
    final String DIAGONAL_UP_A = "diagonalUp-a";
    final String DIAGONAL_UP_B = "diagonalUp-b";
    final String DIAGONAL_UP_C = "diagonalUp-c";
    final String DIAGONAL_UP_D = "diagonalUp-d";        
    
    /**
     * Constructs a new agent.
     * 
     * @param game The game for the agent to play.
     * @param iAmRed Whether the agent is the red player.
     * @param theName The name of the player.
     */
    public Agent(Connect4Game game, boolean iAmRed, String theName)
    {
        this.myGame = game;
        this.iAmRed = iAmRed;
        this.name = theName;
        this.r = new Random();
    }

    /**
     * Returns the name of this agent.
     *
     * @return The agent's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * The way the agent's name is displayed in the game, with its color.     
     *
     * @return the agent's name to display in the game.
     */
    public String toString()
    {
        if (iAmRed)
        {
            return getName() + " (Red)";
        }
        else
        {
            return getName() + " (Yellow)";
        }
    }

    /**
     * Make a move in the game. At the beginning of this method, the myGame object will be
     * ready for a move. After this method has run, exactly one piece should be added to the
     * game board with no other changes made.    
     */
    public abstract void move();

    /**
     * Returns the index of the top empty slot in a particular column.
     * 
     * @param column The column to check.
     * @return The index of the top empty slot in a particular column; -1 if the column is already full.
     */
    private int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())            
                lowestEmptySlot = i;            
        }
        return lowestEmptySlot;
    }

    /**
     * Returns a random valid column index to move on.
     * 
     * @return The index number of a valid random column.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount());
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     * 
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
        // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    } 

    /**
     * Returns the index number of a column with no tokens in it.
     * 
     * @return The index number of the first available empty column from
     * left to right; -1 if none are empty.
     */
    public int canMoveOnEmpty()
    {
        int colIndex = 0;
        int bottomRow = myGame.getRowCount() - 1;
        boolean columnFound = false;        
        while (colIndex < myGame.getColumnCount() && !columnFound)
        {     
            Connect4Column connect4column = myGame.getColumn(colIndex);
            if (getLowestEmptyIndex(connect4column) != -1)
            { 
                if (!connect4column.getSlot(bottomRow).getIsFilled())     
                    columnFound = true; 
                else
                    colIndex ++;
            }
            else 
                colIndex ++;            
        }
        if (columnFound)                     
            return colIndex;        
        else
            return -1; 
    }

    /**
     * Returns the index number of the middle column of the board if it is empty. 
     * 
     * @return The index number of the middle column; -1 if it is not empty.
     */
    public int canMoveOnMiddle()
    {           
        int middleIndex = -1;
        int middle = (int)(myGame.getColumnCount() / 2);
        int bottom = myGame.getRowCount() - 1;
        if (!myGame.getColumn(middle).getSlot(bottom).getIsFilled())
            middleIndex = middle;
        return middleIndex;
    }

    /**
     * Determines whether it is wise to make a move on a column after taking into 
     * consideration the number of moves needed to complete a win after making the move.
     * 
     * @param color The color representing the player for which the function is consulted. 
     * @param threshold The threshold used to determine the ... of the move (if 1 is supplied,
     * the move must guarantee a win; if 2 is supplied, the move should promise a win after an
     * additional move; if 3 is supplied, the move should promise a win two more moves. 
     * @return The index number of the column matching the criteria;
     * -1 if none is found.
     */
    public int worthMoving(String color, int threshold) 
    {
        int columnIndex = 0;
        boolean columnFound = false;        
        while (columnIndex < myGame.getColumnCount() && !columnFound)
        {    
            Connect4Column column = myGame.getColumn(columnIndex);
            int rowIndex = getLowestEmptyIndex(column);
            if (rowIndex != -1)
            { 
                // get a string containing the different possible combinations
                // and directions of possible wins based on the slot's position 
                // with respect to the grid
                String combosToCheck = getCombosToCheck(columnIndex, rowIndex);

                // check each possible combination to see if it meets the supplied threshold
                if(combosToCheck.contains(VERTICAL_A)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, VERTICAL_A), color) == threshold)                
                    columnFound = true;                  
                else if(combosToCheck.contains(VERTICAL_B)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, VERTICAL_B), color) == threshold)                
                    columnFound = true;  
                else if(combosToCheck.contains(VERTICAL_C)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, VERTICAL_C), color) == threshold)                
                    columnFound = true;                  
                else if(combosToCheck.contains(VERTICAL_D)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, VERTICAL_D), color) == threshold)
                    columnFound = true;  
                else if(combosToCheck.contains(HORIZONTAL_A)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, HORIZONTAL_A), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(HORIZONTAL_B)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, HORIZONTAL_B), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(HORIZONTAL_C)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, HORIZONTAL_C), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(HORIZONTAL_D)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, HORIZONTAL_D), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_DOWN_A)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_DOWN_A), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_DOWN_B)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_DOWN_B), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_DOWN_C)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_DOWN_C), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_DOWN_D)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_DOWN_D), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_UP_A)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_UP_A), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_UP_B)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_UP_B), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_UP_C)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_UP_C), color) == threshold)
                    columnFound = true;
                else if(combosToCheck.contains(DIAGONAL_UP_D)
                && leftToConnect(getSlotsToCheck(columnIndex, rowIndex, DIAGONAL_UP_D), color) == threshold)
                    columnFound = true;                  
                else 
                    columnIndex ++;
            }
            else             
                columnIndex ++;              
        }
        if (columnFound)                     
            return columnIndex;        
        else
            return -1;    
    }

    /**
     * Returns all of the possible combinations that can 
     * lead to a win based on a slot's position within the grid 
     * (the combination can be vertical, horizontal or diagonal
     * and can have a slot missing in position A □●●●, position B ●□●●, 
     * position C ●●□● or position D ●●●□).
     * 
     * @param columnIndex The index number of the column.
     * @return The string concatenated from substrings representing
     * each kind of possible combination for the specified column.
     */
    private String getCombosToCheck(int col, int row)
    {
        String toCheckFor = "";
        if (row <= myGame.getRowCount() - 4)       
            toCheckFor += VERTICAL_A;                
        if (row >= 1 && row <= myGame.getRowCount() - 3)        
            toCheckFor += VERTICAL_B;                
        if (row >= 2 && row <= myGame.getRowCount() - 2)        
            toCheckFor += VERTICAL_C;                
        if (row >= 3)        
            toCheckFor += VERTICAL_D;                
        if (col <= myGame.getColumnCount() - 4)
        {
            toCheckFor += HORIZONTAL_A;  
            if (row <= myGame.getRowCount() - 4)
                toCheckFor += DIAGONAL_DOWN_A;
            if (row >= 3)
                toCheckFor += DIAGONAL_UP_A;
        }        
        if (col >= 1 && col <= myGame.getColumnCount() - 3)
        {
            toCheckFor += HORIZONTAL_B;
            if (row >= 1 && row <= myGame.getRowCount() -3)
                toCheckFor += DIAGONAL_DOWN_B;
            if (row >= 2 && row <= myGame.getRowCount() -2)
                toCheckFor += DIAGONAL_UP_B;
        }
        if (col >= 2  && col <= myGame.getColumnCount() - 2)
        {
            toCheckFor += HORIZONTAL_C;       
            if (row >= 2 && row <= myGame.getRowCount() - 2)
                toCheckFor += DIAGONAL_DOWN_C;  
            if (row >= 1 && row <= myGame.getRowCount() - 3)
                toCheckFor += DIAGONAL_UP_C;             
        }
        if (col >= 3)
        {
            toCheckFor += HORIZONTAL_D; 
            if (row >= 3)
                toCheckFor += DIAGONAL_DOWN_D;
            if (row <= myGame.getRowCount() - 4)
                toCheckFor += DIAGONAL_UP_D;
        }       
        return toCheckFor;    
    }

    /**
     * Returns the three slots that should be checked when 
     * to determine if a connection can be made from 4 tokens
     * of the same color taking into consideration whether 
     * the line is vertical, horizontal or diagonal.
     *
     * @param col The column number of the slot.
     * @param row The row number of the slot.
     * @param combination The type of combination to check with.  
     * @return The array of three slots relevant to the formation
     * of the specified type of line using the provided slot.
     */
    private Connect4Slot[] getSlotsToCheck(int col, int row, String combination)
    {        
        Connect4Slot[] slots = new Connect4Slot[3];
        if (combination.equals(VERTICAL_A))
        {
            slots[0] = myGame.getColumn(col).getSlot(row + 1);
            slots[1] = myGame.getColumn(col).getSlot(row + 2);
            slots[2] = myGame.getColumn(col).getSlot(row + 3);
        }
        else if (combination.equals(VERTICAL_B))
        {
            slots[0] = myGame.getColumn(col).getSlot(row - 1);
            slots[1] = myGame.getColumn(col).getSlot(row + 1);
            slots[2] = myGame.getColumn(col).getSlot(row + 2);
        }
        else if (combination.equals(VERTICAL_C))
        {
            slots[0] = myGame.getColumn(col).getSlot(row - 2);
            slots[1] = myGame.getColumn(col).getSlot(row - 1);
            slots[2] = myGame.getColumn(col).getSlot(row + 1);
        }
        else if (combination.equals(VERTICAL_D))
        {
            slots[0] = myGame.getColumn(col).getSlot(row - 3);
            slots[1] = myGame.getColumn(col).getSlot(row - 2);
            slots[2] = myGame.getColumn(col).getSlot(row - 1);
        }
        else if (combination.equals(HORIZONTAL_A))
        {
            slots[0] = myGame.getColumn(col + 1).getSlot(row);
            slots[1] = myGame.getColumn(col + 2).getSlot(row);
            slots[2] = myGame.getColumn(col + 3).getSlot(row);
        }
        else if (combination.equals(HORIZONTAL_B))
        {
            slots[0] = myGame.getColumn(col - 1).getSlot(row);
            slots[1] = myGame.getColumn(col + 1).getSlot(row);
            slots[2] = myGame.getColumn(col + 2).getSlot(row);
        }
        else if (combination.equals(HORIZONTAL_C))
        {
            slots[0] = myGame.getColumn(col - 2).getSlot(row);
            slots[1] = myGame.getColumn(col - 1).getSlot(row);
            slots[2] = myGame.getColumn(col + 1).getSlot(row);
        }
        else if (combination.equals(HORIZONTAL_D))  
        {
            slots[0] = myGame.getColumn(col - 3).getSlot(row);
            slots[1] = myGame.getColumn(col - 2).getSlot(row);
            slots[2] = myGame.getColumn(col - 1).getSlot(row);
        } 
        else if (combination.equals(DIAGONAL_DOWN_A))
        {
            slots[0] = myGame.getColumn(col + 1).getSlot(row + 1);
            slots[1] = myGame.getColumn(col + 2).getSlot(row + 2);
            slots[2] = myGame.getColumn(col + 3).getSlot(row + 3);            
        }
        else if (combination.equals(DIAGONAL_DOWN_B))
        {
            slots[0] = myGame.getColumn(col - 1).getSlot(row - 1);
            slots[1] = myGame.getColumn(col + 1).getSlot(row + 1);
            slots[2] = myGame.getColumn(col + 2).getSlot(row + 2);            
        }
        else if (combination.equals(DIAGONAL_DOWN_C))
        {
            slots[0] = myGame.getColumn(col - 2).getSlot(row - 2);
            slots[1] = myGame.getColumn(col - 1).getSlot(row - 1);
            slots[2] = myGame.getColumn(col + 1).getSlot(row + 1);            
        }
        else if (combination.equals(DIAGONAL_DOWN_D))
        {
            slots[0] = myGame.getColumn(col - 3).getSlot(row - 3);
            slots[1] = myGame.getColumn(col - 2).getSlot(row - 2);
            slots[2] = myGame.getColumn(col - 1).getSlot(row - 1);            
        }
        else if (combination.equals(DIAGONAL_UP_A))
        {
            slots[0] = myGame.getColumn(col + 1).getSlot(row - 1);
            slots[1] = myGame.getColumn(col + 2).getSlot(row - 2);
            slots[2] = myGame.getColumn(col + 3).getSlot(row - 3);            
        }
        else if (combination.equals(DIAGONAL_UP_B))
        {
            slots[0] = myGame.getColumn(col - 1).getSlot(row + 1);
            slots[1] = myGame.getColumn(col + 1).getSlot(row - 1);
            slots[2] = myGame.getColumn(col + 2).getSlot(row - 2);            
        }
        else if (combination.equals(DIAGONAL_UP_C))
        {
            slots[0] = myGame.getColumn(col - 2).getSlot(row + 2);
            slots[1] = myGame.getColumn(col - 1).getSlot(row + 1);
            slots[2] = myGame.getColumn(col + 1).getSlot(row - 1);            
        }
        else if (combination.equals(DIAGONAL_UP_D))
        {
            slots[0] = myGame.getColumn(col - 3).getSlot(row + 3);
            slots[1] = myGame.getColumn(col - 2).getSlot(row + 2);
            slots[2] = myGame.getColumn(col - 1).getSlot(row + 1);            
        }        
        return slots;
    }  

    /**
     * Returns the number of slots needed to fill up a line of
     * 4 slots assuming that the slots are all either empty or
     * filled in by tokens of the same color we are checking for.
     * 
     * @param slots The set of three slots to check.
     * @param color The color of tokens that we are looking for.
     * @returns The number of slots left to complete a set of 4.
     */
    private int leftToConnect(Connect4Slot[] slots, String color)
    {
        int countFilled = 0; 
        int countEmpty = 0;
        int leftToConnect = 0;
        for (Connect4Slot slot : slots)
        {           
            if (color.equals("R") && slot.getIsFilled() && slot.getIsRed())                        
                countFilled ++;             
            else if (color.equals("Y") && slot.getIsFilled() && !slot.getIsRed())       
                countFilled ++;
            if (!slot.getIsFilled())
                countEmpty ++;
        }
        if (countFilled == 1 && countEmpty == 2)
            leftToConnect = 3;
        else if (countFilled == 2 && countEmpty == 1)
            leftToConnect = 2;        
        else if (countFilled == 3)
            leftToConnect = 1;
        return leftToConnect;
    }
}