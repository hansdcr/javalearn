package st08_semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * 1、 记录开始时间
 * 2、 汽车来到停车场门口
 * 3、 是否有空余车位
 * 4、 进入停车场
 * 5、 停车一段时间
 * 6、 离开停车场
 */
public class ParkCar {
    // 10个车位的停车场
    // 来了500量汽车
    //   获取到达停车场时间
    //   等待车位
    //   进入停车场
    //   记录等待时间
    //   离开停车场

    public static void main(String[] args) {
        //模拟随机停车时长
        final Random  random =  new Random();
        // 模拟10个车位的停车厂
        final Semaphore parkingSystem = new Semaphore(10);


        for(int i=0;i<500;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 获取到达停车厂的时间
                    Long startWaitTime = System.currentTimeMillis();
                    System.out.println("第"+(finalI +1)+"辆车在排队");
                    // 等待获取资源
                    try{
                        parkingSystem.acquire();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    // 记录等待时间（当前时间 - 到达停车场时间)
                    Long waitingTime = (System.currentTimeMillis() - startWaitTime)/1000;
                    System.out.println("第"+(finalI +1)+"辆车等待"+ waitingTime + "秒后进入停车场");
                    // 模拟停车时间
                    int parkingTime = random.nextInt(10)+2;
                    try {
                        TimeUnit.SECONDS.sleep(parkingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 离开停车场，释放资源
                    parkingSystem.release();
                    System.out.println("第"+(finalI +1)+"辆汽车停车"+parkingTime+"毫秒离开车库");

                }
            }).start();
        }
    }
}
