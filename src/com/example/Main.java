package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }

    //Input is the matrix of string consisting of # and whitespace.

    /**
     * Assumptions:
     * All spaces are reachable.
     * You can assume there are no rooms 1 space wide (for example, narrow hallways).
     * @param floor - a matrix of n X m consists of # and whitespace
     */
    public Character[][] colorizeRooms(Character[][] floor) {

        if(floor.length == 0) System.out.println("Invalid Floor Plan");

        List<Room> roomList = new ArrayList<>(); //Contains the list of the rooms.

        //Idenfity the room using this method first to obtain the limits of each room to be colorized.
        for(int i = 1; i < floor.length-1; i++){ //First and last row are the boundaries for the floor. Not required to be evaluated.
            for(int j = 1; j < floor[0].length-1; j++) { // First and Last col are the boundaries for the floor. Not required to be evaluated
                //Identify the top left corner of the room.
                if(floor[i-1][j].equals('#') && floor[i-1][j-1].equals('#') && floor[i][j-1].equals('#')) {
                    //Create a new room and find other corners
                    Room room = new Room();
                    room.setMinCol(j);
                    room.setMinRow(i);

                    //Identify the bottom right as the diagonal will give the limits for the room.
                    room = identifyCorners(i,j,floor,room);
                    roomList.add(room);
                }
            }
        }

        int currentColor = '0';
        //Go through each room and fill in the space with colors.
        //Assuming each number represents the color to
        for (Room room: roomList){
            int maxRow = room.getMaxRow();
            int maxCol = room.getMaxCol();
            int minRow = room.getMinRow();
            int minCol = room.getMinCol();
            for(int i = minRow; i <= maxRow; i++){
                for(int j = minCol; j <= minCol; j++){
                    floor[i][j] = (char) currentColor;
                }
            }
            currentColor += 1;
        }

        System.out.println(floor);

        return floor;
    }

    /**
     * The function allows to identify the bottom right corner to evaluate the limit of the room
     * @param row
     * @param col
     * @param floor
     * @param currentRoom
     * @return
     */
    Room identifyCorners(int row, int col, Character[][] floor, Room currentRoom) {
        for(int i = row; i < floor.length; i++){
            for(int j = col; j < floor[row].length; j++) {
                //Search for bottom right corner - Look for the first instance of the corners where
                // row,col+1 & row+1,col & row+1 and col+1  == '#' - Bottom corner found.
                if(floor[i][j+1].equals('#') && floor[i+1][j].equals('#') && floor[i+1][j+1].equals('#')) {
                    currentRoom.setMaxRow(i);
                    currentRoom.setMaxCol(j);
                    break;
                }
            }
        }
        return currentRoom;
    }

    //Represents the room.
    class Room {
        private int minRow;
        private int minCol;
        private int maxRow;
        private int maxCol;

        Room() {
            this.minCol = Integer.MAX_VALUE;
            this.minRow = Integer.MAX_VALUE;
            this.maxCol = 0;
            this.maxRow = 0;
        }

        public int getMinRow() {
            return minRow;
        }

        public void setMinRow(int minRow) {
            this.minRow = minRow;
        }

        public int getMinCol() {
            return minCol;
        }

        public void setMinCol(int minCol) {
            this.minCol = minCol;
        }

        void setMaxRow(int row) {
            this.maxRow = row;
        }

        int getMaxRow() {
            return this.maxRow;
        }

        void setMaxCol(int col){
            this.maxCol = col;
        }

        int getMaxCol() {
            return this.maxCol;
        }
    }
}
