package movegenerators;

import java.util.ArrayList;
import java.util.Collection;

import chess.Move;
import chess.Square;

public class MoveGeneratorResult {
	
	private Collection<Move> moveContainer;
	
	private long affectedByContainer;

	private boolean saveMovesInCache;

	public MoveGeneratorResult() {
		moveContainer = createContainer(); 
	}
	
	public Collection<Move> getPseudoMoves(){
		return moveContainer;
	}
	

	public long getAffectedBy() {
		return affectedByContainer;
	}
	
	
	public boolean isSaveMovesInCache(){
		return this.saveMovesInCache;
	}

	public void setSaveMovesInCache(boolean flag){
		this.saveMovesInCache = flag;
	}
	
	public void affectedByContainerAdd(Square key) {
		affectedByContainer |= key.getPosicion();
	}		
	
	public void moveContainerAdd(Move move) {
		moveContainer.add(move);
	}	
	
	private static <T> Collection<T> createContainer(){
		return new ArrayList<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2237718042714336104L;

			@Override
			public String toString() {
				StringBuffer buffer = new StringBuffer(); 
				for (T move : this) {
					buffer.append(move.toString() + "\n");
				}
				return buffer.toString();
			}
		};
	}
	
}
