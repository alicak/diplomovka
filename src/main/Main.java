package main;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Double a = 3.5;
//        Double b = null;
//        b = a;
//        a = null;
//
//        //System.out.println(b);
//
//        Chromosome c = new Chromosome();
//        System.out.println(c.getFitness());

//        List<Character> cisla = new ArrayList<>();
//        cisla.add('a');
//        cisla.add('b');
//        cisla.add('c');
//        cisla.add('d');
//        System.out.println("povodny - abcd: " + cisla);
//        cisla.remove(1);
//        System.out.println("po odobrati - acd: " + cisla);
//        cisla.add(1, 'e');
//        System.out.println("po pridani - aecd: " + cisla);

//        int nula = 0;
//        int minusJeden = -1;
//        int minusDva = -2;
//        int jeden = 1;
//        int dva = 2;
//
//        System.out.println("-2: " + (minusDva && nula));
//        System.out.println("-1: " + minusJeden);
//        System.out.println("0: " + nula);
//        System.out.println("1: " + jeden);
//        System.out.println("2: " + dva);

        Integer[] pole = new Integer[3];
        Arrays.fill(pole, 0);
        List<Integer> list = Arrays.asList(pole);
        list.set(1,3);
        System.out.println(list);

        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list2.add(list.get(i));
        }
        System.out.println(list2);

        list.set(1,2);
        list2.set(2,1);
        System.out.println("po zmene: ");
        System.out.println(list);
        System.out.println(list2);

    }
}
