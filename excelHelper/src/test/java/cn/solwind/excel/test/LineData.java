package cn.solwind.excel.test;

import cn.solwind.excel.annotation.Cell;
import cn.solwind.excel.annotation.Title;

/**
* @author chfenix
* @version 创建时间：2019-05-07
*
* 示例数据
*/

public class LineData {

	@Title(value = "ID", fontFamily = "微软雅黑", background = Cell.COLOR_GREY_25_PERCENT, bold = true, 
			border = {Cell.BORDER_BOLD_LEFT, Cell.BORDER_BOLD_TOP, Cell.BORDER_BOLD_RIGHT,Cell.BORDER_THIN_BOTTOM })
	@Cell(col="A",width=5,height=20)
	private Long id;

	@Title(value="名称", bold = true, fontFamily = "微软雅黑",background=Cell.COLOR_GREY_25_PERCENT,
			border = {Cell.BORDER_BOLD_LEFT, Cell.BORDER_BOLD_TOP, Cell.BORDER_BOLD_RIGHT,Cell.BORDER_THIN_BOTTOM })
	@Cell(col = "C", width = Cell.WIDTH_AUTO, fontSize = 10, fontFamily = "微软雅黑", color = Cell.COLOR_RED, align = Cell.ALIGN_CENTER, bold = true)
	private String name;
	
	@Title(value="年龄", fontFamily = "微软雅黑",background=Cell.COLOR_GREY_25_PERCENT, bold = true,
			border = {Cell.BORDER_BOLD_LEFT, Cell.BORDER_BOLD_TOP, Cell.BORDER_BOLD_RIGHT,Cell.BORDER_THIN_BOTTOM,})
	@Cell(col="D",width=50,align=Cell.ALIGN_CENTER,color=Cell.COLOR_BLUE)
	private Integer age;
	
	@Title(value="国籍", fontFamily = "微软雅黑",background=Cell.COLOR_GREY_25_PERCENT, bold = true,
			border = {Cell.BORDER_BOLD_LEFT, Cell.BORDER_BOLD_TOP, Cell.BORDER_BOLD_RIGHT,Cell.BORDER_THIN_BOTTOM })
	@Cell(col="E",value="中国",color=Cell.COLOR_BLUE)
	private String nation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
}
