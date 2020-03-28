package cn.net.colin.model.common;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Map;

/**
 * zTree树的实体类
 * @author sxf
 */
@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class TreeNode implements Serializable {
	private static final long serialVersionUID = 6267653369793648605L;

	private String id;			// 节点id
	private String pId;			// 节点父id
	private String name;		// 节点名称
	private String isParent;	// 该节点是否是父节点（表示该节点是否有子类），支持 "false","true" 字符串格式的数据
	private String open;		// 该节点是否展开
	private String operate; 	// 操作是否允许点击
	private String chkDisabled; //设置节点的 checkbox / radio 是否禁用
	private Map<String,Object> param;//存放临时添加的变量
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPId()
    {
        return pId;
    }
    public void setPId(String pId)
    {
        this.pId = pId;
    }
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
    public Map<String, Object> getParam()
    {
        return param;
    }
    public void setParam(Map<String, Object> param)
    {
        this.param = param;
    }

	public String getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(String chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}
