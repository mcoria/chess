package moveexecutors;

import java.util.Objects;

import chess.BoardState;
import chess.DummyBoard;
import chess.Move;
import chess.Square;

public class CapturePeonPasanteExecutor implements MoveExecutor {

	private Square casilleroPeonPasante;

	public CapturePeonPasanteExecutor(Square casilleroPeonPasante) {
		this.casilleroPeonPasante = casilleroPeonPasante;
	}

	@Override
	public void execute(DummyBoard board, Move move, BoardState boardState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void undo(DummyBoard board, Move move, BoardState boardState) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CapturePeonPasanteExecutor){
			CapturePeonPasanteExecutor theOther = (CapturePeonPasanteExecutor) obj;
			return Objects.equals(casilleroPeonPasante, theOther.casilleroPeonPasante);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return casilleroPeonPasante.hashCode();
	}
	
	@Override
	public String toString() {
		return "Peon Pasante: " + casilleroPeonPasante.toString();
	}	

}
