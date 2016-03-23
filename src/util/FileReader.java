package util;

import java.io.File;
import java.util.Scanner;

public class FileReader
{

	public static void main(String[] args)
	{

		// put directory path here
		String directoryPath = "C:/Users/SRIN/workspace/SRINSWTest";

		// list all java files in the given directory path
		listFilesOnFolder(directoryPath);

		// read source code
		readFileContent("C:/Users/SRIN/workspace/SRINSWTest/src/datastructure/HashMap.java");
	}

	private static void listFilesOnFolder(String directoryPath)
	{
		final File folder = new File(directoryPath);
		listFilesForFolder(folder);
	}

	private static void listFilesForFolder(final File folder)
	{
		for (final File element : folder.listFiles())
		{
			if (element.isDirectory())
			{
				listFilesForFolder(element);
			}
			else if (element.getName().endsWith(".java"))
			{
				System.out.println(element.getAbsolutePath());
			}
		}
	}

	private static void readFileContent(String filePath)
	{
		try
		{
			Scanner in = new Scanner(new File(filePath));
			while (in.hasNextLine())
			{
				System.out.println(in.nextLine());
			}
			in.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
}
