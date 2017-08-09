package org.bumishi.admin.domain.modle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by xieqiang on 2016/9/17.
 * 菜单
 */
@Entity
public class Menu{
	    @Id
	    private String id;

	    private String label;

	    private String path="0";  //父节点的路径与父节点的id路径，用","分开，0表示父节点是根节点
        
	    private int viewOrder=1;  //排序

	    private int type;//扩展字段。菜单类型，供不同业务区分

	    private String style;//样式，方便ui展现
	    
	    @Transient
	    public List<? extends Menu> childNodes=new ArrayList<>();

	    /** 
	     * 状态 是否禁用
	     */
	    private boolean disabled;
	    

	    /**
	     * 链接
	     */
	    private String url;

	    /**
	     * 父标题
	     */
	    @Transient
	    private String parentTitle;
	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getLabel() {
	        return label;
	    }

	    public void setLabel(String label) {
	        this.label = label;
	    }

	    public String getPath() {
	        return path;
	    }

	    public void setPath(String path) {
	        this.path = path;
	    }

		public int getViewOrder() {
			return viewOrder;
		}

		public void setViewOrder(int viewOrder) {
			this.viewOrder = viewOrder;
		}

		public boolean isDisabled() {
	        return disabled;
	    }

	    public void setDisabled(boolean disabled) {
	        this.disabled = disabled;
	    }

	    public String getStyle() {
	        return style;
	    }

	    public void setStyle(String style) {
	        this.style = style;
	    }

	    public int getType() {
	        return type;
	    }

	    public void setType(int type) {
	        this.type = type;
	    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    /***
     * 以level==1的节点作为开始节点构建树结构
     * @param nodes
     * @return
     */
    public static List<? extends Menu> buildTree(List<? extends Menu> nodes){
        if (isEmpty(nodes)){
            return null;
        }
        List<? extends Menu> firstLevels=nodes.stream().filter(node->!node.isDisabled() && node.getLevel()==1).collect(Collectors.toList());
        sortByOrder(firstLevels);
        firstLevels.stream().forEach(node-> setChildren(node,nodes));
        return firstLevels;
    }
    
    private static boolean isEmpty(List nodes) {
        return nodes == null || nodes.isEmpty();
    }
    
    private static void sortByOrder(List<? extends Menu> firstLevels) {
        firstLevels.sort((node1,node2)->Integer.valueOf(node1.getViewOrder()).compareTo(Integer.valueOf(node2.getViewOrder())));
    }
    

    private static  void setChildren(Menu currentNode, List<? extends Menu> nodeList){
        List<? extends Menu> childrens=nodeList.stream().filter(node->(!node.isDisabled() && node.getPath().equals(currentNode.getPath()+","+currentNode.getId()))).collect(Collectors.toList());
        currentNode.childNodes=childrens;
        if (isEmpty(childrens)){
            return;
        }
        sortByOrder(childrens);
        childrens.stream().forEach(node-> setChildren(node,nodeList));

    }
    
    public int getLevel() {
        if(path==null){
            return 1;
        }
        return path.split(",").length;
    }
    
    /***
     * 按数结构给节点排序
     * @param nodes
     */
    public static void sortByTree(List<? extends Menu> nodes) {
        if(isEmpty(nodes)){
            return;
        }
        sortByOrder(nodes);
        nodes.sort((o1, o2) -> (o1.getPath()+","+o1.getId()).compareTo(o2.getPath()+","+o2.getId()));
    }

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	public List<? extends Menu> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<? extends Menu> childNodes) {
		this.childNodes = childNodes;
	}
	

}
