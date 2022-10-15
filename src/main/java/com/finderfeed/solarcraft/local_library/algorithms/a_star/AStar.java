package com.finderfeed.solarcraft.local_library.algorithms.a_star;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AStar {

    private static final IPosition[] NEIGHBOURS = {
            new IPosition(0,1,0),
            new IPosition(0,-1,0),
            new IPosition(1,0,0),
            new IPosition(-1,0,0)
    };

    /**
     * Matrix: o--->Y
     *         |
     *        \/
     *        X
     * Finds the shortest way from initial and end position in 2d matrix. Doesn't take diagonals in account.
     * @param matrix a 2d array where 0 - free path, 1 - blocked
     * @param initPos initial position in array
     * @param endPos end position in array
     * @return shortest path between them (null if path doesn't exist)
     */
    public static List<IPosition> pathFrom2DArrayNoDiagonals(int[][] matrix, IPosition initPos,IPosition endPos){
        int[][] mCopy =  copyMatrix(matrix);
        if (mCopy[initPos.x()][initPos.y()] == 1 || mCopy[endPos.x()][endPos.y()] == 1) return null;
        if (!initPos.equals(endPos)) {
            List<INode> openNodes = new ArrayList<>();
            INode start = new INode(initPos,endPos,0);
            List<IPosition> pat = new ArrayList<>();
            pat.add(initPos);
            start.setSavedPath(pat);
            openNodes.add(start);
            INode finalNode = null;
            while (!openNodes.isEmpty()){
                openNodes.sort(Comparator.comparingDouble(n->n.fin));
                INode node = openNodes.remove(0);
                if (node.position.equals(endPos)){
                    finalNode = node;
                    break;
                }
                for (IPosition n : NEIGHBOURS){
                    IPosition p = node.position.add(n);
                    if (canMoveTo(mCopy,p.x(),p.y())){
                        mCopy[p.x()][p.y()] = 1;
                        INode candidate = new INode(p,endPos,node.distanceFromStart + 1);
                        List<IPosition> path = new ArrayList<>(node.savedPath);
                        path.add(p);
                        candidate.setSavedPath(path);
                        openNodes.add(candidate);
                    }
                }
            }
            if (finalNode != null) {
                return finalNode.savedPath;
            }else{
                return null;
            }
        }else{
            return List.of(initPos);
        }
    }
    private static int[][] copyMatrix(int[][] matrix){
        int[][] n = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length;i++){
            for (int g = 0; g < matrix[i].length;g++){
                n[i][g] = matrix[i][g];
            }
        }
        return n;
    }


    private static boolean canMoveTo(int[][] matrix,int x,int y){
        return x >= 0
                &&
                x < matrix.length
                &&
                y >= 0
                &&
                y < matrix[0].length
                &&
                matrix[x][y] != 1;
    }


    private static class INode{
        private final IPosition position;
        private double heuristic;
        private double fin;
        private double distanceFromStart;
        private List<IPosition> savedPath = new ArrayList<>();

        private INode(IPosition nodePos, IPosition finalPos, double distFromStart){
            this.position = nodePos;
            this.heuristic = finalPos.distTo(nodePos);
            this.distanceFromStart = distFromStart;
            this.fin = distFromStart + heuristic;
        }

        public void setSavedPath(List<IPosition> savedPath) {
            this.savedPath = savedPath;
        }
    }

}
