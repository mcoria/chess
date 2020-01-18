package moveexecutors;

import chess.BoardState;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;
import chess.Square;

public class EnroqueNegroReynaMoveExecutor implements MoveExecutor{

	@Override
	public void execute(DummyBoard board, BoardState boardState, Move move) {
		board.setEmptySquare(Square.e8);
		board.setEmptySquare(Square.a8);
		board.setPieza(Square.c8, Pieza.REY_NEGRO);
		board.setPieza(Square.d8, Pieza.TORRE_NEGRO);
		
		boardState.setEnroqueNegroReyPermitido(false);
		boardState.setEnroqueNegroReinaPermitido(false);
	}

	@Override
	public void undo(DummyBoard board, BoardState boardState, Move move) {
		board.setEmptySquare(Square.c8);
		board.setEmptySquare(Square.d8);
		board.setPieza(Square.e8, Pieza.REY_NEGRO);
		board.setPieza(Square.a8, Pieza.TORRE_NEGRO);
	}

}