package com.company;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForLong{
        private static long compTime;
        private static long compTimeParallel;

    public static void main(String[] args) {
        Long n;
        Long m;
        int c;
        try{
            n=Long.parseUnsignedLong(args[0]);
            m=Long.parseUnsignedLong(args[1]);
            c=Integer.parseUnsignedInt(args[2]);
        }catch (Exception ex){
            System.out.println("Arguments are not correct!");
            return;
        }

        List<Long> result= streamer(n,m,Integer.toString(c));
        List<Long> presult=parallelStreamer(n,m,Integer.toString(c));

        writeToFile(result,args[3]);
        writeToFile(presult,args[3]);

    }


    public static boolean isSimpleL(Long l){
        boolean simple=true;
        for (int i=2;i<l;i++){
            if(l%i==0){
                simple=false;
                break;
            }
        }
        return simple;
    }


    public static List<Long> streamer(Long n, Long m, String c){
        long time=System.nanoTime();
        List<Long> list=new ArrayList<>();
        long size=m-n;

        Stream<Long> longStream=Stream.iterate(n,i->i+1);

        list=longStream.limit(size).filter(j->j.toString().endsWith(c)).filter(j->isSimpleL(j)).collect(Collectors.toList());

        compTime=System.nanoTime()-time;
        System.out.println("Time for consistant:"+compTime);
        return list;
    }

    public static  List<Long> parallelStreamer(Long n,Long m,String c){
        long time=System.nanoTime();
        List<Long> list=new ArrayList<>();
        long size=m-n;

        Stream<Long> longStreamP=Stream.iterate(n,i->i+1);

        longStreamP=longStreamP.parallel();
        list=longStreamP.limit(size).filter(j->j.toString().endsWith(c)).filter(j->isSimpleL(j)).collect(Collectors.toList());

        compTimeParallel =System.nanoTime()-time;
        System.out.println("Time for parallel:"+ compTimeParallel);
        return list;
    }

    public static void writeToFile(List<Long> list,String path){
        PrintWriter writer=null;
        try{
            writer=new PrintWriter(path);
            writer.print(list.size()+":<");
            for (Long b: list){
                writer.print(b+" ");
            }
            writer.print(">");}catch (Exception ex){}finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
