package de.elsivas;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.download.FinDownloadUtils;
import de.elsivas.finance.logic.imports.FinImportUtils;
import de.elsivas.finance.logic.portals.Portal;

//@ManagedBean
//@SessionScoped
public class DataController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1L;

	private CurrentDataHolderControllable dataHolder;

	private StringBuffer messages = new StringBuffer();
	
	public String getMessages() {
		final String string = messages.toString();
		messages = new StringBuffer();
		return string;
	}
	
	public boolean isMessagesOccured() {
		return !StringUtils.isBlank(messages);
	}

	@Override
	protected void postConstruct() {
		dataHolder = CurrentDataHolderImpl.instanceControllable();
	}

	public void updateData() {
		final FinProperties properties = properties();
		final Protocolant instance = Protocolant.instance();
		FinDownloadUtils.download(properties, instance);
		FinImportUtils.importData(properties, instance);
		AppLog.append("data updated:\n" + instance.toProtocol());
		messages.append(instance.toProtocol());
	}

	public Collection<ShareValuePeriod> getData() {
		final TreeSet<ShareValuePeriod> set = new TreeSet<>(new Comparator<ShareValuePeriod>() {

			@Override
			public int compare(ShareValuePeriod o1, ShareValuePeriod o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});
		set.addAll(dataHolder.getData());
		return set;
	}

	public void clearData() {
		dataHolder.clear();
	}

	public void loadData() {
		dataHolder.load();
	}

	private FinProperties properties() {
		return new FinProperties() {

			@Override
			public String getWorkdir() {
				return FinanceWebappConfig.getValue("workdir");
			}

			@Override
			public Portal getPortal() {
				return Portal.ONVISTA;
			}

			@Override
			public Collection<String> getDownloads() {
				return Collections.singletonList("DAX");
			}
		};
	}
	
	

}
