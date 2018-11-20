package com.czxy.dept.util;

import java.util.Random;

public class GetRandomCodeUtil {

    /**
     * 随机生成4位验证码
     * @return
     */
    public static String getNumber(){
        int number;//定义两变量
        Random ne = new Random();//实例化一个random的对象ne
        number = ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        // System.out.println("产生的随机数是:"+number);//输出
        return String.valueOf(number);
    }

    /**
     * 生成随机密码
     * @return
     */
    public static String randomPassword(){
        int length = 6;

        String val = "";
        Random random = new Random();
        //length为几位密码
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void main(String[] args) {
        System.out.println(randomPassword());
    }
}
