/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.utilities;

import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author ADMIN
 */
public class Utils {

    public static int getNumberRandom() {
        Random rand = new Random();
        IntStream intStream = rand.ints(1, 5, 11);
        return intStream.findFirst().getAsInt();
    }

}
