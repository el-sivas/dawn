package de.elsivas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.elsivas.basic.DateUtils;
import de.elsivas.finance.data.model.ShareValuePeriod;

@ManagedBean
@SessionScoped
public class OutputController extends AbstractValueController<FinData> {

	private CurrentDataHolder currentDataHolder;

	public Collection<Integer> days = new ArrayList<>(Arrays.asList(1, 2, 4, 7));

	public String newValue;

	@Override
	protected void postConstruct() {
		currentDataHolder = CurrentDataHolderImpl.instance();
	}

	private ShareValuePeriod getCurrentValue() {
		final List<ShareValuePeriod> dataOrderedDecreasing = currentDataHolder.getDataOrderedDecreasing();
		if (dataOrderedDecreasing.isEmpty()) {
			return null;
		}
		return new ArrayList<>(dataOrderedDecreasing).get(0);
	}

	public Date getDate() {
		return getCurrentValue().getDate();
	}

	public Collection<FinData> getData() {
		return Collections.singletonList(
				OutputControllerUtils.create(getCurrentValue(), currentDataHolder.getDataOrderedDecreasing(), 0));
	}

	public Collection<FinData> getAnalyzeData() {
		final Comparator<FinData> reverse = new Comparator<FinData>() {

			@Override
			public int compare(FinData o1, FinData o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		};

		final TreeSet<FinData> treeSet = new TreeSet<>(reverse);
		final ShareValuePeriod currentValue = getCurrentValue();
		if (currentValue == null) {
			return treeSet;
		}
		treeSet.addAll(OutputControllerUtils.createAll(DateUtils.toLocalDate(currentValue.getDate()), currentDataHolder,
				days.toArray(new Integer[0])));
		return treeSet;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public void createNewValue() {
		days.add(Integer.valueOf(newValue));
		newValue = null;
	}

	public Collection<FinDataDetail> getDetailData() {
		return OutputControllerUtils.createDetail(getValue().getOffset(), currentDataHolder,
				OutputControllerUtils.upTo(22));
	}
}
