package iterators;

import java.util.Iterator;

import chess.PosicionPieza;

//Esta implementacion es muy especifica a como se representa un tablero y como se representa un listado de posiciones
public class BoardBitSquareIterator implements Iterator<PosicionPieza> {

	private final long posiciones;
	private final PosicionPieza[] tablero;

	private int idx = -1;

	// Observar este constructor
	public BoardBitSquareIterator(PosicionPieza[] tablero, long posiciones) {
		this.posiciones = posiciones;
		this.tablero = tablero;
		calcularNextPoint();
	}

	@Override
	public boolean hasNext() {
		return this.idx < 64;
	}

	@Override
	public PosicionPieza next() {
		PosicionPieza currentPoint = tablero[this.idx];
		calcularNextPoint();
		return currentPoint;
	}

	// ATENCION !!! Llevar esta implementacion a otros iteradores
	private void calcularNextPoint() {
		do {
			this.idx++;
		} while (this.idx < 64 && (posiciones & (1L << this.idx)) == 0);
	}

}
