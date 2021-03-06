package moveexecutors;

import chess.PosicionPieza;
import layers.ColorBoard;

public class CaptureReyMove extends CaptureMove {

	
	public CaptureReyMove(PosicionPieza from, PosicionPieza to) {
		super(from, to);
	}


	@Override
	public void executeMove(ColorBoard cache) {
		super.executeMove(cache);
		cache.setKingSquare(from.getValue().getColor(), to.getKey());
	}

	@Override
	public void undoMove(ColorBoard cache) {
		super.undoMove(cache);
		cache.setKingSquare(from.getValue().getColor(), from.getKey());		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)  && obj instanceof CaptureReyMove){
			return true;
		}
		return false;
	}
	
	@Override
	protected String getType() {
		return "CaptureReyMove";
	}	
}
