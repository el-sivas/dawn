package de.elsivas;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.finance.data.model.ShareValuePeriod;

//@ManagedBean
//@SessionScoped
public class IndexController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1L;

	private CurrentDataHolder dataHolder;

	@Override
	protected void postConstruct() throws Exception {
		dataHolder = CurrentDataHolderImpl.instance();
	}

	public boolean isConfigured() {
		final String value = FinanceWebappConfig.getValue("workdir");
		if (StringUtils.isBlank(value)) {
			return false;
		}

		return true;
	}

	public String getDatasetCount() {
		if (dataHolder == null) {
			return "no data holder";
		}
		return String.valueOf(dataHolder.getData().size());
	}

}
