/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.  
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentNMNK.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import static hanto.common.HantoPieceType.*;

/**
 * An implementation of the baord using the coordinate implementation class
 * 
 * @author Nicholas Muesch and Nicholas Kalamvokis
 */
public class HantoBoard {

	private Map<HantoCoordinateImpl, HantoPiece> currentBoard = new HashMap<HantoCoordinateImpl, HantoPiece>();

	/**
	 * Copy constructor for HantoBoard
	 * @param hantoBoard
	 */
	public HantoBoard(HantoBoard hantoBoard) {
		currentBoard = hantoBoard.currentBoard;
	}

	public HantoBoard() {

	}

	public Map<HantoCoordinateImpl, HantoPiece> getHantoBoardHash() {
		return currentBoard;
	}

	public void setHantoBoardHash(Map<HantoCoordinateImpl, HantoPiece> board) {
		currentBoard = board;
	}

	/**
	 * Adds a piece that has been placed to the "board" implementation
	 * 
	 * @param piece
	 * @param coordinate
	 */
	public void AddPieceToBoard(HantoPiece piece, HantoCoordinate coordinate) {
		HantoCoordinateImpl newCord = new HantoCoordinateImpl(coordinate);
		currentBoard.put(newCord, piece);
	}
	
	/**
	 * Removes a piece that has been placed on the "board" implementation
	 * @param coordinate
	 */
	public void removePieceToBoard(HantoCoordinate coordinate) {
		HantoCoordinateImpl newCord = new HantoCoordinateImpl(coordinate);
		currentBoard.remove(newCord);
	}

	/**
	 * Checks if the from and to coordinates are adjacent to one another.
	 * 
	 * @param from
	 * @param to
	 * @return true if the two coordinates are adjacent, false otherwise
	 */
	public static boolean isAdjacent(HantoCoordinate from, HantoCoordinate to) {
		if (getAdjacent(from).contains(to)) {
			return true;
		}

		return false;
	}

	/**
	 * Calculates all of the adjacent cells to the given coordinate
	 * 
	 * @param coordinate
	 * @return a List of all adjacent HashCoordinateImpls
	 */
	public static List<HantoCoordinateImpl> getAdjacent(HantoCoordinate coordinate) {
		List<HantoCoordinateImpl> adjacent = new ArrayList<HantoCoordinateImpl>();
		final int x = coordinate.getX();
		final int y = coordinate.getY();
		adjacent.add(new HantoCoordinateImpl(x, y + 1));
		adjacent.add(new HantoCoordinateImpl(x + 1, y));
		adjacent.add(new HantoCoordinateImpl(x + 1, y - 1));
		adjacent.add(new HantoCoordinateImpl(x, y - 1));
		adjacent.add(new HantoCoordinateImpl(x - 1, y));
		adjacent.add(new HantoCoordinateImpl(x - 1, y + 1));
		for (HantoCoordinateImpl coor : adjacent) {
			System.out.println(coor.toString());
		}
		return adjacent;
	}

	/**
	 * Gets the adjacent hexes of a given hex that are currently occupied
	 * @param coordinate
	 * @return a List of the HantoCoordinated corresponding to the occupied hexes surrounding a location
	 */
	public List<HantoCoordinateImpl> getTakenAdjacentHexes(HantoCoordinate coordinate) {
		List<HantoCoordinateImpl> adjacent = getAdjacent(coordinate);
		List<HantoCoordinateImpl> adjacentTaken = new ArrayList<HantoCoordinateImpl>();
		for (HantoCoordinateImpl coord : adjacent) {
			if (isTaken(coord)) {
				adjacentTaken.add(coord);
			}
		}
		return adjacentTaken;
	}

	/**
	 * Checks if a specific Butterfly color is completely surrounded by any
	 * color pieces
	 * 
	 * @param pieceColor
	 * @return true if the Butterfly is surrounded, false otherwise
	 */
	public boolean isButterflySurrounded(HantoPlayerColor pieceColor) {
		HantoCoordinate butterflyLoc = getButterflyLocation(pieceColor);
		if (butterflyLoc == null) {
			return false;
		}
		List<HantoCoordinateImpl> adjacent = getAdjacent(butterflyLoc);
		for (HantoCoordinateImpl coor : adjacent) {
			if (!currentBoard.containsKey(coor)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the current Butterfly location from the current board state
	 * 
	 * @param pieceColor
	 * @return a HantoCoordinateImpl corresponding to the Butterfly
	 */
	public HantoCoordinateImpl getButterflyLocation(HantoPlayerColor pieceColor) {
		for (HantoCoordinate key : currentBoard.keySet()) {
			HantoPiece piece = currentBoard.get(key);
			if ((piece.getColor() == pieceColor) && (piece.getType() == BUTTERFLY)) {
				return new HantoCoordinateImpl(key);
			}
		}
		return null;
	}

	/**
	 * Checks if the given coordinate is adjacent to any piece currently on the
	 * board
	 * 
	 * @param coordinate
	 * @return true if there is at least one adjacent piece, false otherwise
	 */
	public boolean isAdjacentToAny(HantoCoordinate coordinate) {
		for (HantoCoordinate key : currentBoard.keySet()) {
			if (isAdjacent(key, coordinate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the current coordinate already has a piece on it
	 * 
	 * @param coordinate
	 * @return true if there is already a piece at this coordinate, false
	 *         otherwise
	 */
	public boolean isTaken(HantoCoordinate coordinate) {
		if (currentBoard.containsKey(coordinate)) {
			return true;
		}
		return false;
	}

	/**
	 * Prints the current board
	 * 
	 * @return String representation of the board state
	 */
	public String printBoard() {
		String boardState = "";
		for (HantoCoordinate key : currentBoard.keySet()) {
			boardState += "(" + key.getX() + "," + key.getY() + ") " + currentBoard.get(key).getColor() + " "
					+ currentBoard.get(key).getType().getPrintableName() + "\n";
		}
		return boardState;
	}

	/**
	 * Retrieves information about a piece at a given location on the HantoBoard
	 * @param location
	 * @return the HantoPiece at the current coordinate
	 */
	public HantoPiece getPieceAtLocation(HantoCoordinate location) {
		return currentBoard.get(location);
	}

}
