package moveexecutors;

import chess.BoardState;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;
import chess.Square;

public class EnroqueNegroReyMoveExecutor implements MoveExecutor{

	@Override
	public void execute(DummyBoard board, BoardState boardState, Move move) {
		board.setEmptySquare(Square.e8);
		board.setEmptySquare(Square.h8);
		board.setPieza(Square.g8, Pieza.REY_NEGRO);
		board.setPieza(Square.f8, Pieza.TORRE_NEGRO);
		
		boardState.setEnroqueNegroReyPermitido(false);
		boardState.setEnroqueNegroReinaPermitido(false);
	}

	@Override
	public void undo(DummyBoard board, BoardState boardState, Move move) {
		board.setEmptySquare(Square.g8);
		board.setEmptySquare(Square.f8);
		board.setPieza(Square.e8, Pieza.REY_NEGRO);
		board.setPieza(Square.h8, Pieza.TORRE_NEGRO);
	}

}