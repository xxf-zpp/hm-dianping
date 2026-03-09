package com.hmdp;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HmDianPingApplicationTests {

    @Test
    public void RandomTest(){
        System.out.println(RandomUtil.randomNumbers(6));
    }

}
