package com.zmg.panda.utils;

import java.io.*;

public class ProcessUtil {

    public void runCommands(String command) {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        command = "java -jar D:\\code\\panda\\target\\panda-0.0.1-SNAPSHOT.jar";
        try {
            process = runtime.exec(command);
            // 后面那个目录是，程序运行时候所需要在的目录
//            process = runtime.exec(command, null , new File("D:\\tmp"));
            InputStream input = process.getInputStream();
            InputStream error = process.getErrorStream();
            System.out.println("运行日志如下：");
            readRunLogs(input);
            System.out.println("错误日志如下：");
            readRunLogs(error);
            process.waitFor();
            process.destroy();
            System.out.println("程序运行结束！");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                process.getInputStream().close();
                process.getOutputStream().close();
                process.getErrorStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
//
//    private void readRunLogsThread(InputStream input) {
//        new Thread(()->{
//            BufferedReader bufferedReader = null;
//            try {
//                bufferedReader = new BufferedReader(new InputStreamReader(input, "gbk"));
//                String line = null;
//                while ((line = bufferedReader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public void readRunLogs(InputStream input) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(input, "gbk"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
