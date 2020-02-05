// Name: Andrew Martins 
// Student #: 260719278
// Worked with: Aaron Nadal and Elisa Branson

import java.io.*;
import java.util.*;


public class main {     
    public static void main(String[] args) {
        int[] keysX = {86, 85, 6, 97, 19, 66, 26, 14, 15, 49, 75, 64, 35, 54, 31, 9, 82, 29, 81, 13};
        Chaining chain1 = new Chaining(10, 0, 554);
        Open_Addressing open_add1 = new Open_Addressing(10, 0, 554);
        int coll_result_chain_1 = chain1.insertKeyArray(keysX);
        int coll_result_open_add_1 = open_add1.insertKeyArray(keysX);
        int[] keysY = {79, 13, 45, 64, 32, 95, 67, 27, 78, 18, 41, 69, 15, 29, 72, 57, 81, 50, 60, 14};
        Chaining chain2 = new Chaining(10,0,590);
        Open_Addressing open_add2 = new Open_Addressing(10,0,590);
        int coll_result_chain_2 = chain2.insertKeyArray(keysY);
        int coll_result_open_add_2 = open_add2.insertKeyArray(keysY);
        System.out.print("\n");
        System.out.print("The number of collisions for Chaining on LisX is: " + coll_result_chain_1);
        System.out.print("\n");
        System.out.print("The number of collisions for Chaining on ListY is: " + coll_result_chain_2);
        System.out.print("\n");
        System.out.print("The number of collisions for Open Addressing on ListX is: " + coll_result_open_add_1);
        System.out.print("\n");
        System.out.print("The number of collisiions for Open Addressing on ListY is: " + coll_result_open_add_2);
        System.out.print("\n");
        
    }
}