package com.waveditor;
import java.io.*;


public class WavProc {
	public static final int HEMING_WINDOW = 1;
	public static final int HANN_WINDOW = 2;
	public static final int BLACKMAN_WINDOW = 3;
	public static final int BLACKMAN_HARRIS_WINDOW = 4;
	
	private final int WAVE_HEADER_MINSIZE = 36;
	private final int DATE_HEADER_SIZE = 8;
	
	private int[] wavDataMono;
	private int[] wavDataLeft, wavDataRight;
	
	private int[] wavBufMono;
	private int[] wavBufLeft, wavBufRight;
	
	//WaveHeader
	private String idRiff;//4 bytes
	private int riffLen;//4 bytes
	private String idWave;//4 bytes
	private String idFmt;//4 bytes
	private int infoLen;//4 bytes
	private int waveType;//2 byte
	private int chanels;//2 byte
	private int freq;//4 bytes
	private int bytePerSec;//4 bytes
	private int align;//2 byte
	private int bits;//2 byte;
	//WaveHeader size=36 + (infoLen-16) bytes
	private byte[] bufWH;
	private byte[] bufTp;
	
	//DataHeader
	private String idData;//4 bytes
	private int dataLen;//4 bytes
	//DataHeader size=8
	private byte[] bufDH;
	
	private boolean isEmptyBuf;
	
	private FileInputStream iStrWav;
	
	public WavProc(String FileNameWav) {
		isEmptyBuf = false;
		
		try	{
			iStrWav = new FileInputStream(FileNameWav);
		
			bufWH = new byte[this.WAVE_HEADER_MINSIZE];
			iStrWav.read(bufWH);
		
			//decoding bufDH to WaveHeader
			idRiff = BufToString(bufWH,0,3);
			riffLen = BufToInt(bufWH,4,7);
			idWave = BufToString(bufWH,8,11);
			idFmt = BufToString(bufWH,12,15);
			infoLen = BufToInt(bufWH,16,19);
			waveType = BufToInt(bufWH,20,21);
			chanels = BufToInt(bufWH,22,23);
			freq = BufToInt(bufWH,24,27);
			bytePerSec = BufToInt(bufWH,28,31);
			align = BufToInt(bufWH,32,33);
			bits = BufToInt(bufWH,34,35);
		
			if (infoLen>16) {
				bufTp = new byte[infoLen-16];
				iStrWav.read(bufTp);
			}
		
			bufDH = new byte[this.DATE_HEADER_SIZE];
			iStrWav.read(bufDH);
		
			//decoding bufWH to DataHeader
			idData = BufToString(bufDH,0,3);
			dataLen = BufToInt(bufDH,4,7);
		} catch (FileNotFoundException e) {
			System.out.println("File "+FileNameWav+" not found!");
		} catch (IOException e)	{
			System.out.println("Input/output error in file "+FileNameWav+"!");
		}
	}
	
	private int BufToInt(byte[] buf, int i0, int i1) {
		int sum = 0;
		for (int i=i0;i<=i1;i++) {
			int dataBuf = (buf[i]>=0)?(int) buf[i]:256+((int) buf[i]);
			sum += (dataBuf<<((i-i0)*8));
		}
		
		return sum;
	}

	private int BufToIntNegative(byte[] buf) {
		int sum = 0;
		
		for (int i=0;i<buf.length;i++) {
			int dataBuf = (buf[i]>=0)?(int) buf[i]:256+((int) buf[i]);
			sum += (dataBuf<<(i*8));
		}
		
		return sum;
	}
	
	private int decodeAmplit(int intAmpl) {
		int sum = 0;
		for (int i=0;i<(bits/8);i++) {
			byte tmpByte = (byte) ( (intAmpl>>(i*8)) & 0xFF);
			sum += (tmpByte<<(i*8));
		}
		
		return sum;
	}
	
	
	private String BufToString(byte[] buf, int i0, int i1) {
		StringBuffer res = new StringBuffer("");
		for (int i=i0;i<=i1;i++) {
			res.append((char) (buf[i]));
		}
		return res.toString();
	}
	
	public boolean ReadDataWav() {
		try	{
			byte[] bufDA = new byte[bits/8];
			int dataSamplesLen = dataLen/(bits/8);

			if (chanels == 1) wavDataMono = new int[dataSamplesLen];
			
			if (chanels == 2) {
				dataSamplesLen /= 2; 
				wavDataLeft = new int[dataSamplesLen];
				wavDataRight = new int[dataSamplesLen];
			}
			
			for (int i=0;i<dataSamplesLen;i++) {
				if (chanels == 1) {
					iStrWav.read(bufDA);
					wavDataMono[i] = BufToIntNegative(bufDA);
				}
				if (chanels == 2) {
					iStrWav.read(bufDA);
					wavDataLeft[i] = BufToIntNegative(bufDA);
					iStrWav.read(bufDA);
					wavDataRight[i] = BufToIntNegative(bufDA);
				}
			}
			
			iStrWav.close();
			return true;
		} catch (IOException e)	{
			return false;
		}
	}
	
	public double[] getFurie(int numCh, int nHz, double dDivSec, int typeWindowFunc) {
		double[] res = new double[nHz];
		for (int i=0;i<nHz;i++) res[i] = 0;
		
		int dDivSmp = (int) (dDivSec*freq);
		int nDiv = dataLen / (dDivSmp*(chanels*bits/8));
		
		for (int i=0;i<nDiv;i++) {
			for (int j=1;j<=nHz;j++) {
				double reX = 0;
				double imX = 0;
				for (int k=0;k<dDivSmp;k++)	{
					double dAmplit = 0.0;
					
					if (chanels == 1) dAmplit = wavDataMono[k+i*dDivSmp];
					if ((chanels == 2)&&(numCh == 0)) dAmplit = wavDataLeft[k+i*dDivSmp];
					if ((chanels == 2)&&(numCh == 1)) dAmplit = wavDataRight[k+i*dDivSmp];
					
					switch (typeWindowFunc) {
					case 1:dAmplit *= (0.54-0.46*Math.cos(2*Math.PI*k/dDivSmp));break;
					case 2:dAmplit *= 0.5*(1-Math.cos(2*Math.PI*k/dDivSmp));break;
					case 3:dAmplit *= (0.42-0.5*Math.cos(2*Math.PI*k/dDivSmp)+0.08*Math.cos(4*Math.PI*k/dDivSmp));break;
					case 4:dAmplit *= (0.35875 - 0.48829*Math.cos(2*Math.PI*k/dDivSmp) + 0.14128*Math.cos(4*Math.PI*k/dDivSmp) - 0.01168*Math.cos(6*Math.PI*k/dDivSmp));break;	
					}
					
					reX += 1.0*dAmplit*Math.cos(2.0*Math.PI*k*j/dDivSmp);
					imX += 1.0*dAmplit*Math.sin(2.0*Math.PI*k*j/dDivSmp);
				}
				reX /= 1.0*dDivSmp;
				imX /= 1.0*dDivSmp;
				if (res[j-1] < Math.sqrt(Math.abs(reX*imX))) res[j-1] = Math.sqrt(Math.abs(reX*imX));  
			}
		}
		
		double Max = 0;
		for (int i=0;i<nHz;i++) if (Max<res[i]) Max = res[i];
		
		for (int i=0;i<nHz;i++) res[i] /= Max;
		
		return res;
	}
	
	public String toString() {
		return "Wave header information.\n"+
			"idRiff:"+idRiff+"\n"+
			"riffLen:"+riffLen+"\n"+
			"idWave:"+idWave+"\n"+
			"idFmt:"+idFmt+"\n"+
			"infoLen:"+infoLen+"\n"+
			"waveType:"+waveType+"\n"+
			"chanels:"+chanels+"\n"+
			"freq:"+freq+"\n"+
			"bytePerSec:"+bytePerSec+"\n"+
			"align:"+align+"\n"+
			"bits:"+bits+"\n"+
			"Data header information.\n"+
			"idData:"+idData+"\n"+
			"dataLen:"+dataLen;
	}
	
	public int getChanels() {
		return chanels;
	}
	
	public int[] getDataAmplitudeMono()	{
		int[] wavDataMonoDecode = new int[wavDataMono.length];
		
		for (int i=0;i<wavDataMono.length;i++)
			wavDataMonoDecode[i] = decodeAmplit(wavDataMono[i]);
		
		return wavDataMonoDecode;
	}
	
	public int[] getDataAmplitudeLeft()	{
		int[] wavDataLeftDecode = new int[wavDataLeft.length];
		
		for (int i=0;i<wavDataLeft.length;i++)
			wavDataLeftDecode[i] = decodeAmplit(wavDataLeft[i]);
		
		return wavDataLeftDecode;
	}
	
	public int[] getDataAmplitudeRight() {
		int[] wavDataRightDecode = new int[wavDataRight.length];
		
		for (int i=0;i<wavDataRight.length;i++)
			wavDataRightDecode[i] = decodeAmplit(wavDataRight[i]);
		
		return wavDataRightDecode;
	}
	
	public int getMaxSamples() {
		return dataLen/(chanels*bits/8);
	}
	
	public int getMaxAmp() {
		return (int) (Math.pow(256.0,bits/8)/2);
	}
	
	private void correctLenSamples() {
		if (chanels==1) {
			dataLen = wavDataMono.length*(bits/8);
		}
		if (chanels==2)	{
			dataLen = wavDataLeft.length*2*(bits/8);
		}
		
		//System.out.println("Datalen="+dataLen);
	}
	
	public void copyToBuf(int beginIndex, int endIndex) {
		if (endIndex<=beginIndex) return;
		
		if (chanels==1)	{
			wavBufMono = new int[endIndex-beginIndex+1];
			for (int i=beginIndex;i<=endIndex;i++)
				wavBufMono[i-beginIndex] = wavDataMono[i];
		}
		
		if (chanels==2) {
			wavBufLeft = new int[endIndex-beginIndex+1];
			wavBufRight = new int[endIndex-beginIndex+1];
			for (int i=beginIndex;i<=endIndex;i++) {
				wavBufLeft[i-beginIndex] = wavDataLeft[i];
				wavBufRight[i-beginIndex] = wavDataRight[i];
			}
		}
		
		isEmptyBuf = true;
	}
	
	public void cutToBuf(int beginIndex,int endIndex) {
		if (endIndex<=beginIndex) return;
		
		copyToBuf(beginIndex,endIndex);
		
		if (chanels==1)	{
			for (int i=endIndex+1;i<wavDataMono.length;i++)	{
				wavDataMono[beginIndex+i-(endIndex-1)] = wavDataMono[i];
			}
			
			int[] tmp = new int[wavDataMono.length-wavBufMono.length];
			for (int i=0;i<wavDataMono.length-wavBufMono.length;i++) {
				tmp[i] = wavDataMono[i];
			}
			
			wavDataMono = tmp;
		}
		
		if (chanels==2)	{
			for (int i=endIndex+1;i<wavDataLeft.length;i++)	{
				wavDataLeft[beginIndex+i-(endIndex-1)] = wavDataLeft[i];
				wavDataRight[beginIndex+i-(endIndex-1)] = wavDataRight[i];
			}
			
			int[] tmpL = new int[wavDataLeft.length-wavBufLeft.length];
			int[] tmpR = new int[wavDataRight.length-wavBufRight.length];
			for (int i=0;i<wavDataLeft.length-wavBufLeft.length;i++) {
				tmpL[i] = wavDataLeft[i];
				tmpR[i] = wavDataRight[i];
			}
			
			wavDataLeft = tmpL;
			wavDataRight = tmpR;
		}
		
		correctLenSamples();
	}
	
	public void pasteFromBuf(int pasteIndex) {
		if (!isEmptyBuf) return;
		
		if (chanels==1)	{
			int[] tmp = new int[wavDataMono.length+wavBufMono.length];
			
			for (int i=0;i<pasteIndex;i++)
				tmp[i] = wavDataMono[i];
			for (int i=0;i<wavBufMono.length;i++)
				tmp[i+pasteIndex] = wavBufMono[i];
			for (int i=pasteIndex;i<wavDataMono.length;i++)
				tmp[i+wavBufMono.length] = wavDataMono[i];
			
			wavDataMono = tmp;
		}
		if (chanels==2)	{
			int[] tmpL = new int[wavDataLeft.length+wavBufLeft.length];
			int[] tmpR = new int[wavDataRight.length+wavBufRight.length];
			
			for (int i=0;i<pasteIndex;i++) {
				tmpL[i] = wavDataLeft[i];
				tmpR[i] = wavDataRight[i];
			}
			for (int i=0;i<wavBufLeft.length;i++) {
				tmpL[i+pasteIndex] = wavBufLeft[i];
				tmpR[i+pasteIndex] = wavBufRight[i];
			}
			for (int i=pasteIndex;i<wavBufLeft.length;i++) {
				tmpL[i+wavBufLeft.length] = wavDataLeft[i];
				tmpR[i+wavBufRight.length] = wavDataRight[i];
			}
			
			wavDataLeft = tmpL;
			wavDataRight = tmpR;
		}
		
		correctLenSamples();
	}
	
	private byte intToIndexedByte(int dInt,int iByte) {
		return (byte) ((dInt>>(iByte*8))&0xFF);
	}
	
	public void writeToFile(String wavFileName) {
		try	{
			FileOutputStream fO = new FileOutputStream(wavFileName);
			
			fO.write(bufWH);
			fO.write(bufTp);
			
			for (int i=0;i<4;i++)
				bufDH[4+i] = intToIndexedByte(dataLen,i);
			fO.write(bufDH);
			
			if (chanels==1)	{
				byte[] tmpBuf = new byte[bits/8];
				for (int i=0;i<wavDataMono.length;i++) {
					for (int j=0;j<(bits/8);j++)
						tmpBuf[j] = intToIndexedByte(wavDataMono[i],j);
					
					fO.write(tmpBuf);
				}
			}
			if (chanels==2)	{
				byte[] tmpBufLeft = new byte[bits/8];
				byte[] tmpBufRight = new byte[bits/8];
				for (int i=0;i<wavDataLeft.length;i++) {
					for (int j=0;j<(bits/8);j++) {
						tmpBufLeft[j] = intToIndexedByte(wavDataLeft[i],j);
						tmpBufRight[j] = intToIndexedByte(wavDataRight[i],j);
					}
					fO.write(tmpBufLeft);
					fO.write(tmpBufRight);
				}
			}
			fO.close();			
		} catch (Exception e) {
			return;
		}
	}
}
