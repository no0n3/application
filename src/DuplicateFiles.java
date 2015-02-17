import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DuplicateFiles {

	public static void main(String[] args) throws Exception {
		Set<CustFile> filesWithoutDuplicate = listDuplicatingFiles("D:\\f1");
		System.out.println(filesWithoutDuplicate);
	}

	static Set<CustFile> listDuplicatingFiles(String dir) throws Exception {
		Set<CustFile> filesWithoutDuplicate = new HashSet<CustFile>();

		if(null != dir) {
			listDuplicatingFiles(new File(dir), filesWithoutDuplicate);
		}

		return filesWithoutDuplicate;
	}

	private static void listDuplicatingFiles(File file,
			Set<CustFile> filesWithoutDuplicate) throws Exception {
		if (isValidFile(file)) {
			if (file.isDirectory()) {
				for (File childFile : file.listFiles()) {
					listDuplicatingFiles(childFile, filesWithoutDuplicate);
				}
			} else {
				CustFile newFile = new CustFile(file);
				filesWithoutDuplicate.add(newFile);
			}
		}
	}

	private static boolean isValidFile(File file) {
		return null != file && file.exists();
	}

	private static class CustFile {
		private File file;

		CustFile(File file) {
			if (null == file) {
				throw new RuntimeException("Invalid file.");
			}
			this.file = file;
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(getFileCont(this.file));
		}

		@Override
		public boolean equals(Object otherObj) {
			if (null == otherObj) {
				return false;
			}
			File otherFile;
			if (otherObj instanceof File) {
				otherFile = (File) otherObj;
			} else if (otherObj instanceof CustFile) {
				otherFile = ((CustFile) otherObj).file;
			} else {
				return false;
			}
			try {
				byte[] thisFileBytes = getFileCont(this.file);
				byte[] otherFileBytes = getFileCont(otherFile);
				return areEqualBytes(thisFileBytes, otherFileBytes);
			} catch (Exception e) {
			}
			return false;
		}

		private static boolean areEqualBytes(byte[] b1, byte[] b2) {
			if (null == b1 || null == b2) {
				return false;
			}
			if (b1.length != b2.length) {
				return false;
			}
			for (int i = 0; i < b1.length; i++) {
				if (b1[i] != b2[i]) {
					return false;
				}
			}
			return true;
		}

		private static byte[] getFileCont(File file) {
			try {
				FileInputStream in = new FileInputStream(file);
				byte[] bytes = new byte[in.available()];

				while (-1 != (in.read(bytes, 0, bytes.length)))
					;

				in.close();

				return bytes;
			} catch (Exception e) {
				return new byte[0];
			}
		}

		@Override
		public String toString() {
			return this.file.getName();
		}
	}

}
