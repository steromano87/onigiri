package io.github.steromano87.onigiri.ui;

import java.util.Date;
import java.util.List;

public interface DatePicker extends ExtendedElement {
    void setDate(Date date);

    Date getDate();

    void setDates(List<Date> dates);

    List<Date> getDates();

    void setDateInterval(Date startDate, Date endDate);

    Date[] getDateInterval();

    void reset();
}
