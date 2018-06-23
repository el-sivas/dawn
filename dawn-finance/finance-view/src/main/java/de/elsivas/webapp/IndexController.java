package de.elsivas.webapp;

import java.io.File;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.elsivas.basic.filedao.KeyValueDao;
import de.elsivas.webapp.base.AbstractController;
import de.elsivas.webapp.base.Parameter;
import de.elsivas.webapp.base.ParameterCache;
import de.elsivas.webapp.base.ParameterCacheImpl;
import de.elsivas.webapp.base.StateController;

@ManagedBean
@SessionScoped
public class IndexController extends AbstractController implements StateController {

	private static final Log LOG = LogFactory.getLog(IndexController.class);

	private ParameterCache parameterCache = ParameterCacheImpl.getInstance();

	private String configFileName;

	private boolean configured = false;

	public IndexController() {
		initConfigFile();
	}

	private void initConfigFile() {
		final String configFileName = System.getProperty("slag.configfile");
		if (StringUtils.isBlank(configFileName)) {
			return;
		}
		if (new File(configFileName).exists()) {
			setConfigFileName(configFileName);
			addInfo("init: config file: " + configFileName);
		} else {
			addError("init: file not found: " + configFileName);
		}
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		LOG.info("use config file: " + configFileName);
		this.configFileName = configFileName;
	}

	public void configure() {
		if (StringUtils.isAllBlank(configFileName)) {
			addError("no config file");
			return;
		}
		final File file = new File(configFileName);
		if (!file.exists()) {
			addError("file not exists: " + configFileName);
			return;
		}
		configured = true;
		addInfo("configured");
		final Map<String, String> map = KeyValueDao.read(configFileName);
		parameterCache.putAll(map);
		addInfo("Workdir: " + parameterCache.getStringValue(Parameter.WORKDIR));
	}

	public void deconfigure() {
		configured = false;
		parameterCache.clear();
		addInfo("deconfigured");
	}

	public boolean isConfigured() {
		return configured;
	}

	@Override
	public String getState() {
		if (isConfigured()) {
			return "configured";
		} else {
			return "not configured";
		}
	}
}
