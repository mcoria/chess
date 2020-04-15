package movegenerators;

import java.util.Collection;

import chess.DummyBoard;
import chess.Move;
import chess.PosicionPieza;
import chess.Square;

public interface MoveGenerator {

	public void generateMoves(PosicionPieza origen, Collection<Move> moveContainer);

	public boolean puedeCapturarRey(PosicionPieza origen, Square kingSquare);
	
	public void setTablero(DummyBoard tablero);

	public void setFilter(MoveFilter filter);

}
