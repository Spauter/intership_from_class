package score.UI;

import javax.swing.JOptionPane;

/**
 * 用动态数组存储的学生数据：
 * <P/>
 * 1.数组缺陷：(1)数组长度一经定义不能再被修改；(2)同一数组中数据类型只能一致
 * <P/>
 * 2.解决：(1)arrayCode()//数组拷贝——扩容；(2)Object可以存储任意类型 data[][] {@code Object}
 * 
 * @author Bloduc_Spauter
 * 
 */
public class Students implements Students_list {
	protected Object[][] data;// 存储数据的数组，用来进行运算排序的
	protected int cols;// 列
	protected int rows;// 行
	Students next;

	protected Students(String string) {
		this.data = new Object[10][6];
		this.rows = 0;
		this.cols = 0;
		this.information=string;
	}


	private String information;
	private Students head = null;


	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	

	public boolean isEmpty() {// 判断该动态数组是否为空
		return this.rows == 0;
	}

	public boolean add(Object data) {// 向数组添加数据
		if (this.rows + 1 == this.data.length) {// 扩容
			Object[][] newArray = new Object[this.data.length * 2][6];// 空间扩大
			System.arraycopy(this.data, 0, newArray, 0, this.data.length);// 数据复制
			this.data = newArray;// 将data指向新的数组
		}
		if (cols == 6) {
			rows++;
			cols = 0;
		}
		this.data[rows][cols] = data;
		cols++;
		return true;
	}

	/**
	 * 输入行数，取得指定的行值
	 * 
	 * @param cols
	 * @return
	 */
	public Object getRows(int rows) {
		cols--;
		String infos = "";
		if (rows < 0 || rows >= this.rows) {
			return "";
		}
		for (int i = 0; i < 6; i++) {
			if (i < 6) {
				infos = infos + data[rows][i] + "\t";
			}
			if (i == 6) {
				infos = infos + "\n";
			}
		}
		return infos;
	}

	/**
	 * 输入列数，获取当前列值
	 * 
	 * @param cols
	 * @return
	 */
	public Object getCols(int cols) {
		cols--;
		String info = "";
		if (cols < 0 || cols >= 6) {
			System.out.println();
			return "";
		}
		for (int i = 0; i <= this.rows; i++) {
			info = info + data[i][cols] + "\t";
		}
		return info;
	}

	/**
	 * 删除指定的行数
	 * 
	 * @param rows
	 * @return
	 */
	public boolean remove(int rows) {// 删除指定位置元素
		if (this.rows + 1 < this.data.length / 2) {// 缩容
			Object[][] newArray = new Object[this.data.length / 2][6];
			System.arraycopy(this.data, 0, newArray, 0, this.data.length);// 数据复制
			this.data = newArray;// 将data指向新的数组
		}
		if (rows < 0 || rows > this.rows) {
			return false;
		}
		System.arraycopy(this.data, rows, this.data, rows - 1, this.data.length - rows);
		this.rows--;
		return true;
	}

	/**
	 * 用到顺序查询（遍历第一列数组） 检查是否有重复，如果有返回false，没有返回 true
	 * <P/>
	 * suid {@code String} containing a {@code boolean}
	 * 
	 * @param suid
	 * @return true or false
	 */
	public boolean HasSame(String suid) {
		if (data[0][0] == null) {
			return true;
		}
		for (int i = 0; i < rows + 1; i++) {
			if (suid.equals(data[i][0].toString())) {
				return false;
			}
		}
		return true;
	}

	public String turnIntoList(String uid, String name, String Math, String English,String Cmoputer, String avg) {
			information =(uid + "\t" + name + "\t" + Math + "\t" + English +"\t"+Cmoputer+ "\t" + avg);
			return information;
	}

	
	/**
	 * 
	 * 以下是单链表部分，只负责增减两操作
	 */
	@Override
	public void listadd(Students node) {
		if (head == null) {
			head = node;
			return;
		}
		Students temp = head;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = node;
	}

	@Override
	public void printList() {
		Students temp = head;
		if (head == null) {
			JOptionPane.showMessageDialog(null,"链表为空,可能需要刷新","提示",JOptionPane.PLAIN_MESSAGE);
			return;
		}
		while (temp != null) {
			System.out.println(temp.information);
			temp = temp.next;
		}

	}

	@Override
	public boolean deleteIndexNode(int index) {
		{
			Students preDeleteNode = head; // 记录被删除节点前一个节点
			Students deleteNode = preDeleteNode.next; // 记录被删除节点
			if (index == 1 && head != null) {
				head = deleteNode;
				preDeleteNode = null; // 设置被删除对象为null，垃圾回收
				return true;
			} else {
				while (deleteNode != null) {
					if (index - 1 == 1) {
						preDeleteNode.next = deleteNode.next;
						deleteNode = null; // 设置被删除对象为null，垃圾回收
						return true;
					}
					preDeleteNode = preDeleteNode.next;
					deleteNode = deleteNode.next;
					index--;
				}
				return false;
			}
		}

	}

	@Override
	public int listLength() {
	    {
	        Students temp = head;
	        int length=0;
	        while(temp!=null)
	        {
	            length++;
	            temp=temp.next;
	        }
	        return length;
	    }
	}
}
