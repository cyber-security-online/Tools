import java.io.IOException;
import java.util.List;

/**
 * Main program.
 * 
 * @author André Dalwigk.
 * @version 1.0 (31.05.18)
 *
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {

		// Include all PDF documents.
		final List<String> files = PDFMerge.get_pdf_files("pdf");
		
		// Merge PDF documents.
		PDFMerge.merge(files);
	}
}
