package com.tthome.generator01;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class StaticGenerator {
    public static void main(String[] args) {
        //获取整个项目的根目录

        //获取当前项目的根目录E:\Projects\generator
        String projectPath = System.getProperty("user.dir");
        File projectFile = new File(projectPath);
        //获取上级目录E:\Projects
        //File parentFile = new File(projectPath).getParentFile();

//        System.out.println(projectPath);
//        System.out.println(parentFile);

        //输入路径：ACM示例代码模板目录
        //String inputPath = new File(projectPath,"generator-demo-projects/acm-template").getAbsolutePath();
        String inputPath = new File(projectFile,"generator-demo-projects/acm-template").getAbsolutePath();
        //输出路径：直接输出到项目的根目录
        //String outputPath = String.valueOf(parentFile);
        String outputPath = projectPath;
        copyFilesByHutool(inputPath,outputPath);


    }




    /*
    拷贝文件（Hutool实现，会将输入目录完整拷贝到输出目录下）
     */
    public static void copyFilesByHutool(String inputPath, String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }

    /*
    递归拷贝文件（递归实现，会将输入目录完整拷贝到输出目录下）
     */
    public static void copyFilesByRecursive(String inputPath, String outputPath){
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try{
            copyFilesByRecursive(inputFile,outputFile);
        }catch (Exception e){
            System.err.println("文件复制失败");
            e.printStackTrace();
        }
    }

    /*
    先创建目录，然后再遍历目录内的文件，依次复制
     */
    private static void copyFilesByRecursive(File inputFile, File outputFile) throws IOException {
        //区分文件和目录
        if (inputFile.isDirectory()){
            System.out.println(inputFile.getName());
            //获取输出地址是否有相同文件
            File destOutputFile = new File(outputFile, inputFile.getName());
            //如果是目录，先创建目录
            if(!destOutputFile.exists()){
                destOutputFile.mkdirs();
            }
            //获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            //无子文件则直接结束
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            //递归到下一层文件拷贝
            for(File file:files){
                copyFilesByRecursive(file,destOutputFile);
            }
        }else{
            //是文件，直接复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
