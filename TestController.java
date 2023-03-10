package com.zhoufeng.web.controller.zlxtreport;


import com.zhoufeng.common.annotation.Log;
import com.zhoufeng.common.core.domain.AjaxResult;
import com.zhoufeng.common.enums.BusinessType;
import com.zhoufeng.common.utils.DateUtils;
import com.zhoufeng.domain.Czd;
import com.zhoufeng.domain.KcbPdMx;
import com.zhoufeng.domain.SbRkd;
import com.zhoufeng.mapper.KcbPdMxMapper;
import com.zhoufeng.service.PublicFunctionService;
import lombok.Data;
import lombok.val;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.validator.internal.properties.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("/test")
public class TestController extends RuntimeException{

    //枚举类型
    public enum  type{
        A,B,C
    }


    /***
     * 对于一些包装类的测试
     * @param args
     */
    public static void main2(String[] args){

        Integer a=100;
        Integer b=100;
        System.out.println(a==b);//true
        //自动装箱的机制：  执行的其实是，直接new了一个对象出来
        // valueOf 首先获取Integer缓存机制，中的常量池，也就是说 其实在jvm中，已经存放了 -127  -128 的数据，到常量池中
        //如果 包含在-128  -128 这里面的话，实际上，并不会创建新的对象，而是直接将常量池的引用地址返回来
        // 否则超出缓存的范围，才会 new一个对象，new 一个对象的话，就相当于在堆内存中，新建一个内存地址，返回来的铁定不一样
        //相等 true
        System.out.println(a==b);
        Integer a1=1000;
        Integer b1=1000;
        //不相等 false
        System.out.println(a1==b1);

        /**
         * 字符串的常量池是存放在堆内存中的
         * String的自动装箱，和其他数据类型的还不一致，因为String其实并没有缓存机制这个概念，
         * 他存在一个常量池，当StrA被装箱时
         * 字符串常量池是Java堆内存中一个特殊的存储区域
         */
        String strA="aaa";
        String strB="aaa";
        /**
         * StrA=aaa，创建了两个对象 一个String类型的空对象，另一个在常量池中创建了一个aaa的对象，然后将aaa的引用地址赋值给了StrA
         * 当再次创建strB的时候呢，由于常量池中已经有了aaa 所以不需要在常量池中创建对象，转而直接将之前的aaa引用地址赋值给strB
         */
        //相等true
        System.out.println(strA==strB);

        String strA1=new String("aaa");
        String strB1=new String("aaa");
        //为啥会是false呢
        System.out.println("111"+ strA1==strB1);

        String strC="aaaa";
        /**
         * string很有意思，这个过程中，到底是执行了什么呢
         * 首先创建了一个对象，String的自动装箱，新建一个空的String对象   将aaa存放到了常量池中，
         * 然后其实将，aaa中常量池的引用地址给了strC，
         * 当我吧strC改为bbb的时候呢，其实是又把bbb，新建了一个常量池对象，原本的aaa并没有改变，只是将新的厂里bbb，的引用地址给了aaa
         * 这也就是为啥String是静态的，static final是为了常量池
         *
         */
        strC+="bbbb";
        /**
         * +=和-=是为二被java重写过得计算符
         * +=和-=本质上就是新建了一个StringBuilder；
         * 内部就是调用StringBuilder的append方法
         */

        String StrD="a";

        String[] arr = {"he", "llo", "world"};
        StringBuilder s = new StringBuilder();
        for (String value : arr) {
            s.append(value);
        }
        System.out.println(s);
        //String 的常量折叠。。。。
        System.out.println("a".hashCode());
        System.out.println("100".hashCode());
        System.out.println("100".hashCode());

        Integer a2=100;
        Integer b2=100;
        System.out.println(a2.hashCode());
        System.out.println(b2.hashCode());

        Integer a3=new Integer(800);
        Integer b3=new Integer(800);
        System.out.println(a3);
        System.out.println(b3.hashCode());
        System.out.println("-----------");
        String x01="a";
        String x02="a";
        String x10=String.valueOf("a");
        String x11=String.valueOf("a");
        //不相等，为啥
        System.out.println(String.valueOf("a")==x01);
        System.out.println(String.valueOf(x01)==x01);
        System.out.println(x01==x02);
        System.out.println("###########");
        String strA01="aaa";
        String strB01=new String("aaa");
        String strB03=new String("aaa");
        System.out.println(strA01==strB01);
        System.out.println(strA01==strB03);
        System.out.println(strB01==strB03);
        System.out.println("------------读取IO    --------------------");
        TestIOStream();
    }


    /***
     * IO流相关测试
     */
    public static void TestIOStream(){

        String fileName="D:/Hello.txt";
        File f=new File(fileName);
        //一个字节输入流
        InputStream in= null;
        try {
            //输入流
            in = new FileInputStream(f);
            //btye是字节，已经申请出来空间了
            byte[] b=new byte[(int)f.length()];
            //从字节流中读取内容，并赋值给字节b  字节流，只能读取给字节 byte
            in.read(b);
            in.close();
            System.out.println(new String(b));
            Calendar calendar=Calendar.getInstance();

            for (byte b1 : b) {
                b1=(byte)in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

/**
 * IO流
 */
class  testB{

    public static void main(String[] args) {

        String fileName="D:/aaa.txt";
        File file=new File(fileName);
        OutputStream outputStream=null;
        String a="测试输出内容+++";
        //byte的valueof只能是数字，转换weibyte
        // Byte aByte = Byte.valueOf(a);
        //输出流
        byte[] bytes = a.getBytes();
        try {
            outputStream=new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {


        }
    }
}



    class testIO {

        public static void main(String[] args) {

            File file = new File("D:/hello.text");

            System.out.println("----");
            try {
                //序列化的作用
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.close();
            } catch (IOException e) {


            }


        }



    }


/***
 * 从零手写一个链表
  */
class testDate{

    public static void main(String[] args) {

            System.out.println(System.currentTimeMillis());
            long l = System.currentTimeMillis();
            //获取一个他的单例对象
            Calendar cal=Calendar.getInstance();
            Date date=new Date(0);
            //调用的就是新建一个Date的方法
            cal.getTime();
            cal.setTime(new Date());
            //Date 和Calender的区别和用法
            cal.get(1);
            cal.get(Calendar.MONTH);
            System.out.println(date);
            //给他一个初始容量
            HashMap<Object, Object> map = new HashMap<>(2);
            Set<Map.Entry<Object, Object>> entries = map.entrySet();
            Set<Object> objects1 = new HashSet<>();
            objects1.iterator();;
            new HashSet<String>();
            new HashMap<String,String>();
            //时间戳
            System.currentTimeMillis();
        }
    }

       // [ 内容 —+下一个节点]
    //从零去写一个链表


//链表的->节点
@Data
class Node{
    //每一个链表的元素，这个
    String name;
    //数据本身
    Object data;

    //下一个元素全部
    Node next;

    public Node(String name,Object data,Node next){
        this.name=name;
        this.data=data;
        this.next=next;
    }


    //是否存在下一个元素
    public boolean hasNext(){

        if(null !=next){
            return false;
        }else{
            return true;
        }
        }
}



//链表
class LinkList{
    //头节点
    private Node head;



    public boolean isEmpty(){
        if (null == head) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 增加节点
     */
    public boolean insertData(Object data) {

        //如果链表的头节点是空的，首先创建一个首节点
        if(isEmpty()){
            head=new Node("first",data,null);
        }else{
            Node temp=head;
            //判断是否存在下一个元素
            while (temp.hasNext()){
                //一直/如果为真
                temp = temp.getNext();//获取到最后一个元素
            }
            temp.setNext(new Node("end",data,null));

        }
    return true;
    }




}


 class listUpdateTest{

    public static void main(String[] args) {

        List<String> list=new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");


        for (String s : list) {
            list.remove("a");
        }


        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            if(iterator.next().equals("a")){
                iterator.remove();
            }
        }

        String a="213";
        switch (a){
            case "222":
                System.out.println("1");
                break;
            case "4444":
                System.out.println("2");
                break;
            case "333":
                System.out.println("3");
                break;
        }
    }







}


/**
 * 多线程
 *
 * 经典线程问题：买票
 */
class duoxiancTest{
    //这是一个主线程，

    public static void main(String[] args) {
        //开辟一个 xianA的附线程，主线程结束了，但是附线程并不会消失
        System.out.println("11111111");
        xianA xianA = new xianA();
        xianA.start();
        //设置线程的优先级
        xianA.setPriority(1);
        new Thread(){//创建匿名子类
        //相当于实现Runnable接口

            @Override
            public void run() {
                super.run();
            }
        };
        xianB xianB=new xianB();

        //创建 线程类对象
        Maipiao mp0=new Maipiao();
        //实际上他是去执行的mp锁继承的Thread的Runnale接口
        Thread thread=new Thread(mp0);
        Thread thread1=new Thread(mp0);
        Thread thread2=new Thread(mp0);
        thread.start();//开启线程
        thread1.start();
        thread2.start();
        //
    }
}



class Maipiao implements  Runnable  {

    //把需要一块操作的数据共享一下
    public  int i=50;
    Object obj=new Object();
   //没看明白

    @Override
    public void run(){

        synchronized(obj){
            //需要同步的地方判断也需要同步
        while(i>0){
                try {
                    Thread.sleep(10);
                    System.out.println("当前票数："+i);
                    --i;
                } catch (InterruptedException e) {
                }
            }
        }
    }

}


class xianB implements Runnable{
    @Override
    public void run() {

    }


}



//一个线程其实就是一个类，线程类
class xianA extends Thread{
    @Override
    public void run() {
        try {
            sleep(2000);
            System.out.println(currentThread().getName());
        } catch (InterruptedException e) {
        }
    }
}


class TimeLineMain{

    //主时间线
    public static void main(String[] args) {
        //开启时间线
        DiaLog diaLog=new DiaLog("1111");
        DiaLog diaLog1=new DiaLog("2222");
        DiaLog diaLog2=new DiaLog("3333");
        List<DiaLog> diaLogList=new ArrayList<>();
        diaLogList.add(diaLog);
        diaLogList.add(diaLog1);
        diaLogList.add(diaLog2);

        DialogPop dialogPop=new DialogPop();
        dialogPop.setList(diaLogList);

        Thread thread=new Thread(dialogPop);
        Thread thread1=new Thread(dialogPop);
        Thread thread2=new Thread(dialogPop);
        thread.start();
        thread1.start();
        thread2.start();






    }

}


//时间线A
class  TimeLineA implements Runnable{

    int i=5;

    @Override
    public void run() {

        while(i>0){
            i--;
            System.out.println("时间线A："+i);

        }
    }
}


@Data
class DiaLog{
    String txt;
    int type;

    public DiaLog(String txt){
        this.txt=txt;
    }



}



//时间线B
class TimeLineB implements Runnable{

    int i=5;
    @Override
    public void run() {
        while(i>0){
            i--;
            System.out.println("时间线B："+i);
        }
    }


}



class DialogPop implements  Runnable{
    List<DiaLog> list;


    public void setList(List<DiaLog> list) {
        this.list = list;
    }

    public List<DiaLog> getList() {
        return list;
    }

    @Override
    public void run() {

        Thread nowThread = Thread.currentThread();
        //当前的对象加入同步代码块
        //加入同步了，所以其他对象必须等次独享操作完成


            for (DiaLog diaLog : list) {
                //唤醒线程
                /**
                 * 开始动画
                 */
                System.out.println(" "+nowThread.getName()+":"+diaLog.getTxt());

/**
 *
 * 双层 SUBSTRING_INDEX（），外层为了获取最后一个 内层为了分割字符
 * 内存经历的循环：1，2，3   第一次：取到1，第二次取到1，2，第三次取到1，2，3，这样每次获取最后一个
 *
 * SELECT parent_ids,SUBSTRING_INDEX(SUBSTRING_INDEX(parent_ids,'/',help_topic_id+1),'/',-1)
 * FROM
 * sys_dept,mysql.help_topic
 * //手动构建一个循环 很巧妙，将分隔符替换为空，这样长度的差 就是需要循环的次数
 * where help_topic_id<= LENGTH(parent_ids)-LENGTH(REPLACE(parent_ids,'/',''))
 * and id='0506179272bd4078b980c304a97cd99b'
 *
 */


        }
    }
}


class  A{

    public static void main(String[] args) {

        Integer i1=20;
        Integer i2=20;
        Integer j1=1500;
        Integer j2=1500;
        System.out.println("i:"+(i1==i2));
        System.out.println("j:"+(j1==j2));

    }

}


/**
 * 迭代器设计模式
 * 至少存在两个方法  1.检测是否存在下一个元素，2.获取下一个对象
 */

class IteratorTest{


    //迭代器的抽象类
    interface Aggregate<T>{
        /**
         * 获取迭代器
         * @return
         */
        Iterator<T> iterator();
    }

    //接口
    interface Iterator<T>{
        //检测是否存在下一个元素
        boolean hasNext();
        //获取下一个元素
        T next();
    }

    //需要植入迭代器的对象
    class  Age implements  Aggregate<String>{

        private final String[] designDemos = new String[10];

        public Age() {
            // 初始化数据集
            for (int i = 0; i < designDemos.length; i++) {
                designDemos[i] = String.valueOf(i);
            }
        }

        //获取该对象的长度
        public int getLength(){
           return   designDemos.length;
        }


        //获取该对象的迭代器
        @Override
        public Iterator<String> iterator() {
            //返回age的迭代器对象
            return new AgeIterator(this);
        }

        public String get(int index){
            // TODO : 边界校验
            return designDemos[index];
        }

    }


    /**
     * 迭代器的具体实现
     */
    class AgeIterator implements  Iterator{
        private int index = 0;

        private Age age;

        public AgeIterator(Age age){
            this.age=age;
        }


        @Override
        public boolean hasNext() {
            return index < age.getLength() ;
        }

        @Override
        public Object next() {
            //每次获取下一个对象的时候，下标+1
            index+=1;
            return age.get(index);
        }


    }



}


/**
 * 消费者
 */
class  ConsumerTest{


    /**
     * 构建一个方法。传入一个消费者
     * @param a
     * @param consumer
     */
    public static void consumer(String a, Consumer<String> consumer){
        //该消费者去消费a 就是把每一个文本都
        consumer.accept(a);
    }

    public static void main(String[] args) {
        String xf="需要消费的文本";
                    //Consumer是一个函数式接口，直接调用内部的方法
        consumer(xf,(String name)->{
            //将字符串反转
            String reName = new StringBuffer(name).reverse().toString();
            System.out.println(reName);
        });
        System.out.println(xf);

        new ClassA().consumer(str->{
            //处理列表中的每一个值
        });
    }

}



class ClassA{
    List<String> strList;
    String name;
    public  void consumer(Consumer<String> consumer){
        //直接去消费这个类里面的name属性
        for (String str: strList){
            consumer.accept(str);
        }
    }

}


/**
 * 迭代器的设计模式
 */
class ForTest{


    public static void main(String[] args) {
        List<String> strings=new ArrayList<>();


        for (String string : strings) {
            strings.remove(string);
        }
        Iterator<String> iterator = strings.iterator();

        //内部：  小标完全交由第三方控制
        while(iterator.hasNext()){
            String next = iterator.next();
            iterator.remove();
        }

        for (int i = strings.size(); i < strings.size() ; i--) {
            strings.remove(i);
        }
        //------------

        String str="abc";
        switch (str){
            case "a":
                System.out.println("111");
                break;
            case "b":
                System.out.println("222");
                break;
        }
        String a=str+"";
    }
}


class MybatisTest{

    public static void main(String[] args) throws IOException {
        //mybatis的加载
        String configName = "mybatis_config.xml";
        Reader reader = null;
        try {
            //获取资源文件名称
            reader = Resources.getResourceAsReader(configName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


}


 class a123{


    public static void main(String[] args) throws IOException {

        //只会在项目开启的时候，mybatis，会去读取配置文件，根据配置文件，构建一个会话工厂
        //1. 读取mybatis-config.xml 文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        //2. 构建SqlSessionFactory(创建了DefaultSqlSessionFactory)
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //3. 打开SqlSession  根据会话工程船舰会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //根据会话 执行sql，底层的sql就这几个，所有的mybatis可以执行的sql
        sqlSession.selectOne("1111");

        //4. 获取Mapper 接口对象
        KcbPdMxMapper mapper = sqlSession.getMapper(KcbPdMxMapper.class);

        //5. 获取mapper 接口对象的方法操作数据库
        List<KcbPdMx> list = mapper.findList();
        System.out.println("查询结果为：" + list.size());
    }


 }


/**
 * 抽象类的方法
 */
interface IInterest {
    int lambda(int a, int c);
}

/**
 * 普通的类调用，用一个类继承接口
 */
class Interest implements IInterest {
    @Override
    public int lambda(int a, int c) {
        System.out.println("外部类调用-->" + (a + c));
        return 0;
    }
}


/**
 * lamada表达式的本质
 */

class a213{
    public static void main(String[] args) {


        //	匿名内部类 设置一个匿名的内部类
        IInterest interest3 = new IInterest() {
            @Override
            public int lambda(int a, int c) {
                System.out.println("匿名内部类调用-->" + (a + c));
                return (a + c);
            }
        };


        //lamada表达式，就是直接去实现的这个接口,接口时没有具体的实现的
        IInterest  interest=(int a,int c) -> {
            System.out.println("11122233333");
            return  (a+c);
        };

        IInterest interest1=(a,b)->{
            return (a+b);
        };

        FinalMode finalMode;

      //  System.out.println(finalMode.toString());
    }


    final class FinalMode{
        String a="123";
        String a1="123";
        String a2="123";

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getA1() {
            return a1;
        }

        public void setA1(String a1) {
            this.a1 = a1;
        }

        public String getA2() {
            return a2;
        }

        public void setA2(String a2) {
            this.a2 = a2;
        }

        @Override
        public String toString() {
            return "FinalMode{" +
                    "a='" + a + '\'' +
                    ", a1='" + a1 + '\'' +
                    ", a2='" + a2 + '\'' +
                    '}';
        }
    }
}





