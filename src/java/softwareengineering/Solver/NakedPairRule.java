package softwareengineering.Solver;

import java.util.*;

public class NakedPairRule implements DeductionRule {
    @Override
    public boolean apply(int[][] board) {
        boolean changed = false;

        for (int row = 0; row < 9; row++) {
            changed |= eliminatePairs(getRow(board, row));
        }

        for (int col = 0; col < 9; col++) {
            changed |= eliminatePairs(getColumn(board, col));
        }

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                changed |= eliminatePairs(getBlock(board, blockRow, blockCol));
            }
        }

        return changed;
    }

    private boolean eliminatePairs(List<int[]> positions) {
        Map<Set<Integer>, List<int[]>> candidates = new HashMap<>();
        boolean changed = false;

        for (int[] pos : positions) {
            Set<Integer> possibleValues = getPossibleValues(pos[0], pos[1]);
            if (possibleValues.size() == 2) {
                candidates.computeIfAbsent(possibleValues, k -> new ArrayList<>()).add(pos);
            }
        }

        for (Map.Entry<Set<Integer>, List<int[]>> entry : candidates.entrySet()) {
            if (entry.getValue().size() == 2) {
                for (int[] pos : positions) {
                    if (!Arrays.equals(pos, entry.getValue().get(0)) && !Arrays.equals(pos, entry.getValue().get(1))) {
                        for (int val : entry.getKey()) {
                            if (removeCandidate(pos[0], pos[1], val)) {
                                changed = true;
                            }
                        }
                    }
                }
            }
        }

        return changed;
    }

    private boolean removeCandidate(int row, int col, int candidate) {
        // Diese Methode sollte implementiert werden, um einen Kandidaten aus den möglichen Werten zu entfernen.
        return false;
    }

    private List<int[]> getRow(int[][] board, int row) {
        List<int[]> rowPositions = new ArrayList<>();
        for (int col = 0; col < 9; col++) {
            rowPositions.add(new int[]{row, col});
        }
        return rowPositions;
    }

    private List<int[]> getColumn(int[][] board, int col) {
        List<int[]> colPositions = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            colPositions.add(new int[]{row, col});
        }
        return colPositions;
    }

    private List<int[]> getBlock(int[][] board, int blockRow, int blockCol) {
        List<int[]> blockPositions = new ArrayList<>();
        int startRow = blockRow * 3;
        int startCol = blockCol * 3;
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                blockPositions.add(new int[]{row, col});
            }
        }
        return blockPositions;
    }

    private Set<Integer> getPossibleValues(int row, int col) {
        // Implementiere diese Methode, um die möglichen Werte für ein bestimmtes Feld zu erhalten.
        return new HashSet<>();
    }
}
