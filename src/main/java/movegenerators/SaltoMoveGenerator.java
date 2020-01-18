package movegenerators;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import chess.Color;
import chess.DummyBoard;
import chess.Move;
import chess.Pieza;
import chess.Square;
import chess.Move.MoveType;
import iterators.BoardIterator;
import iterators.SaltoSquareIterator;

public abstract class SaltoMoveGenerator extends AbstractMoveGenerator{
	
	protected Color color;
	private int[][] saltos;
	
	 public SaltoMoveGenerator(Color color, int[][] saltos) {
		this.color = color;
		this.saltos = saltos;
	}

	@Override
	public Set<Move> getPseudoMoves(DummyBoard tablero, Map.Entry<Square, Pieza> origen) {
		Square casillero = origen.getKey();
		BoardIterator iterator = tablero.iterator(new SaltoSquareIterator(casillero, saltos));
		Set<Move> moves = createMoveContainer();
		while (iterator.hasNext()) {
		    Entry<Square, Pieza> destino = iterator.next();
		    Pieza pieza = destino.getValue();
		    if(pieza == null){
		    	Move move = new Move(origen, destino, MoveType.SIMPLE);
		    	moves.add(move);
		    } else if(color.equals(pieza.getColor())){
		    	continue;
		    } else if(color.opositeColor().equals(pieza.getColor())){
		    	Move move = new Move(origen, destino, MoveType.CAPTURA);
		    	moves.add(move);		    	
		    }
		}
		return moves;
	}
		
}