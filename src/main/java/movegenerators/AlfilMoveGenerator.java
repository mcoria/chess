package movegenerators;

import chess.Color;
import iterators.Cardinal;

public class AlfilMoveGenerator extends CardinalMoveGenerator {

	public AlfilMoveGenerator(Color color) {
		super(color, new Cardinal[] {Cardinal.NorteEste, Cardinal.SurEste, Cardinal.SurOeste, Cardinal.NorteOeste});
	}

}
