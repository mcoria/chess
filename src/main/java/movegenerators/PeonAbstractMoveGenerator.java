package movegenerators;

import java.util.Collection;

import chess.BoardState;
import chess.Color;
import chess.Move;
import chess.Pieza;
import chess.PosicionPieza;
import chess.Square;
import moveexecutors.CacheMove;
import moveexecutors.CapturaPeonPromocion;
import moveexecutors.CaptureMove;
import moveexecutors.CapturePeonPasante;
import moveexecutors.SaltoDoblePeonMove;
import moveexecutors.SimpleMove;
import moveexecutors.SimplePeonPromocion;

public abstract class PeonAbstractMoveGenerator extends AbstractMoveGenerator {
	
	protected BoardState boardState;

	protected abstract Square getCasilleroSaltoSimple(Square casillero);

	protected abstract Square getCasilleroSaltoDoble(Square casillero);

	protected abstract Square getCasilleroAtaqueIzquirda(Square casillero);
	
	protected abstract Square getCasilleroAtaqueDerecha(Square casillero);
	
	protected abstract PosicionPieza getCapturaPeonPasante(Square peonPasanteSquare);	
	
	protected abstract Pieza[] getPiezaPromocion();
	
	protected CacheMove cacheMove = new CacheMove();
	
	protected SimpleMove simpleMovePrototype = new SimpleMove();
	
	public PeonAbstractMoveGenerator(Color color) {
		super(color);
	}
	
	@Override
	public void generateMoves(PosicionPieza origen, Collection<Move> moveContainer){
		
		Square casillero = origen.getKey();
		Square saltoSimpleCasillero = getCasilleroSaltoSimple(casillero);
		Square saltoDobleCasillero = getCasilleroSaltoDoble(casillero);
		
		Square casilleroAtaqueIzquirda = getCasilleroAtaqueIzquirda(casillero);
		Square casilleroAtaqueDerecha = getCasilleroAtaqueDerecha(casillero);
		
		Square peonPasanteSquare = boardState.getPeonPasanteSquare();
		
			
		PosicionPieza destino = null;
		
		if (saltoSimpleCasillero != null && this.tablero.isEmtpy(saltoSimpleCasillero)) {
			destino = this.tablero.getPosicion(saltoSimpleCasillero);
			if (destino.getValue() == null) {
				//Move moveSaltoSimple = new SimpleMove(origen, destino);
				simpleMovePrototype.setFromTo(origen, destino);
				if(this.filter.filterMove(simpleMovePrototype)){
					int toRank = saltoSimpleCasillero.getRank();
					if(toRank == 0 || toRank == 7){ // Es una promocion
						addSaltoSimplePromocion(origen, destino, moveContainer);
					} else {
						moveContainer.add(simpleMovePrototype.clone());
					}
				}
				if (saltoDobleCasillero != null) {
					destino = this.tablero.getPosicion(saltoDobleCasillero);
					if (destino.getValue() == null) {
				    	//Move moveSaltoDoble = new SaltoDoblePeonMove(origen, destino, saltoSimpleCasillero);
				    	cacheMove.setFromTo(origen, destino);
						if(this.filter.filterMove(cacheMove)){
							moveContainer.add(new SaltoDoblePeonMove(origen, destino, saltoSimpleCasillero));
						}
					}
				}
			}
		}
		
		if (casilleroAtaqueIzquirda != null) {
			destino = this.tablero.getPosicion(casilleroAtaqueIzquirda);
			Pieza pieza = destino.getValue();
			if (pieza != null && color.opositeColor().equals(pieza.getColor())) {
		    	//Move moveCaptura = new CaptureMove(origen, destino);
		    	cacheMove.setFromTo(origen, destino);
				if(this.filter.filterMove(cacheMove)){
					int toRank = saltoSimpleCasillero.getRank();
					if(toRank == 0 || toRank == 7){ // Es una promocion
						addCapturaPromocion(origen, destino, moveContainer);
					} else {
						moveContainer.add(new CaptureMove(origen, destino));
					}					
				}				
			}
		}	
		
		if (casilleroAtaqueDerecha != null) {
			destino = this.tablero.getPosicion(casilleroAtaqueDerecha);
			Pieza pieza = destino.getValue();
			if (pieza != null && color.opositeColor().equals(pieza.getColor())) {				
		    	//Move moveCaptura = new CaptureMove(origen, destino);
		    	cacheMove.setFromTo(origen, destino);
				if(this.filter.filterMove(cacheMove)){
					int toRank = saltoSimpleCasillero.getRank();
					if(toRank == 0 || toRank == 7){ // Es una promocion
						addCapturaPromocion(origen, destino, moveContainer);
					} else {
						moveContainer.add(new CaptureMove(origen, destino));
					}
				}				
			}
		}	
		
		if (peonPasanteSquare != null) {
			if (peonPasanteSquare.equals(casilleroAtaqueIzquirda) || peonPasanteSquare.equals(casilleroAtaqueDerecha)) {
				destino = this.tablero.getPosicion(peonPasanteSquare);
		    	Move move = new CapturePeonPasante(origen, destino, getCapturaPeonPasante(peonPasanteSquare));
				if(this.filter.filterMove(move)){
					moveContainer.add(move);
				}				
			}
		}
	}

	private void addSaltoSimplePromocion(PosicionPieza origen, PosicionPieza destino, Collection<Move> moveContainer) {
		Pieza[] promociones = getPiezaPromocion();
		for (int i = 0; i < promociones.length; i++) {
			moveContainer.add(new SimplePeonPromocion(origen, destino, promociones[i]));
		}
	}
	
	private void addCapturaPromocion(PosicionPieza origen, PosicionPieza destino, Collection<Move> moveContainer) {
		Pieza[] promociones = getPiezaPromocion();
		for (int i = 0; i < promociones.length; i++) {
			moveContainer.add(new CapturaPeonPromocion(origen, destino, promociones[i]));
		}
	}	

	@Override
	public boolean puedeCapturarRey(PosicionPieza origen, Square kingSquare) {
		if(kingSquare.equals(getCasilleroAtaqueIzquirda(origen.getKey())) ||
		   kingSquare.equals(getCasilleroAtaqueDerecha(origen.getKey())) ){
			return true;
		}
		return false;
	}


	public void setBoardState(BoardState boardState) {
		this.boardState = boardState;
	}

}
