package com.pandar.common.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum DataKindEnum {
	BYTE(1, "byte"),
	BOOLEAN(2, "bool"),
	INT(3, "int"),
	DOUBLE(4, "double"),
	STRING(5, "string"),
	FLOAT(6, "float"),
	LONG(7, "long"),
	BYTE_ARRAY(8, "byte[]"),
	BOOLEAN_ARRAY(9, "bool[]"),
	INT_ARRAY(10, "int[]"),
	DOUBLE_ARRAY(11, "double[]"),
	FLOAT_ARRAY(12, "float[]"),
	LONG_ARRAY(13, "long[]"),
	SHORT(14,"short"),
	DATE_TIME(15,"DateTime"),
	SHORT_ARRAY(16,"short[]"),
	UINT(17, "uint"),
	DTL_DATE_TIME(18,"dltdatetime");

	private String des;

	private Integer code;

	DataKindEnum(Integer code, String des) {
		this.des = des;
		this.code = code;
	}
	//获取所有的des
	public static List<String> getAllDes(){
		List<String> desList = new ArrayList<>();
		for (DataKindEnum myEnum : DataKindEnum.values()) {
			desList.add(myEnum.getDes());
		}
		return desList;
	}
}

