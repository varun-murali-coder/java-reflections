package game.internal;

class BoardPrinter {
	
private final BoardDimensions dimensions;

public BoardPrinter(BoardDimensions dimensions) {
	this.dimensions=dimensions;
}
public void print(Board board) {
	printHorizontalBorder();
	printBoard(board);
	printHorizontalBorder();

}
public void printBoard(Board board) {
	for(int r=0;r<dimensions.getNumberOfRows();r++) {
		System.out.print("|");
		for(int c=0;c<dimensions.getNumberOfColums();c++) {
          System.out.print(String.format(" %s|", board.getPrintableCellSign(r,c)));
	}
		System.out.println();
	}
	
}
public void printHorizontalBorder() {
	for(int c=0;c<dimensions.getNumberOfColums()*4+1;c++) {
		System.out.print("-");
	}
	System.out.println();
	
}

}
