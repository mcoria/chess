package positioncaptures;

import java.util.Iterator;

import chess.Color;
import chess.Pieza;
import chess.PosicionPieza;
import chess.Square;
import iterators.Cardinal;
import iterators.CardinalSquareIterator;
import iterators.SaltoSquareIterator;
import layers.DummyBoard;
import movegenerators.CaballoMoveGenerator;

public class ImprovedCapturer implements Capturer {
	
	private ImprovedCapturerColor capturerBlanco = null;
	private ImprovedCapturerColor capturerNegro = null;
	
	public ImprovedCapturer(DummyBoard dummyBoard) {
		this.capturerBlanco = new ImprovedCapturerColor(Color.BLANCO, dummyBoard);
		this.capturerNegro = new ImprovedCapturerColor(Color.NEGRO, dummyBoard);
	}	

	@Override
	public boolean positionCaptured(Color color, Square square) {
		if(Color.BLANCO.equals(color)){
			return capturerBlanco.positionCaptured(square);
		} else {
			return capturerNegro.positionCaptured(square);
		}
	}
	
	public boolean positionCapturedScan(Color color, Square square) {
		if(Color.BLANCO.equals(color)){
			return capturerBlanco.positionCapturedScan(square);
		} else {
			return capturerNegro.positionCapturedScan(square);
		}
	}

	
	private static class ImprovedCapturerColor {
		
		private DummyBoard dummyBoard = null; 
		
		private Pieza torre;
		private Pieza alfil;
		private Pieza reyna;
		private Pieza caballo;
		final int[][] saltosPeon;
		final Pieza peon;	
		
		private final int[][] casillerosPeonBlanco = {
			{ -1, -1 }, 
			{ 1, -1 }
		};
		
		private final int[][] casillerosPeonNegro = {
			{ -1, 1 }, 
			{ 1, 1 }
		};
		
		public ImprovedCapturerColor(Color color, DummyBoard dummyBoard) {
			this.dummyBoard = dummyBoard;
			if (Color.BLANCO.equals(color)) {
				torre = Pieza.TORRE_BLANCO;
				alfil = Pieza.ALFIL_BLANCO;
				reyna = Pieza.REINA_BLANCO;
				caballo = Pieza.CABALLO_BLANCO;
				saltosPeon = casillerosPeonBlanco;
				peon = Pieza.PEON_BLANCO;			
			} else {
				torre = Pieza.TORRE_NEGRO;
				alfil = Pieza.ALFIL_NEGRO;
				reyna = Pieza.REINA_NEGRO;;
				caballo = Pieza.CABALLO_NEGRO;
				saltosPeon = casillerosPeonNegro;
				peon = Pieza.PEON_NEGRO;			
			}		
		}

		public boolean positionCaptured(Square square) {
			if(positionCapturedByCaballo(square)	||
			   positionCapturedByTorre(square)	||
			   positionCapturedByAlfil(square)   ||
			   positionCapturedByPeon(square)) {
				return true;
			}
			return false;
		}
		
		public boolean positionCapturedScan(Square square) {
			boolean result = false;
			result = positionCapturedByCaballo(square);
			result = result || positionCapturedByTorre(square);
			result = result || positionCapturedByAlfil(square);
			result = result || positionCapturedByPeon(square); 	
			return result;
		}	
		
		private Cardinal[]  direccionesAlfil = new Cardinal[] {Cardinal.NorteEste, Cardinal.SurEste, Cardinal.SurOeste, Cardinal.NorteOeste};
		private boolean positionCapturedByAlfil(Square square) {
			return positionCapturedByDireccion(square, direccionesAlfil,  alfil);
		}

		private Cardinal[]  direccionesTorre = new Cardinal[] {Cardinal.Este, Cardinal.Oeste, Cardinal.Norte, Cardinal.Sur};
		private boolean positionCapturedByTorre(Square square) {		
			return positionCapturedByDireccion(square, direccionesTorre, torre);
		}

		private boolean positionCapturedByDireccion(Square square, Cardinal[] direcciones, Pieza torreOalfil) {		
			for (Cardinal cardinal : direcciones) {
				if(cardinalPositionCapturedByPieza(torreOalfil, reyna, square, cardinal)){
					return true;
				}
			}
			return false;
		}
		
		private boolean cardinalPositionCapturedByPieza(Pieza torreOalfil, Pieza reyna, Square square, Cardinal cardinal) {
			Iterator<PosicionPieza> iterator = this.dummyBoard.iterator(new CardinalSquareIterator(cardinal, square));
			while (iterator.hasNext()) {
				PosicionPieza destino = iterator.next();
				Pieza pieza = destino.getValue();
				if (pieza == null) {
					continue;
				} else if (reyna.equals(pieza)) {
					return true;
				} else if (torreOalfil.equals(pieza)) {			
					return true;
				} else {
					break;
				}
			}
			return false;
		}

		private boolean positionCapturedByCaballo(Square square) {
			Iterator<PosicionPieza> iterator = dummyBoard.iterator(new SaltoSquareIterator(square, CaballoMoveGenerator.SALTOS_CABALLO));
			while (iterator.hasNext()) {
			    PosicionPieza destino = iterator.next();
			    if(caballo.equals(destino.getValue())){		    	
			    	return true;
			    }
			}
			return false;
		}


		private boolean positionCapturedByPeon(Square square) {
			Iterator<PosicionPieza> iterator = dummyBoard.iterator(new SaltoSquareIterator(square, saltosPeon));
			while (iterator.hasNext()) {
			    PosicionPieza destino = iterator.next();
			    if(peon.equals(destino.getValue())){		    	
			    	return true;
			    }
			}
			return false;
		}

	}	

}
