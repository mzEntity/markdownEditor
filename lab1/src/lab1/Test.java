package lab1;

import lab1.memento.Retainable;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello Markdown!");
        Apple apple = new Apple();
        apple.setName("banana");
        apple.setPrice(999);
        String filePath = "apple.ser";
        try {
            // 创建文件输出流
            FileOutputStream fileOut = new FileOutputStream(filePath);

            // 创建对象输出流
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            // 将对象写入输出流
            objectOut.writeObject(apple);

            // 关闭输出流
            objectOut.close();

            System.out.println("对象已成功保存到文件中。");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 创建一个 ObjectInputStream 对象来读取序列化数据
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // 从输入流中读取序列化数据并还原对象
            Object obj = in.readObject();

            // 关闭输入流
            in.close();
            fileIn.close();

            // 将还原的对象分配给合适的变量或引用，然后可以使用它
            Apple restoredObj = (Apple) obj;
            System.out.println("Restored Object: " + restoredObj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Apple implements Retainable{
    private String name;
    private long price;

    public Apple(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public Apple() {
        this.name = "apple";
        this.price = 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}