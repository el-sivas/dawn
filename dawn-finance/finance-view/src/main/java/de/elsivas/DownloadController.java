package de.elsivas;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DownloadController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1L;

	private StringBuffer sb = new StringBuffer();

	public String getDownloadMessages() {
		return "lorem";
	}

	public void download() {
		sb = new StringBuffer();
		sb.append("dl: "+ new Date());
	}
}
