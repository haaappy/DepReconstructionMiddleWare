package nju.moon.lhc.reconstruction.classloader;

public class AdaptExtDepClassLoader extends AdaptDepClassLoader {

	protected boolean valid;
	
	public AdaptExtDepClassLoader(String rootDir, String classLoaderName) {
		super(rootDir, classLoaderName);
		valid = true;
	}

	public AdaptExtDepClassLoader() {
		super();
		valid = true;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}
	

}
