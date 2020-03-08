package cn.net.colin.common.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Package: cn.net.colin.common.util
 * @Author: sxf
 * @Date: 2020-3-7
 * @Description: 获取目录工具类
 */
public class GetServerRealPathUnit {
    public static String  getPath(String subdirectory){
        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
        File upload = null;
        try {
            //本地测试时获取到的是"工程目录/target/upload/subdirectory
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            upload = new File(path.getAbsolutePath(),subdirectory);
            if(!upload.exists()) upload.mkdirs();//如果不存在则创建目录
            String realPath = upload + "/";
            return realPath;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取服务器路径发生错误！");
        }
    }

    public static void main(String[] args) {
        String p = File.separator;
        System.out.println(GetServerRealPathUnit.getPath("static"+p+"uploadfile"));
    }
}
