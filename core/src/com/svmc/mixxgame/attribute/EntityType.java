package com.svmc.mixxgame.attribute;

public enum EntityType {
	CIRCLE("circle"), RECTANGLE("rectangle"), POLYGON("polygon"), POLYLINE(
			"polyline");

	String	code	= "";

	private EntityType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static EntityType getEntityType(String entityType) {
		if (entityType.equalsIgnoreCase(CIRCLE.getCode()))
			return CIRCLE;
		if (entityType.equalsIgnoreCase(RECTANGLE.getCode()))
			return RECTANGLE;
		if (entityType.equalsIgnoreCase(POLYGON.getCode()))
			return POLYGON;
		if (entityType.equalsIgnoreCase(POLYLINE.getCode()))
			return POLYLINE;
		return null;
	}

}
