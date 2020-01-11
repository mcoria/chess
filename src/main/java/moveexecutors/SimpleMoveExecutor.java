package moveexecutors;

import chess.BoardMediator;
import chess.Move;

public class SimpleMoveExecutor implements MoveExecutor {

	@Override
	public void execute(BoardMediator board, Move move) {
		board.setPieza(move.getTo(), board.getPieza(move.getFrom()));
		board.setEmptySquare(move.getFrom());
	}

	@Override
	public void undo(BoardMediator board, Move move) {
		board.setPieza(move.getFrom(), board.getPieza(move.getTo()));
		board.setEmptySquare(move.getTo());
	}

}
