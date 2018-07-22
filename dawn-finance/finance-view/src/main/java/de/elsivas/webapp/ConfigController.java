package de.elsivas.webapp;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.elsivas.webapp.base.AbstractController;
import de.elsivas.webapp.base.ParameterCache;
import de.elsivas.webapp.base.ParameterCacheImpl;
import de.elsivas.webapp.base.StateController;

@ManagedBean
@SessionScoped
public class ConfigController extends AbstractController implements StateController {

	private ParameterCache parameterCache = ParameterCacheImpl.getInstance();

	@Override
	public String getState() {
		return !parameterCache.getAvailableKeys().isEmpty() ? "configured" : "not configured";
	}

	public Collection<Entry> getConfig() {
		final Function<? super String, ? extends Entry> function = new Function<String, Entry>() {

			@Override
			public Entry apply(String t) {
				final Entry e = new Entry();
				e.setKey(t);
				e.setValue(parameterCache.getStringValue(t));
				return e;

			}
		};
		return parameterCache.getAvailableKeys().stream().map(function).collect(Collectors.toList());
	}

	public class Entry {
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
