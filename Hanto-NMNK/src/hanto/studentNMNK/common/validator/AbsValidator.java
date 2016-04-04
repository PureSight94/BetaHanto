package hanto.studentNMNK.common.validator;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentNMNK.common.HantoBoard;
import hanto.studentNMNK.common.HantoCoordinateImpl;

public abstract class AbsValidator implements Validator {

	protected boolean redPlayedButterfly = false;
	protected boolean bluePlayedButterfly = false;
	protected HantoBoard board;
	protected int moveNum;
	protected boolean isGameOver = false;
	

	/**
	 * Checks that the Butterfly piece hasnt been played more than once per
	 * player per game. If a player places a Butterfly piece more than once per
	 * game, an exception is thrown
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @throws HantoException
	 */
	public void isButterflyPlayedTwice(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		if ((pieceColor == RED) && (pieceType == BUTTERFLY) && (redPlayedButterfly)) {
			throw new HantoException("Red played butterfly twice");
		} else if ((pieceColor == BLUE) && (pieceType == BUTTERFLY) && (bluePlayedButterfly)) {
			throw new HantoException("Blue played butterfly twice");
		}
	}

	@Override
	public boolean isValidSecondMove(HantoCoordinate to) {
		if (HantoBoard.isAdjacent(new HantoCoordinateImpl(0,0), to))
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if the first piece is placed at coordinate 0,0 as specified by the
	 * BetaHanto rule set
	 * 
	 * @param newTo
	 * @return whether the first move of the game is valid
	 */
	@Override
	public boolean isValidFirstMove(HantoCoordinate newTo) {
		if (newTo.equals(new HantoCoordinateImpl(0, 0))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that the player has played the Butterfly piece by the fourth turn,
	 * if not an Exception is thrown
	 * 
	 * @param bluePlayed
	 * @param redPlayed
	 * @throws HantoException
	 */
	@Override
	public void isButterflyPlayedByMaxTurn(boolean bluePlayed, boolean redPlayed, int butterflyMustBePlayedTurn) throws HantoException {
		boolean isMaxMoveToPlayButterfly = Math.round(((double)moveNum / 2)) == butterflyMustBePlayedTurn;
		boolean butterflyNotPlayed = !redPlayed || !bluePlayed;
		if (butterflyNotPlayed && isMaxMoveToPlayButterfly) {
			throw new HantoException("Fourth turn and butterfly has not been played yet");
		}
	}

	@Override
	public boolean isOccupied(HantoCoordinate coordinate)
	{
		HantoCoordinateImpl newCoordinate = new HantoCoordinateImpl(coordinate);
		if (board.isTaken(newCoordinate))
		{
			return true;
		}
		return false;
	}
}
