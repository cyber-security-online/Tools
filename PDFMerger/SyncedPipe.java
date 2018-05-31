import java.io.InputStream;
import java.io.OutputStream;

/**
 * Synced Pipe.
 * 
 * @author André Dalwigk
 * @versoin 1.0 (31.05.18)
 *
 */
class SyncedPipe implements Runnable {
	
	/**
	 * The OutputStream.
	 */
	private final OutputStream os_;
	
	/**
	 * The InputStream.
	 */
	private final InputStream is_;
	
	/**
	 * Custom Ctor.
	 * @param is The InputStream.
	 * @param os The OutputStream.
	 */
	public SyncedPipe(final InputStream is, final OutputStream os) {
		is_ = is;
		os_ = os;
	}

	@Override
	public void run() {
		try {
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = is_.read(buffer)) != -1;) {
				os_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}