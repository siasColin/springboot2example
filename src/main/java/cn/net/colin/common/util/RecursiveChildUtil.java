package cn.net.colin.common.util;

import cn.net.colin.model.common.TreeNode;

import java.util.List;

/**
 * @Package: cn.net.colin.common.util
 * @Author: sxf
 * @Date: 2020-3-15
 * @Description: 递归得到所有子节点
 */
public class RecursiveChildUtil {
    /**
     * 递归筛选所有子地区
     * @param pAreaCode 父级地区编码
     * @param treeNodeList 所有地区节点集合
     * @param childList 子地区集合
     * @return
     */
    public static void areaTreeChildRecursive(String pAreaCode, List<TreeNode> treeNodeList, List<TreeNode> childList) {
        for (TreeNode treeNode : treeNodeList) {
            if(treeNode.getPId().equals(pAreaCode)){
                childList.add(treeNode);
                areaTreeChildRecursive(treeNode.getId(),treeNodeList,childList);
            }
        }
    }

    /**
     * 递归筛选所有子机构
     * @param pOrgCode 父级机构编码
     * @param treeNodeList 所有机构节点集合
     * @param childList 子机构集合
     * @return
     */
    public static void orgTreeChildRecursive(String pOrgCode, List<TreeNode> treeNodeList, List<TreeNode> childList) {
        for (TreeNode treeNode : treeNodeList) {
            if(treeNode.getPId().equals(pOrgCode)){
                childList.add(treeNode);
                orgTreeChildRecursive(treeNode.getId(),treeNodeList,childList);
            }
        }
    }
}
