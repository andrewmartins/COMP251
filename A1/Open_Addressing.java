// Name: Andrew Martins 
// Student #: 260719278
// Worked with: Aaron Nadal and Elisa Branson

import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
            int A = this.A;
            int w = this.w;
            int r = this.r;
            int h = ((A*key)%power2(w)) >> (w-r);
            int hashvalue = (h + i)%power2(r);
            return hashvalue;
        }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            int[] table = this.Table;
            int i = 0;
            int hashvalue;
            while(i<this.m){
                hashvalue = probe(key,i);
                if(table[hashvalue]<0){
                   table[hashvalue] = key;
                   return i;
                }
                else if(table[hashvalue] == key){
                   return i;
                }
                else{
                   i++;
                }
            }
            return i;
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
            int[] table = this.Table;
            int i = 0;
            int hashvalue;
            while(i<this.m){
                hashvalue = probe(key,i);
                if(table[hashvalue] == key){
                    table[hashvalue] = -2;
                    return i;
                }
                else{
                    i++;
                }
            }
          return i;
        }
}
