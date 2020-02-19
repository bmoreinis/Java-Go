package GoBoard;
//NOTE TO BRAM: https://codereview.stackexchange.com/questions/93901/go-board-game-in-java

public class Go {
    public static Game newGame;
   
    public static void main(String args[]) {
		Game newGame = new Game();
		GoBoard.initiallize(newGame);
    }
}