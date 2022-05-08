package com.finderfeed.solarforge.magic.blocks.blockentities.vines_puzzle;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.algorithms.a_star.IPosition;

public class ArrayFiller {


    public static void fillFromStringPattern(int[][] matrix,IPosition start,String[] pattern){
        for (int x = 0;x < pattern.length;x++) {
            String f = pattern[x];
            for (int y = 0; y < f.length();y++) {
                char c = f.charAt(y);
                if (c != ' ') {
                    if (Helpers.isIn2DArrayBounds(matrix, start.x() + x, start.y() + y)) {
                        matrix[start.x() + x][start.y() + y] = 1;
                    }
                }
            }
        }
    }






    public enum Dir{
        UP(new int[]{-1,0}),
        DOWN(new int[]{1,0}),
        RIGHT(new int[]{0,1}),
        LEFT(new int[]{0,-1}),


        ;

        public int[] step;

        Dir(int[] step){
            this.step = step;
        }

        public static Dir random(){
            Dir[] r = values();
            return r[Helpers.RANDOM.nextInt(r.length)];
        }

        public static Dir rotateCounterClockwise(Dir dir){
            switch (dir){
                case UP -> { return LEFT; }
                case RIGHT -> { return UP; }
                case DOWN -> { return RIGHT; }
                case LEFT -> { return DOWN; }
            }
            return dir;
        }
        public static Dir rotateClockwise(Dir dir){
            switch (dir){
                case UP -> { return RIGHT; }
                case RIGHT -> { return DOWN; }
                case DOWN -> { return LEFT; }
                case LEFT -> { return UP; }
            }
            return dir;
        }


        public static Dir reverse(Dir dir){
            switch (dir){
                case UP -> { return DOWN; }
                case RIGHT -> { return LEFT; }
                case DOWN -> { return UP; }
                case LEFT -> { return RIGHT; }
            }
            return dir;
        }

    }

}
