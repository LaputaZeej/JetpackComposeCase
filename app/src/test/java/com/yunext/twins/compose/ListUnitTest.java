package com.yunext.twins.compose;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListUnitTest {
    @Test
    public void t1(){
        ArrayList<? extends Number> list = new ArrayList<>(List.of(1,2,3));
//         list.add(Integer(1));
//        list.add(new Object());
        //Number r = list.get(0);
        System.out.println("list.size = " +list.size());
        list.remove(Integer.valueOf(1));
        System.out.println("list.size = " +list.size());

//        ArrayList<? super Number> list2 = new ArrayList<>();
//        list2.add(1f);
//        list2.add(1);
//        list2.add(3);
//        Object object = list2.get(0);


    }
}
