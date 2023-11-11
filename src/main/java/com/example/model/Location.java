package com.example.model;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class Location {

    private int x;
    private int y;

    public char getXAsLetter() {
        return getLetterByNumber(getX());
    }

    public void setXAsLetter(char x) {
        int xInt = getNumberForLetter(x);
        this.setX(xInt);
    }

    public static Location stringToLocation(String location) {

        var chX = location.charAt(0);
        int x = getNumberForLetter(chX);

        var strY = location.substring(1);
        int y = Integer.parseInt(strY);

        return new Location(x, y);
    }

    private static int getNumberForLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return ch - 'A' + 1;
    }

    private static char getLetterByNumber(int number) {
        return (char) ('A' + number - 1);
    }

    public String toString() {
        return String.format("%c%d", getXAsLetter(), getY());
    }
}
