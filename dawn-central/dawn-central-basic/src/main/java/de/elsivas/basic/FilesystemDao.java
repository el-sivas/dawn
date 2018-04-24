package de.elsivas.basic;

public interface FilesystemDao<E extends EntityBean> {
	
	void save(E bean, String fileName);
	
	E load(String filename);

}
