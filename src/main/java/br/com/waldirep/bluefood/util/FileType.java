package br.com.waldirep.bluefood.util;

public enum FileType {

	
	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg");
	
	String mimeType;
	String extension;
	
	
	private FileType(String mimeType, String extension) {
		this.mimeType = mimeType;
		this.extension = extension;
	}


	public String getMimeType() {
		return mimeType;
	}


	public String getExtension() {
		return extension;
	}
	
	
	/**
	 * Metodo que compara os mimeType ignorando letras maiusculas ou minusculas
	 * @return
	 */
	public boolean sameOf(String mimeType) {
		return this.mimeType.equalsIgnoreCase(mimeType);
	}
	
	
	/**
	 * Retorna o tipo de mimeType
	 * @param mimeType
	 * @return
	 */
	public static FileType of(String mimeType) {
		/*values() -> retorna todos os elementos do enum ate encontrar o desejado*/
		for(FileType fileType : values()) {
			if(fileType.sameOf(mimeType)) {
				return fileType;
			}
		}
		return null;
	}
}
