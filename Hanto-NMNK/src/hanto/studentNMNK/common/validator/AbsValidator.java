/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.  
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentNMNK.common.validator;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentNMNK.common.HantoBoard;
import hanto.studentNMNK.common.HantoCoordinateImpl;
import hanto.studentNMNK.common.HantoPieceImpl;

/**
 * An abstract class with all of the common methods needed to validate a Hanto Game move
 * This is used for any subsequent version of the Hanto game. 
 * @author Nicholas Muesch & Nicholas Kalamvokis
 *
 */
public abstract class AbsValidator implements Validator {

	protected boolean redPlayedButterfly = false;
	protected boolean bluePlayedButterfly = false;
	protected HantoBoard board;
	protected int moveNum;
	protected boolean isGameOver = false;
	protected boolean blueFirst;
	protected HantoPlayerColor pieceColor;
	protected HantoPieceType pieceType;

	public void setPieceType(HantoPieceType pieceType) {
		this.pieceType = pieceType;
	}

	public HantoPlayerColor getPlayerColor() {
		if (blueFirst) {
			pieceColor = (moveNum % 2 == 0 ? RED : BLUE);
		} else {
			pieceColor = (moveNum % 2 == 0 ? BLUE : RED);
		}
		return pieceColor;
	}

	/**
	 * Checks if the current player tries to move before they have placed their buttefly piece
	 * @param from
	 * @return true if the butterfly is already placed when attempting to move, false otherwise
	 */
	public boolean isButterflyPlacedBeforeMove(HantoCoordinate from) {
		HantoPlayerColor currentColor = getPlayerColor();
		boolean blueCannotMove = ((currentColor == BLUE) && !bluePlayedButterfly);
		boolean redCannotMove = ((currentColor == RED) && !redPlayedButterfly);
		if(from != null && (blueCannotMove || redCannotMove))
		{
			return true;
		}
	return false;
	}

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

	/**
	 * Checks to see if the player attempting to "walk" a piece is moving in a valid way
	 * @param to
	 * @param from
	 * @return true if the walk is valid, false otherwise
	 */
	public boolean isValidWalk(HantoCoordinate to, HantoCoordinate from) {
		HantoCoordinateImpl newTo = new HantoCoordinateImpl(to);
		HantoCoordinateImpl newFrom = new HantoCoordinateImpl(from);
		List<HantoCoordinateImpl> adjacent = HantoBoard.getAdjacent(newFrom);
		int indexOfTo = adjacent.indexOf(newTo);
		int leftOfTo = indexOfTo - 1;
		int rightOfTo = indexOfTo + 1;

		if (indexOfTo + 1 > adjacent.size() -1) {
			rightOfTo = 0;
		}
		if (indexOfTo - 1 < 0) {
			leftOfTo = adjacent.size() - 1;
		}
		if (board.isTaken(newTo)) {
			return false;
		}
		if ((!board.isTaken(adjacent.get(leftOfTo)) || !board.isTaken(adjacent.get(rightOfTo))) 
				&& HantoBoard.isAdjacent(newFrom, newTo) && isContiguous(newFrom, newTo)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if the board state is currently one contiguous grouping
	 * @param newFrom
	 * @param newTo
	 * @return true if the board is one large grouping, false otherwise
	 */
	public boolean isContiguous(HantoCoordinateImpl newFrom, HantoCoordinateImpl newTo) {
		HantoBoard newBoard = new HantoBoard(board);
		Map<HantoCoordinateImpl, HantoPiece> newBoardHash = newBoard.getHantoBoardHash();
		newBoardHash.remove(newFrom);
		newBoardHash.put(newTo, new HantoPieceImpl(pieceColor, BUTTERFLY));
		newBoard.setHantoBoardHash(newBoardHash);
		System.out.println(newBoard.printBoard());

		List<HantoCoordinateImpl> covered = new ArrayList<HantoCoordinateImpl>();
		List<HantoCoordinateImpl> notCovered = new ArrayList<HantoCoordinateImpl>();
		List<HantoCoordinateImpl> toCover = new ArrayList<HantoCoordinateImpl>();

		notCovered.add(newTo);
		while (notCovered.size() > 0) {
			for (HantoCoordinateImpl checkCoord : notCovered) {
				for (HantoCoordinateImpl coord : newBoard.getTakenAdjacentHexes(checkCoord)) {
					toCover.add(coord);
				}
				covered.add(checkCoord);
			}

			for (HantoCoordinateImpl newCoord : toCover) {
				if (!notCovered.contains(newCoord)) {
					notCovered.add(newCoord);
				}
			}

			for (HantoCoordinateImpl checkCoord : covered) {
				if (notCovered.contains(checkCoord)) {
					notCovered.remove(checkCoord);
				}
			}
		}

		return (covered.size() == newBoardHash.size());
	}

	/**
	 * Checks that the second move is valid
	 * 
	 * @param to
	 */
	@Override
	public boolean isValidSecondMove(HantoCoordinate to) {
		if (HantoBoard.isAdjacent(new HantoCoordinateImpl(0, 0), to)) {
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
	public void isButterflyPlayedByMaxTurn(boolean bluePlayed, boolean redPlayed, int butterflyMustBePlayedTurn)
			throws HantoException {
		boolean isMaxMoveToPlayButterfly = Math.round(((double) moveNum / 2)) == butterflyMustBePlayedTurn;
		boolean butterflyNotPlayed = !redPlayed || !bluePlayed;
		if (butterflyNotPlayed && isMaxMoveToPlayButterfly) {
			throw new HantoException("Fourth turn and butterfly has not been played yet");
		}
	}

	@Override
	public boolean isOccupied(HantoCoordinate coordinate) {
		HantoCoordinateImpl newCoordinate = new HantoCoordinateImpl(coordinate);
		if (board.isTaken(newCoordinate)) {
			return true;
		}
		return false;
	}
}
