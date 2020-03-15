package cn.net.colin.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: cn.net.colin.common.util
 * @Author: sxf
 * @Date: 2020-3-15
 * @Description: SQL处理工具类
 */
public class SQLUtil {
    /**
     * sql中in查询超过1000的处理
     * @param list
     * @param <T>
     * @return
     */
    public static <T>  List<List<T>> getSumArrayList(List<T> list){
        List<List<T>> objectlist = new ArrayList<>();
        int iSize = list.size()/1000;
        int iCount = list.size()%1000;
        for(int i=0;i<=iSize;i++){
            List<T> newObjList = new ArrayList<>();
            if(i==iSize){
                for(int j =i*1000;j<i*1000+iCount;j++ ){
                    newObjList.add(list.get(j));
                }
            }else{
                for(int j =i*1000;j<(i+1)*1000;j++ ){
                    newObjList.add(list.get(j));
                }
            }
            if(newObjList.size()>0){
                objectlist.add(newObjList);
            }
        }
        return objectlist;
    }
}
