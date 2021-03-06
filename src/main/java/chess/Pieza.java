package chess;

import movegenerators.MoveGenerator;
import movegenerators.MoveGeneratorStrategy;

public enum Pieza {
	PEON_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getPeonBlancoMoveGenerator()),
	PEON_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getPeonNegroMoveGenerator()),
	
	TORRE_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getTorreBlancaMoveGenerator()),
	TORRE_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getTorreNegraMoveGenerator()),
	
	CABALLO_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getCaballoBlancoMoveGenerator()),
	CABALLO_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getCaballoNegroMoveGenerator()),
	
	ALFIL_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getAlfilBlancoMoveGenerator()),
	ALFIL_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getAlfilNegroMoveGenerator()),
	
	REINA_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getReinaBlancaMoveGenerator()),
	REINA_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getReinaNegraMoveGenerator()),
	
	REY_BLANCO(Color.BLANCO, (MoveGeneratorStrategy strategy) -> strategy.getReyBlancoMoveGenerator()),
	REY_NEGRO(Color.NEGRO, (MoveGeneratorStrategy strategy) -> strategy.getReyNegroMoveGenerator());
	
	private Color color;
	private StrategySelector selector = null;
	
	private Pieza(Color color, StrategySelector selector) {
		this.color = color;
		this.selector = selector;
	}
	
	public Color getColor(){
		return color;
	}
	
	public static Pieza getRey(Color color){
		switch (color) {
		case  BLANCO:
			return REY_BLANCO;
		case  NEGRO:
			return REY_NEGRO;
		default:
			throw new RuntimeException("Invalid color");
		}
	}
	
	public static Pieza getReina(Color color){
		switch (color) {
		case  BLANCO:
			return REINA_BLANCO;
		case  NEGRO:
			return REINA_NEGRO;
		default:
			throw new RuntimeException("Invalid color");
		}
	}
	
	public static Pieza getAlfil(Color color){
		switch (color) {
		case  BLANCO:
			return ALFIL_BLANCO;
		case  NEGRO:
			return ALFIL_NEGRO;
		default:
			throw new RuntimeException("Invalid color");
		}
	}
	
	public static Pieza getTorre(Color color){
		switch (color) {
		case  BLANCO:
			return TORRE_BLANCO;
		case  NEGRO:
			return TORRE_NEGRO;
		default:
			throw new RuntimeException("Invalid color");
		}
	}	

	public MoveGenerator getMoveGenerator(MoveGeneratorStrategy strategy) {
		return this.selector.getMoveGenerator(strategy);
	}
}
