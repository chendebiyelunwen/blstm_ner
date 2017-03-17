package experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/********
 *
 * @author Fpp
 *�������ϸ�ʽ��xml�ģ����Բ���xml�Ŀ�Դ��dom4j�ṩ��API���н���
��ԭʼ��sogouCA�ļ������Ĵ���
1��ȥ���ļ������е�&���ţ�xml�ļ��е�&��&amp;�������ȷ������ɴ��ȥ���ˣ�
xml��һ������������ַ����Ƿ�Ҫȥ����
2����ԭʼ�ļ��޸�Ϊ�Ϸ���xml�ļ��ĸ�ʽ��
1������ͷ
2�������
3�������뷽ʽ��Ϊutf-8
3����xml��������������������ݲ��֣�д�뵥�����ı��ļ�
 *
 *
 */


public class CorpusParse {

	/*************
	 * 
	 * @param srcFile ԭʼ�ļ�
	 * @param desFile ȥ��������š�����ͷ��β��תΪutf-8������ļ�
	 * @throws IOException 
	 */
	private static void convert2XMLFormat(String srcFile,String desFile) throws IOException
	{
		File src=new File(srcFile);		
		FileReader fr=new FileReader(src);
		BufferedReader br=new BufferedReader(fr);
		
		File des=new File(desFile);
		OutputStream os=new FileOutputStream(des);
		OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
		BufferedWriter bw=new BufferedWriter(osw);
		String temp="";
		String temp1="";
		String head="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><sogouCA>";
		String tail="</sogouCA>";
		bw.write(head);
		while((temp=br.readLine())!=null)
		{
			temp1=temp.replace('&', ' ');
			bw.newLine();
			bw.write(temp1);			
		}
		bw.write(tail);
		bw.close();
		osw.close();
		os.close();
		br.close();
		fr.close();
		
	}
	/***
	 * ��src_path�µ������ļ���תΪ����xml��ʽ���ļ�,��������һ���ļ���
	 * @param src_path,des_path
	 * @throws IOException
	 */
	private static void convert2XMLFormat_All(String src_path,String des_path) throws IOException
	{
		File[] files = new File(src_path).listFiles();  
		for (File file : files) 
		{
			//System.out.println(file.getName());
            convert2XMLFormat(src_path+"/"+file.getName(), des_path+"/"+file.getName());
        }           
	}
	private static int parseXML2TXT(String srcFile,String desFile) throws IOException, DocumentException
	{
		int count = 0;
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(srcFile));
		// ��ȡ��Ԫ��
		Element root = document.getRootElement();
		System.out.println("Root: " + root.getName());
		// ��ȡ������Ԫ��
		List<Element> childList = root.elements();
		System.out.println("total child count: " + childList.size());
		System.out.println("�������-----------------------");
		// ����������Ԫ�أ�ȡ�ض�������Ԫ��д���ļ�
		String temp="";
		String temp1="";
		File des=new File(desFile);
		OutputStream os=new FileOutputStream(des);
		OutputStreamWriter osw=new OutputStreamWriter(os,"utf-8");
		BufferedWriter bw=new BufferedWriter(osw);
		String content = "";
		String title = "";
		for (Iterator iter = root.elementIterator(); iter.hasNext();) {
			Element e = (Element) iter.next();			
			title = e.elementText("contenttitle");
			// System.out.println();
			content = e.elementText("content");
			// System.out.println();
			if (title.length() > 2 || content.length() > 2)//����̫�̵ľͲ�Ҫ��
			{
				count++;
				bw.write(title+" "+content);
				bw.newLine();
			}
				
			// if(count>3) break;
		}		
		bw.close();
		osw.close();
		os.close();
		System.out.println(count);
		return count;
	}
	private static int parseXML2TXT_All(String src_path,String des_path) throws IOException, DocumentException
	{
		int count=0;
		int countTotal=0;
		File[] files = new File(src_path).listFiles();  
		for (File file : files) 		{
			
            count=parseXML2TXT(src_path+"/"+file.getName(), des_path+"/"+file.getName());
            countTotal+=count;
        }	
		return countTotal;
	}
	public static void main(String[] args) {
		try {
			//convert2XMLFormat_All("data/raw_data/sogouCA","data/raw_data/sogouCA_1");			
			//convert2XMLFormat("data/raw_data/sogouCA/news.allsites.020806.txt","data/raw_data/sogouCA/news.allsites.010806_1.txt");
			//parseXML2TXT("data/raw_data/sogouCA_1/news.allsites.010806.txt","data/raw_data/sogouCA_2/news.allsites.010806.txt");
			int c=parseXML2TXT_All("data/raw_data/sogouCA_1","data/raw_data/sogouCA_2");
			System.out.println(c);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
	

