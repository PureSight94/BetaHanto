/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentxxx.common;

import java.util.HashMap;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;

public class HantoBoard {

	private static HashMap<HantoCoordinateImpl, HantoPiece> currentBoard = new HashMap<HantoCoordinateImpl, HantoPiece>();

	public static void AddPieceToBoard(HantoPiece piece, HantoCoordinate coordinate)
	{
		HantoCoordinateImpl newCord = new HantoCoordinateImpl(coordinate);
		currentBoard.put(newCord, piece);
	}
	
	public static HantoCoordinateImpl DistanceTo(HantoCoordinate from, HantoCoordinate to) {
		int fromX = Math.abs(from.getX());
		int fromY = Math.abs(from.getY());
		int toX = Math.abs(to.getX());
		int toY = Math.abs(to.getY());
		return new HantoCoordinateImpl(Math.abs(fromX - toX), Math.abs(fromY - toY));
	}

	public static boolean isAdjacent(HantoCoordinate from, HantoCoordinate to) {
		HantoCoordinateImpl dist = DistanceTo(from, to);
		if (dist.getX() + dist.getY() != 1) {
			return false;
		}
		return true;
	}

	public static boolean isAdjacentToAny(HantoCoordinate coordinate) {
		for (HantoCoordinate key : currentBoard.keySet()) {
			if (isAdjacent(key, coordinate)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isTaken(HantoCoordinate coordinate) {
		if (currentBoard.containsKey(coordinate)) {
			return true;
		}
		return false;
	}
	
	public static void printBoard() {
		for (HantoCoordinate key : currentBoard.keySet()) {
			System.out.println(key.getX() + " " + key.getY());
		}
	}

	public static void reset() {
		currentBoard.clear();
	}
}
