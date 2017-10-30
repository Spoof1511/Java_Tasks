package com.company;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForBigInt {
    private static long compTime;
    private static long compTimeParallel;

    public static void main(String[] args) {
        BigInteger n;
        BigInteger m;
        int c;
        try{
            n=new BigInteger(args[0]);
            m=new BigInteger(args[1]);
            c=Integer.parseUnsignedInt(args[2]);
        }catch (Exception ex){
            System.out.println("Arguments are not correct!");
            return;
        }

        List<BigInteger> result= streamer(n,m,Integer.toString(c));
        List<BigInteger> presult=parallelStreamer(n,m,Integer.toString(c));

        writeToFile(result,args[3]);
        writeToFile(presult,args[3]);
    }

    public static List<BigInteger> streamer(BigInteger n, BigInteger m, String c){
        long time=System.nanoTime();
       List<BigInteger> list=new ArrayList<>();
        long size=m.subtract(n).longValue();

        Stream<BigInteger> bigIntegerStream=Stream.iterate(n,i->i.add(BigInteger.ONE));

        list=bigIntegerStream.limit(size).filter(j->j.toString().endsWith(c)).filter(j->isSimpleBI(j)).collect(Collectors.toList());

        compTime=System.nanoTime()-time;
        System.out.println("Time for consistant:"+compTime);
        return list;
    }

    public static  List<BigInteger> parallelStreamer(BigInteger n,BigInteger m,String c){
        long time=System.nanoTime();
       List<BigInteger> list=new ArrayList<>();
        long size=m.subtract(n).longValue();

        Stream<BigInteger> bigIntegerStream=Stream.iterate(n,i->i.add(BigInteger.ONE));

        bigIntegerStream=bigIntegerStream.parallel();
        list=bigIntegerStream.limit(size).filter(j->j.toString().endsWith(c)).filter(j->isSimpleBI(j)).collect(Collectors.toList());

        compTimeParallel =System.nanoTime()-time;
        System.out.println("Time for parallel:"+ compTimeParallel);
        return list;
    }

    public static boolean isSimpleBI(BigInteger number) {
        if (!number.isProbablePrime(5))
            return false;

        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
            if (BigInteger.ZERO.equals(number.mod(i)))
                return false;
        }
        return true;
    }

    public static void writeToFile(List<BigInteger> list,String path){
        PrintWriter writer=null;
        try{
        writer=new PrintWriter(path);
        writer.print(list.size()+":<");
        for (BigInteger b: list){
            writer.print(b+" ");
        }
        writer.print(">");}catch (Exception ex){}finally {
            if(writer!=null){
            writer.close();
            }
        }
    }
}


