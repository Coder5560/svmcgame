package utils.listener;

public interface OnClickListener {
	public static int	NONE	= 0;
	public static int	UP		= 1;
	public static int	DOWN	= 2;

	public void onClick(int count);
}
