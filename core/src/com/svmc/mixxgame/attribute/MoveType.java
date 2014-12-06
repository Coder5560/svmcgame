package com.svmc.mixxgame.attribute;

public enum MoveType {

	/*
	 * - Parameter :
	 * 
	 * rotate(float) : degree of rotation. default = 1f. originX (float) :
	 * default = center. originY (float) : default = center
	 */

	ROTATE("move_rotate"),

	/*
	 * - Parameter :
	 * 
	 * rotate(float) : degree of rotation. default = 1f. 
	 * 
	 * originX (float) : default = center 
	 * 
	 * originY (float) : default = center
	 */
	HARMONIC("move_harmonic"),

	;

	String	code	= "";

	private MoveType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static MoveType getMoveType(String type) {
		if (type.equalsIgnoreCase(ROTATE.getCode()))
			return ROTATE;
		if (type.equalsIgnoreCase(HARMONIC.getCode()))
			return HARMONIC;
		return null;
	}

}
