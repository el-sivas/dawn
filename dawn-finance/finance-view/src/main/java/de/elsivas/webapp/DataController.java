package de.elsivas.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.elsivas.basic.protocol.Protocolant;
import de.elsivas.finance.data.model.ShareValuePeriod;
import de.elsivas.finance.data.model.ShareValuePeriodNewToOldComparator;
import de.elsivas.finance.data.model.Wertpapier;
import de.elsivas.finance.logic.FinProperties;
import de.elsivas.finance.logic.download.FinDownloadUtils;
import de.elsivas.finance.logic.imports.FinImportUtils;
import de.elsivas.finance.logic.parse.FinParseUtils;
import de.elsivas.finance.logic.portals.Portal;
import de.elsivas.webapp.base.AbstractController;
import de.elsivas.webapp.base.Parameter;
import de.elsivas.webapp.base.ParameterCache;
import de.elsivas.webapp.base.ParameterCacheImpl;
import de.elsivas.webapp.base.StateController;
import de.elsivas.webapp.logic.ShareValuePeriodLogicService;

@ManagedBean
@SessionScoped
public class DataController extends AbstractController implements StateController {

	private ParameterCache parameterCache = ParameterCacheImpl.getInstance();

	private ShareValuePeriodLogicService shareValuePeriodLogicService = ShareValuePeriodLogicService.getInstance();

	private Collection<ShareValuePeriod> all = new TreeSet<>(new ShareValuePeriodNewToOldComparator());

	private Wertpapier wertpapier;

	@Override
	public String getState() {
		if (parameterCache.isSet(Parameter.WORKDIR)) {
			return "configured";
		}
		return "not configured";
	}

	public Collection<Wertpapier> getWertpapiere() {
		return Arrays.asList(Wertpapier.values());
	}

	public Collection<ShareValuePeriod> getData() {
		return all;
	}

	public void findAll() {
		final Collection<ShareValuePeriod> findAll = shareValuePeriodLogicService
				.findAll(parameterCache.getStringValue(Parameter.WORKDIR));
		addInfo(findAll.size() + " data sets load.");
		all.addAll(findAll);
	}

	public void clearAll() {
		all.clear();
		addInfo("all cleared.");
	}

	public void updateData() {
		clearAll();
		FinProperties properties = newProperties();
		Protocolant protocolant = Protocolant.instance();
		FinDownloadUtils.download(properties, protocolant);
		FinParseUtils.parse(properties, protocolant);
		FinImportUtils.importData(properties, protocolant);
		addInfo(protocolant.toProtocol());
		findAll();
	}

	private FinProperties newProperties() {
		return new FinProperties() {

			@Override
			public String getWorkdir() {
				return parameterCache.getStringValue(Parameter.WORKDIR);
			}

			@Override
			public Portal getPortal() {
				return Portal.ONVISTA;
			}

			@Override
			public Collection<String> getDownloads() {
				final Collection<Wertpapier> wp = new ArrayList<>();
				if (wertpapier != null) {
					wp.add(wertpapier);
				} else {
					wp.addAll(Arrays.asList(Wertpapier.values()));
				}
				return wp.stream().map(e -> e.name()).collect(Collectors.toList());
			}
		};
	}

	public Wertpapier getWertpapier() {
		return wertpapier;
	}

	public void setWertpapier(Wertpapier wertpapier) {
		this.wertpapier = wertpapier;
	}

}
