package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16};

    Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            int candidateDestinationCoordinate = this.piecePosition + this.getPieceAlliance().getDirection() * currentCandidateOffset;
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            }else if(currentCandidateOffset==16&&this.isFirstMove()
                    &&(BoardUtils.SECOND_ROW[this.piecePosition]
                    &&this.getPieceAlliance().isBlack())
                    ||(BoardUtils.SEVENTH_ROW[this.piecePosition]
                    &&this.getPieceAlliance().isWhite())){
                int bonusPawnMove=this.piecePosition+(this.getPieceAlliance().getDirection()*8);
                if(!board.getTile(candidateDestinationCoordinate).isTileOccupied()&&!board.getTile(bonusPawnMove).isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
                }
            }
        }
        return legalMoves;
    }
}
