package GoBoard;
//NOTE TO BRAM: https://codereview.stackexchange.com/questions/93901/go-board-game-in-java

public class Go {
    public static Game newGame;
   
    public static void main(String args[]) {
		final Game newGame = new Game();
		Turn testTurn = new Turn(3,3,1);
		newGame.addTurn(testTurn);
		System.out.println(newGame.toString());
		GoBoard.initiallize(newGame);
    }
}