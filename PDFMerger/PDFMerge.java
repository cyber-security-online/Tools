import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PDFMerge {

	/**
	 * Creates a list with all PDF documents in a specific directory.
	 * 
	 * @return List with all PDFs in a specific directory.
	 * @throws IOException 
	 */
	public static List<String> get_pdf_files(final String doctype) throws IOException {
		// List containing the names of all files of doctype.
		final List<String> file_list = new ArrayList<>();
		
		// Current folder.
		final File folder = new File(new java.io.File(".").getCanonicalPath());
		
		// Get a list of all files in the current folder.
		final File[] files = folder.listFiles();

		// Filter all files after the doctype.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile() && files[i].getName().contains("." + doctype) && !files[i].getName().contains("merge." + doctype)) {
				file_list.add(files[i].getName());
			}
		}
		return file_list;
	}
	
	public static void delete_files(final Set<String> to_delete) throws IOException {
		
		// Current folder.
		final File folder = new File(new java.io.File(".").getCanonicalPath());
		
		// Get a list of all files in the current folder.
		final File[] files = folder.listFiles();

		// Delete all files to delete.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile() && to_delete.contains(files[i].getName())) {
				files[i].delete();
			}
		}
	}
	
	/**
	 * Merges n PDFs given in the pdf_files list.
	 * 
	 * @param pdf_files List of files to merge.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void merge(final List<String> pdf_files) throws IOException, InterruptedException{
		// TeX Code.
		String tex_code = 
			"\\documentclass[a4paper,bibtotoc,12pt,listof=numbered]{scrartcl}\n"
		  + "\\usepackage[utf8]{inputenc}\n"
		  + "\\usepackage[T1]{fontenc}\n"
		  + "\\usepackage[ngerman]{babel}\n"
		  + "\\usepackage{longtable}\n"
		  + "\\usepackage{tocloft}\n"
		  + "\\usepackage{pdfpages}\n"
		  + "\\usepackage[onehalfspacing]{setspace}\n"
		  + "\\begin{document}";
		for (int i = 0; i < pdf_files.size(); i++) {
			tex_code += ("\\includepdf[pages={-}]{" + pdf_files.get(i) + "}\n");
		}
		
		tex_code += "\\end{document}";

		// Create TeX document.
		final PrintWriter writer = new PrintWriter("merge.tex", "UTF-8");
		writer.println(tex_code);
		writer.close();
		
		// Create a process.
		final Process process = Runtime.getRuntime().exec("cmd");
		
		new Thread(new SyncedPipe(process.getErrorStream(), System.err)).start();
		new Thread(new SyncedPipe(process.getInputStream(), System.out)).start();
		
		final PrintWriter in = new PrintWriter(process.getOutputStream());

		// Compile LaTeX document.
		in.println("pdflatex merge.tex");
		
		in.close();
		process.waitFor();
		
		// Files to delete after the PDF creation.
		Set<String> to_delete = new HashSet<>(Arrays.asList("merge.aux","merge.log","merge.tex"));
		delete_files(to_delete);
	}
}