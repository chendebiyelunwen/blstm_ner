package experiment;

import java.io.File;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class Segment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//�ִ�21-28��31-38������������һֱ��98
		/*for(int i=2;i<=9;i++)
		{
			for(int j=1;j<=8;j++)
			{
				try{
					segment2Words("data\\raw_data\\sogouCA_2\\news.allsites."+String.valueOf(i)+j+"0806.txt","data\\word_data\\sogouCA\\news.allsites."+String.valueOf(i)+j+"0806.txt");
					}catch(Error e)
					{
						System.out.println(i+" "+j+"hahahhhah");
						
					}
			}
			
		}
		System.out.println("after    hahahhhah");*/
		segment2Words_All("data/raw_data/sogouCA_2","data/word_data/sogouCA");
		//segment2Words("data\\raw_data\\fish\\fishAll.txt","data\\word_data\\fish\\fishAll.txt");
		
	}
	/******
	 * ��·���µ������ļ��ִ�
	 * @param srcPath
	 * @param desPath
	 */
	public static void segment2Words_All(String srcPath,String desPath)
	{
		File[] files = new File(srcPath).listFiles();  
		for (File file : files) 		{
			
			segment2Words(srcPath+"/"+file.getName(), desPath+"/"+file.getName());
            
        }	
	}
	
	// ����ӿ�CLibrary���̳���com.sun.jna.Library
			public interface CLibrary extends Library {
				// ���岢��ʼ���ӿڵľ�̬����
				CLibrary Instance = (CLibrary) Native.loadLibrary(
						"D:\\nowing\\crf\\ICTCLAS2015\\lib\\win64\\NLPIR", CLibrary.class);
				
				public int NLPIR_Init(String sDataPath, int encoding,
						String sLicenceCode);
						
				public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
				public String NLPIR_FileProcess(String sSourceFilename,String sResultFilename,int bPostagged);
				public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
						boolean bWeightOut);
				public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,
						boolean bWeightOut);
				public int NLPIR_AddUserWord(String sWord);//add by qp 2008.11.10
				public int NLPIR_DelUsrWord(String sWord);//add by qp 2008.11.10
				public String NLPIR_GetLastErrorMsg();
				public void NLPIR_Exit();
			}
	/************
	 * ���ñ���ִ�������зִʣ�ֻҪ�ʣ���Ҫ����
	 * @param sSourceFilename����֮ǰ���ļ�
	 * @param sResultFilename����֮����ļ�
	 */
	public static void segment2Words(String sSourceFilePath,String sResultFilePath)
	{
		String argu ="D:\\nowing\\crf\\ICTCLAS2015";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "UTF-8";
		int charset_type = 1;			
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("��ʼ��ʧ�ܣ�fail reason is "+nativeBytes);
			return;
		}			
		try {				
			//���ļ��ִ�				
			CLibrary.Instance.NLPIR_FileProcess(sSourceFilePath, sResultFilePath, 1);
			CLibrary.Instance.NLPIR_Exit();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
