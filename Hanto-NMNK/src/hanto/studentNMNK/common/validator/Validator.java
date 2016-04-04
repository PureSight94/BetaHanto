package hanto.studentNMNK.common.validator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentNMNK.common.HantoBoard;

public interface Validator {

	/**
	 * Checks if the player is making a valid move under the rule set defined in
	 * the GammaHnato Game
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @param to
	 * @return whether the given move is valid
	 * @throws HantoException
	 */
	boolean isValidMove(HantoPieceType pieceType, HantoPlayerColor pieceColor, HantoCoordinate from, HantoCoordinate to)
			throws HantoException;

	/**
	 * Checks if the first piece is placed at coordinate 0,0 as specified by the
	 * GammaHanto rule set
	 * 
	 * @param newTo
	 * @return whether the first move of the game is valid
	 */
	boolean isValidFirstMove(HantoCoordinate newTo);

	/**
	 * Checks if the second piece is placed correctly GammaHanto rule set
	 * 
	 * @param newTo
	 * @return whether the first move of the game is valid
	 */
	boolean isValidSecondMove(HantoCoordinate to);
	
	boolean isValidSubsequentMove(HantoCoordinate to, HantoCoordinate from);

	/**
	 * Checks that the player has played the Butterfly piece by the fourth turn,
	 * if not an Exception is thrown
	 * 
	 * @param bluePlayed
	 * @param redPlayed
	 * @throws HantoException
	 */
	void isButterflyPlayedByMaxTurn(boolean bluePlayed, boolean redPlayed, int butterflyMustBePlayedTurn)
			throws HantoException;

	/**
	 * Checks that the Butterfly piece hasnt been played more than once per
	 * player per game. If a player places a Butterfly piece more than once per
	 * game, an exception is thrown
	 * 
	 * @param pieceType
	 * @param pieceColor
	 * @throws HantoException
	 */
	void isButterflyPlayedTwice(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException;

	void updateGame(int moveNumber, HantoBoard currentBoard);
	
	boolean isOccupied(HantoCoordinate coordinate);

	MoveResult getBoardState();
	
	HantoPlayerColor getPlayerColor();
}
